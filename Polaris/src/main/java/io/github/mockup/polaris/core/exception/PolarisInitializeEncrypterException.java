package io.github.mockup.polaris.core.exception;

import io.github.mockup.polaris.core.exception.wraper.PolarisInitializeException;

/**
 * Polaris暗号化初期処理例外
 */
public class PolarisInitializeEncrypterException extends PolarisInitializeException {
	public PolarisInitializeEncrypterException() {
	}

	public PolarisInitializeEncrypterException(String message) {
		super(message);
	}

	public PolarisInitializeEncrypterException(Throwable t) {
		super(t);
	}

	public PolarisInitializeEncrypterException(String message, Throwable t) {
		super(message, t);
	}
}
