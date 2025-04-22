package io.github.mockup.algoltool.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.github.mockup.algoltool.service.EncryptionService;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Component
@Command(
    name = "encrypt",
    description = "指定文字列をAESで暗号化してBase64出力します",
    mixinStandardHelpOptions = true
)
@RequiredArgsConstructor
public class EncryptCommand implements Runnable {
	/** ロガー */
	private static final Logger log = LoggerFactory.getLogger(EncryptCommand.class);

    private final EncryptionService encryptionService;

    @Option(names = {"-t", "--text"}, required = true, description = "暗号化対象の文字列")
    private String inputText;

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