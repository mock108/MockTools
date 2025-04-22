package io.github.mockup.algoltool.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.github.mockup.algoltool.service.EncryptionService;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * {@code DecryptCommand} は、AES で暗号化された Base64 文字列を復号する CLI サブコマンドです。
 * <p>
 * {@code --text} オプションで指定された暗号化文字列を復号し、標準出力に表示します。
 * </p>
 */
@Component
@Command(name = "decrypt", description = "指定文字列をAESで復号します（Base64入力）")
@RequiredArgsConstructor
public class DecryptCommand implements Runnable {

	/** ロガー */
	private static final Logger log = LoggerFactory.getLogger(DecryptCommand.class);

	/** 暗号化・復号サービス */
	private final EncryptionService encryptionService;

	/**
	 * 復号対象の Base64 文字列を受け取るオプション。
	 */
	@Option(names = { "-t", "--text" }, required = true, description = "Base64形式の暗号化文字列")
	private String encryptedText;

	/**
	 * 復号処理を実行し、復号結果を INFO レベルで出力します。
	 * エラーが発生した場合は ERROR レベルでスタックトレースを出力します。
	 */
	@Override
	public void run() {
		try {
			String decrypted = encryptionService.decryptText(encryptedText);
			log.info(decrypted);
		} catch (Exception e) {
			log.error("復号に失敗しました: ", e);
		}
	}
}
