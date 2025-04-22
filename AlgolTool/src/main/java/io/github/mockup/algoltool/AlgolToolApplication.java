package io.github.mockup.algoltool;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.mockup.algoltool.command.AlgolToolCommand;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

/**
 * {@code AlgolToolApplication} は、Spring Boot と Picocli による CLI アプリケーションのエントリポイントです。
 * <p>
 * Spring の DI 機能を活かしながら、Picocli によるコマンドラインインターフェースを構築し、
 * 暗号化ユーティリティツールとして機能します。
 * </p>
 */
@SpringBootApplication
public class AlgolToolApplication implements CommandLineRunner, ExitCodeGenerator {

	/** Picocli のコンポーネントファクトリ（Spring と連携） */
	private final IFactory factory;

	/** ルートコマンドとなる AlgolToolCommand */
	private final AlgolToolCommand rootCommand;

	/** 終了コード */
	private int exitCode;

	/**
	 * コンストラクタ
	 *
	 * @param factory     Picocli に渡す Spring 対応ファクトリ
	 * @param rootCommand アプリケーションのルート CLI コマンド
	 */
	public AlgolToolApplication(IFactory factory, AlgolToolCommand rootCommand) {
		this.factory = factory;
		this.rootCommand = rootCommand;
	}

	/**
	 * Spring Boot 起動後に実行される CLI エントリポイントです。
	 *
	 * @param args コマンドライン引数
	 */
	@Override
	public void run(String... args) {
		String[] filtered = Arrays.stream(args)
				.filter(arg -> !arg.startsWith("--spring."))
				.toArray(String[]::new);
		// Picocli に引数を渡してコマンドを実行
		exitCode = new CommandLine(rootCommand, factory).execute(filtered);
	}

	/**
	 * Spring Boot の終了コードを取得します。
	 *
	 * @return コマンド実行結果による終了コード
	 */
	@Override
	public int getExitCode() {
		return exitCode;
	}

	/**
	 * アプリケーションのメインメソッド。
	 * Spring Boot アプリケーションをバナーなし、JMX 無効で起動します。
	 *
	 * @param args コマンドライン引数
	 */
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AlgolToolApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.setDefaultProperties(Collections.singletonMap("spring.jmx.enabled", "false"));
		System.exit(SpringApplication.exit(app.run(args)));
	}
}
