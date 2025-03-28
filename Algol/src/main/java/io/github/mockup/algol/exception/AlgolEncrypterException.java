package io.github.mockup.algol.exception;

import io.github.mockup.algol.exception.base.AlgolRuntimeException;

public class AlgolEncrypterException extends AlgolRuntimeException {
	public AlgolEncrypterException() {
	}

	public AlgolEncrypterException(String message) {
		super(message);
	}

	public AlgolEncrypterException(Throwable t) {
		super(t);
	}

	public AlgolEncrypterException(String message, Throwable t) {
		super(message, t);
	}
}
