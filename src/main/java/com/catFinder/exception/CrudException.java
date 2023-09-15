package com.catFinder.exception;

public class CrudException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CrudException(String msg) {
		super(msg);
	}
}