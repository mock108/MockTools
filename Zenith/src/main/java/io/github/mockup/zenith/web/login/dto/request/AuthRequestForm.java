package io.github.mockup.zenith.web.login.dto.request;

import lombok.Getter;
import lombok.Setter;

public class AuthRequestForm {
	/** ユーザ名 */
	@Getter
	@Setter
	private String username;
	/** パスワード */
	@Getter
	@Setter
	private String password;
}
