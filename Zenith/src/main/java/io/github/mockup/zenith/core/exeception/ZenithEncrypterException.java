package io.github.mockup.zenith.core.exeception;

import io.github.mockup.zenith.core.exeception.base.ZenithRuntimeException;

/**
 * Zenith暗号化例外（通常発生しないため非検査例外）
 */
public class ZenithEncrypterException extends ZenithRuntimeException {
	public ZenithEncrypterException() {
	}

	public ZenithEncrypterException(String message) {
		super(message);
	}

	public ZenithEncrypterException(Throwable t) {
		super(t);
	}

	public ZenithEncrypterException(String message, Throwable t) {
		super(message, t);
	}
}
