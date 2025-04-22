package io.github.mockup.algoltool.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.github.mockup.algoltool.service.KeyGenerationService;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine.Command;

/**
 * {@code InitCommand} は、AlgolTool に必要な初期鍵（RSA 秘密鍵・公開鍵、AES 鍵、IV）を
 * 指定ディレクトリ（デフォルト: {@code ~/.algol/secret/}）に生成する CLI サブコマンドです。
 * <p>
 * このコマンドは鍵ファイルが存在しない初回利用時や、鍵の再生成を行う際に使用します。
 * </p>
 */
@Component
@Command(name = "init", description = "初期鍵ファイル（RSA鍵、AES鍵、IV）を ~/.algol/secret/ に生成します")
@RequiredArgsConstructor
public class InitCommand implements Runnable {

	/** ロガー */
	private static final Logger log = LoggerFactory.getLogger(InitCommand.class);

	/** 鍵生成サービス */
	private final KeyGenerationService keyGenerationService;

	/**
	 * {@inheritDoc}
	 * <p>
	 * 鍵生成処理を実行し、成功または失敗をログ出力します。
	 * </p>
	 */
	@Override
	public void run() {
		try {
			keyGenerationService.generateAllKeys();
		} catch (Exception e) {
			log.error("鍵生成に失敗しました", e);
		}
	}
}
