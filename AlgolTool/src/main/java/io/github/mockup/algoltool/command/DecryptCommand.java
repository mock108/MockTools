package io.github.mockup.algoltool.command;

import org.springframework.stereotype.Component;

import io.github.mockup.algoltool.service.EncryptionService;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Component
@Command(
    name = "decrypt",
    description = "指定文字列をAESで復号します（Base64入力）",
    mixinStandardHelpOptions = true
)
@RequiredArgsConstructor
public class DecryptCommand implements Runnable {

    private final EncryptionService encryptionService;

    @Option(names = {"-t", "--text"}, required = true, description = "Base64形式の暗号化文字列")
    private String encryptedText;

    @Override
    public void run() {
        try {
            String decrypted = encryptionService.decryptText(encryptedText);
            System.out.println(decrypted);
        } catch (Exception e) {
            System.err.println("復号に失敗しました: " + e.getMessage());
            e.printStackTrace();
        }
    }
}