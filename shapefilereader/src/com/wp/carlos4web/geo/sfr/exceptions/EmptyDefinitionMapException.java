package com.wp.carlos4web.geo.sfr.exceptions;

/**
 * Classe para representar as exceções para a falta de definições.
 * 
 * @author Carlos A. Junior (CIH - Centro Internacional de Hidroinformática)
 */
public class EmptyDefinitionMapException extends Exception
{
	private static final long serialVersionUID = 5750093545039120374L;

	public EmptyDefinitionMapException() {
		super();
	}

	public EmptyDefinitionMapException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyDefinitionMapException(String message) {
		super(message);
	}

	public EmptyDefinitionMapException(Throwable cause) {
		super(cause);
	}
}
