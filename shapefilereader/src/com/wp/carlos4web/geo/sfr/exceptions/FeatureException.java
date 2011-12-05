package com.wp.carlos4web.geo.sfr.exceptions;

/**
 * Classe para representar as exceções para a leitura de features.
 * 
 * @author Carlos A. Junior (CIH - Centro Internacional de Hidroinformática)
 */
public class FeatureException extends Exception
{
	private static final long serialVersionUID = 5750093545039120374L;

	public FeatureException() {
		super();
	}

	public FeatureException(String message, Throwable cause) {
		super(message, cause);
	}

	public FeatureException(String message) {
		super(message);
	}

	public FeatureException(Throwable cause) {
		super(cause);
	}
}
