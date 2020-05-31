package org.elasticsearch2sql.exception;

import java.io.Serializable;

public class SqlParseException extends Exception implements Serializable {

	private static final long serialVersionUID = -529312788708024761L;

	public SqlParseException(String message) {
		super(message);
	}



}
