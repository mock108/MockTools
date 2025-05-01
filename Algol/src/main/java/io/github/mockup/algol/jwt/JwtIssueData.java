package io.github.mockup.algol.jwt;

import java.time.Duration;

import lombok.Data;

@Data
public class JwtIssueData {
	/** 発行者 */
	private String issuer;
	/** トークンの subject（ユーザー識別子など） */
	private String subject;
	/** 権限ロールの空白区切り文字列（例: "USER ADMIN"） */
	private String rolesCsv;
	/** 有効期限 */
	private Duration expiration;
}