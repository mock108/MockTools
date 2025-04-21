package io.github.mockup.algoltool.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.mockup.algoltool.common.config.AlgolConfig;

@SpringBootTest
public class EncryptionServiceTest {

    @Autowired
    EncryptionService encryptionService;

    @Autowired
    AlgolConfig config;

    @Test
    void encryptAndDecrypt_shouldReturnOriginalText() {
        String original = "test-あいうえお-123";
        String encrypted = encryptionService.encryptText(original);
        String decrypted = encryptionService.decryptText(encrypted);

        assertNotNull(encrypted);
        assertNotEquals(original, encrypted); // 暗号化結果は違う文字列
        assertEquals(original, decrypted);    // 復号で元に戻る
    }
}