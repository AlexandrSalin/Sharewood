package com.dub.sharewood.exceptions;



public class UnexpectedServerException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public UnexpectedServerException(String message) {
		super(message);
	}
	
}
