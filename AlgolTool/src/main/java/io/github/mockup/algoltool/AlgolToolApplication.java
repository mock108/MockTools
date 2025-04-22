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

@SpringBootApplication
public class AlgolToolApplication implements CommandLineRunner, ExitCodeGenerator {

	private final IFactory factory;
	private final AlgolToolCommand rootCommand;
	private int exitCode;

	public AlgolToolApplication(IFactory factory, AlgolToolCommand rootCommand) {
		this.factory = factory;
		this.rootCommand = rootCommand;
	}

	@Override
	public void run(String... args) {
		String[] filtered = Arrays.stream(args)
				.filter(arg -> !arg.startsWith("--spring."))
				.toArray(String[]::new);
		// コマンドライン引数を解析して実行
		exitCode = new CommandLine(rootCommand, factory).execute(filtered);
	}

	@Override
	public int getExitCode() {
		return exitCode;
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AlgolToolApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.setDefaultProperties(Collections.singletonMap("spring.jmx.enabled", "false"));
		// Springコンテキスト起動 → CLI実行 → 終了コードを返す
		System.exit(SpringApplication.exit(app.run(args)));
	}
}
