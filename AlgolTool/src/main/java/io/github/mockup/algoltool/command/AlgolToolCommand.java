package io.github.mockup.algoltool.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.github.mockup.algoltool.common.config.AlgolConfig;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "algoltool", mixinStandardHelpOptions = true, version = "algoltool 0.1", description = "CLI tool for encryption and key management using Algol.", subcommands = {
		InitCommand.class,
		EncryptCommand.class,
		DecryptCommand.class
}, usageHelpAutoWidth = true, sortOptions = false)
@Component
@RequiredArgsConstructor
public class AlgolToolCommand implements Runnable {
	/** ロガー */
	private static final Logger log = LoggerFactory.getLogger(AlgolToolCommand.class);

	private final AlgolConfig config;

	@Option(names = "--secret-dir", description = "鍵ファイル格納ディレクトリ", scope = CommandLine.ScopeType.INHERIT)
	public void setSecretDir(String path) {
		config.setSecretDir(path);
	}

	@Override
	public void run() {
		// ルートコマンドだけが実行された場合に出すメッセージ（ヘルプ表示でもOK）
		log.warn("Use one of the subcommands. Try '--help' for more information.");
	}
}