package io.github.mockup.algol.exception;

import io.github.mockup.algol.exception.base.AlgolRuntimeException;

public class AlgolJwtException extends AlgolRuntimeException {
	public AlgolJwtException() {
	}

	public AlgolJwtException(String message) {
		super(message);
	}

	public AlgolJwtException(Throwable t) {
		super(t);
	}

	public AlgolJwtException(String message, Throwable t) {
		super(message, t);
	}
}
