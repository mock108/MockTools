package io.github.mockup.polaris.core.exception;

import io.github.mockup.polaris.core.exception.base.PolarisRuntimeException;

/**
 * Polaris暗号化例外（通常発生しないため非検査例外）
 */
public class PolarisEncrypterException extends PolarisRuntimeException {
	public PolarisEncrypterException() {
	}

	public PolarisEncrypterException(String message) {
		super(message);
	}

	public PolarisEncrypterException(Throwable t) {
		super(t);
	}

	public PolarisEncrypterException(String message, Throwable t) {
		super(message, t);
	}
}
