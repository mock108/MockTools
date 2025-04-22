package io.github.mockup.algoltool.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.github.mockup.algoltool.service.EncryptionService;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * {@code EncryptCommand} は、指定された文字列を AES で暗号化し、
 * Base64 エンコードされた暗号文字列を出力する CLI サブコマンドです。
 * <p>
 * {@code --text} オプションでプレーンテキストを指定し、標準出力に暗号結果を出力します。
 * </p>
 */
@Component
@Command(name = "encrypt", description = "指定文字列をAESで暗号化してBase64出力します")
@RequiredArgsConstructor
public class EncryptCommand implements Runnable {

	/** ロガー */
	private static final Logger log = LoggerFactory.getLogger(EncryptCommand.class);

	/** 暗号化・復号サービス */
	private final EncryptionService encryptionService;

	/**
	 * 暗号化対象のプレーンテキストを受け取るオプション。
	 */
	@Option(names = { "-t", "--text" }, required = true, description = "暗号化対象の文字列")
	private String inputText;

	/**
	 * 入力文字列を AES 暗号化し、Base64 文字列として INFO ログで出力します。
	 * 処理中にエラーが発生した場合は、ERROR ログに出力します。
	 */
	@Override
	public void run() {
		try {
			String encrypted = encryptionService.encryptText(inputText);
			log.info(encrypted);
		} catch (Exception e) {
			log.error("暗号化に失敗しました", e);
		}
	}
}
