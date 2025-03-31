package io.github.mockup.polaris.web.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.github.mockup.algol.AlgolEncrypter;
import io.github.mockup.polaris.core.database.entity.User;
import io.github.mockup.polaris.core.database.repository.UserRepository;
import io.github.mockup.polaris.web.dto.request.AuthRequest;
import io.github.mockup.polaris.web.dto.request.RegisterRequest;
import io.github.mockup.polaris.web.dto.response.AuthResponse;

@Service
public class AuthService {

	/** ユーザRepository */
	private final UserRepository userRepository;
	/** パスワードエンコーダ */
	private final AlgolEncrypter algolEncrypter;
	/** JWTサービス */
	private final JwtService jwtService;

	public AuthService(UserRepository userRepo, AlgolEncrypter algolEncrypter, JwtService jwtService) {
		this.userRepository = userRepo;
		this.algolEncrypter = algolEncrypter;
		this.jwtService = jwtService;
	}

	/**
	 * ユーザ登録
	 * @param request ユーザ情報
	 */
	public void register(RegisterRequest request) {
		var user = new User();
		user.setUsername(request.username());
		user.setPassword(algolEncrypter.encryptstr(request.password()));
		user.setRole(request.role());
		userRepository.save(user);
	}

	/**
	 * ログイン処理
	 * @param request 認証情報
	 * @return JWT情報
	 */
	public AuthResponse login(AuthRequest request) {
		var user = userRepository.findByUsername(request.username())
				.orElseThrow(() -> new UsernameNotFoundException("Not found"));

		if (!request.password().equals(algolEncrypter.decrypt(user.getPassword()))) {
			throw new BadCredentialsException("Invalid credentials");
		}

		String accessToken = jwtService.generateToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);

		user.setRefreshToken(refreshToken);
		userRepository.save(user);

		return new AuthResponse(accessToken, refreshToken);
	}

	/**
	 * ログアウト処理
	 * @param authHeader 認証ヘッダ情報
	 */
	public void logout(String authHeader) {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new IllegalArgumentException("Invalid Authorization header");
		}

		String token = authHeader.substring(7); // "Bearer " を除去
		String username = jwtService.extractUsername(token);

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		// refreshToken を削除（= ログアウト）
		user.setRefreshToken(null);
		userRepository.save(user);
	}

	/**
	 * JWTリフレッシュ
	 * @param refreshToken リフレッシュ用JWTトークン
	 * @return 
	 */
	public AuthResponse refreshAccessToken(String refreshToken) {
		// トークン検証（署名・有効期限）
		String username = jwtService.extractUsername(refreshToken);
		var user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		// DB上のトークンと一致するか確認（stateful）
		if (!refreshToken.equals(user.getRefreshToken())) {
			throw new RuntimeException("Invalid refresh token");
		}

		if (jwtService.isTokenExpired(refreshToken)) {
			throw new RuntimeException("Refresh token expired");
		}

		// 新トークン発行
		String newAccessToken = jwtService.generateToken(user);
		String newRefreshToken = jwtService.generateRefreshToken(user);

		// リフレッシュトークンも更新する
		user.setRefreshToken(newRefreshToken);
		userRepository.save(user);

		return new AuthResponse(newAccessToken, newRefreshToken);
	}

}
