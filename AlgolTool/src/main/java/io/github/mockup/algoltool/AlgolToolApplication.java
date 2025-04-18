package io.github.mockup.algoltool;

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
        // コマンドライン引数を解析して実行
        exitCode = new CommandLine(rootCommand, factory).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }

    public static void main(String[] args) {
        // Springコンテキスト起動 → CLI実行 → 終了コードを返す
        System.exit(SpringApplication.exit(SpringApplication.run(AlgolToolApplication.class, args)));
    }
}
