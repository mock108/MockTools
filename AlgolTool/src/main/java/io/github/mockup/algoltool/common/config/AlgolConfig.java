package io.github.mockup.algoltool.common.config;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.github.mockup.algoltool.common.constants.AlgolToolConst;
import lombok.Data;

/**
 * {@code AlgolConfig} は、AlgolTool が使用する設定値（主に鍵ファイルの格納ディレクトリ）を保持するクラスです。
 * <p>
 * 鍵ファイル（AES鍵、IV、RSA鍵ペア）の保存先パスを取得するユーティリティメソッドも提供します。
 * </p>
 */
@Component
@Data
public class AlgolConfig {

	/**
	 * 鍵ファイルの格納ディレクトリパス（デフォルト: {@code ~/.algol/secret}）
	 */
	private String secretDir;

	/**
	 * コンストラクタ。プロパティ {@code algol.secret-dir} から設定値を取得します。
	 *
	 * @param secretDir 鍵ファイルを格納するディレクトリパス
	 */
	public AlgolConfig(@Value("${algol.secret-dir:${user.home}/.algol/secret}") String secretDir) {
		this.secretDir = secretDir;
	}

	/**
	 * AES秘密鍵ファイルのパスを返します。
	 *
	 * @return AES秘密鍵ファイルパス
	 */
	public String getSecretKey() {
		return Path.of(this.getSecretDir(), AlgolToolConst.Encrypt.KEY_FILENAME).toString();
	}

	/**
	 * AES初期化ベクトル（IV）ファイルのパスを返します。
	 *
	 * @return IVファイルパス
	 */
	public String getSecretIV() {
		return Path.of(this.getSecretDir(), AlgolToolConst.Encrypt.IV_FILENAME).toString();
	}

	/**
	 * RSA公開鍵ファイルのパスを返します。
	 *
	 * @return 公開鍵ファイルパス
	 */
	public String getPublic() {
		return Path.of(this.getSecretDir(), AlgolToolConst.JWT.PUBLIC_FILENAME).toString();
	}

	/**
	 * RSA秘密鍵ファイルのパスを返します。
	 *
	 * @return 秘密鍵ファイルパス
	 */
	public String getPrivate() {
		return Path.of(this.getSecretDir(), AlgolToolConst.JWT.PRIVATE_FILENAME).toString();
	}
}
