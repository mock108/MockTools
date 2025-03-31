package io.github.mockup.zenith.web.login.controller;

import java.time.Duration;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import io.github.mockup.zenith.core.constans.Pages;
import io.github.mockup.zenith.core.util.ZenithStringUtils;
import io.github.mockup.zenith.web.login.dto.polaris.PolarisAuthResponse;
import io.github.mockup.zenith.web.login.dto.request.AuthRequestForm;
import io.github.mockup.zenith.web.login.service.AuthProxyService;

@Controller
public class LoginController {
	/** ロガー */
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	private final AuthProxyService authService;

	public LoginController(AuthProxyService authService) {
		this.authService = authService;
	}

	@GetMapping("/login")
	public String loginForm() {
		return Pages.PAGE_LOGIN;
	}

	@PostMapping("/login")
	public String loginSubmit(AuthRequestForm form, HttpSession session, HttpServletResponse response, Model model) {
		log.info("login req");
		PolarisAuthResponse tokens;
		try {
			tokens = authService.login(form.getUsername(), form.getPassword());
		} catch (Exception e) {
			model.addAttribute("error", "ログインに失敗しました。");
			return Pages.PAGE_LOGIN;
		}
		session.setAttribute("accessToken", tokens.accessToken());
		// リフレッシュトークンを Cookie に保存（HttpOnly, Secure）
		ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", tokens.refreshToken())
				.httpOnly(true)
				.secure(false) // 本番は true（https）
				.path("/")
				.maxAge(Duration.ofDays(7))
				.sameSite("Strict")
				.build();
		response.addHeader("Set-Cookie", refreshCookie.toString());
		return ZenithStringUtils.redirect("/home"); // 任意の画面へ
	}
}
