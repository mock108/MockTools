package io.github.mockup.algoltool.command;

import org.springframework.stereotype.Component;

import io.github.mockup.algoltool.common.config.AlgolConfig;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "algoltool", subcommands = {
		InitCommand.class,
		EncryptCommand.class,
		DecryptCommand.class
}, mixinStandardHelpOptions = true)
@Component
@RequiredArgsConstructor
public class AlgolToolCommand implements Runnable {
	private final AlgolConfig config;

	@Option(names = "--secret-dir", description = "鍵ファイル格納ディレクトリ")
	public void setSecretDir(String path) {
		config.setSecretDir(path);
	}

	@Override
	public void run() {
		// ルートコマンドだけが実行された場合に出すメッセージ（ヘルプ表示でもOK）
		System.out.println("Use one of the subcommands. Try '--help' for more information.");
	}
}