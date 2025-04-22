package io.github.mockup.algoltool.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.github.mockup.algoltool.common.config.AlgolConfig;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * {@code AlgolToolCommand} は、AlgolTool CLI アプリケーションのルートコマンドです。
 * <p>
 * Picocli を使用してコマンドラインオプションを定義し、サブコマンドとして
 * {@code init}, {@code encrypt}, {@code decrypt} を提供します。
 * </p>
 */
@Command(name = "algoltool", version = "algoltool 0.1", description = "CLI tool for encryption and key management using Algol.", subcommands = {
		InitCommand.class,
		EncryptCommand.class,
		DecryptCommand.class
}, sortOptions = false)
@Component
@RequiredArgsConstructor

public class AlgolToolCommand implements Runnable {

	/** ロガー */
	private static final Logger log = LoggerFactory.getLogger(AlgolToolCommand.class);

	/** 設定クラス（暗号鍵格納パス等） */
	private final AlgolConfig config;

	/**
	 * 鍵ファイルの格納ディレクトリを指定するオプション。
	 * このオプションはすべてのサブコマンドに継承されます。
	 *
	 * @param path 鍵格納ディレクトリのパス
	 */
	@Option(names = "--secret-dir", description = "鍵ファイル格納ディレクトリ", scope = CommandLine.ScopeType.INHERIT)
	public void setSecretDir(String path) {
		config.setSecretDir(path);
	}

	/**
	 * サブコマンドが指定されなかった場合に実行される処理。
	 * デフォルトでは警告メッセージを表示します。
	 */
	@Override
	public void run() {
		log.warn("Use one of the subcommands. Try '--help' for more information.");
	}
}
