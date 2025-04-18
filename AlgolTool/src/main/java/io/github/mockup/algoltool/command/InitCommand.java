package io.github.mockup.algoltool.command;

import org.springframework.stereotype.Component;

import io.github.mockup.algoltool.service.KeyGenerationService;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine.Command;

@Component
@Command(name = "init", description = "初期鍵ファイル（RSA鍵、AES鍵、IV）を ~/.algol/secret/ に生成します", mixinStandardHelpOptions = true)
@RequiredArgsConstructor
public class InitCommand implements Runnable {

	private final KeyGenerationService keyGenerationService;

	@Override
	public void run() {
		try {
			keyGenerationService.generateAllKeys();
			System.out.println("鍵ファイルを ~/.algol/secret/ に生成しました。");
		} catch (Exception e) {
			System.err.println("鍵生成に失敗しました: " + e.getMessage());
			e.printStackTrace();
		}
	}
}