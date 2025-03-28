package io.github.mockup.polaris.core.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "User")
public class User {
	/** ID */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	@Column(name = "id")
	private Long id;

	/** ユーザ名 */
	@Getter
	@Setter
	@Column(name = "username", unique = true)
	private String username;

	/** パスワード */
	@Getter
	@Setter
	@Column(name = "password")
	private String password;

	/** 役割 */
	@Getter
	@Setter
	@Column(name = "role")
	private String role;

	@Getter
	@Setter
	@Column(name = "refreshtoken")
	private String refreshToken;
}