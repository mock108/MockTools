package io.github.mockup.algol.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

public class AlgolEncryptFileUtil {
	public static void writeBytes(byte[] b, String path) throws IOException {
		try (OutputStream o = new FileOutputStream(path)) {
			o.write(Base64.getEncoder().encode(b));
		}
	}

	public static byte[] readBytes(String path) throws IOException {
		try (InputStream i = new FileInputStream(path)) {
			return Base64.getDecoder().decode(i.readAllBytes());
		}
	}
}
