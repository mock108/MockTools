package io.github.mockup.zenith.core.exeception;

import io.github.mockup.zenith.core.exeception.wraper.ZenithInitializeException;

/**
 * Zenith暗号化初期処理例外
 */
public class ZenithInitializeEncrypterException extends ZenithInitializeException {
	public ZenithInitializeEncrypterException() {
	}

	public ZenithInitializeEncrypterException(String message) {
		super(message);
	}

	public ZenithInitializeEncrypterException(Throwable t) {
		super(t);
	}

	public ZenithInitializeEncrypterException(String message, Throwable t) {
		super(message, t);
	}
}
