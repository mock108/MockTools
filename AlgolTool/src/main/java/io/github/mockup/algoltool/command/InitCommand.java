package io.github.mockup.algoltool.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.github.mockup.algoltool.service.KeyGenerationService;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine.Command;

@Component
@Command(name = "init", description = "初期鍵ファイル（RSA鍵、AES鍵、IV）を ~/.algol/secret/ に生成します", mixinStandardHelpOptions = true)
@RequiredArgsConstructor
public class InitCommand implements Runnable {
	/** ロガー */
	private static final Logger log = LoggerFactory.getLogger(InitCommand.class);

	private final KeyGenerationService keyGenerationService;

	@Override
	public void run() {
		try {
			keyGenerationService.generateAllKeys();
		} catch (Exception e) {
			log.error("鍵生成に失敗しました", e);
		}
	}
}