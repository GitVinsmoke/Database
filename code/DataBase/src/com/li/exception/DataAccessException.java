package com.li.exception;

/*自定义的异常，并生成来自父类的构造方法*/

public class DataAccessException extends Exception {

	public DataAccessException() {
		// TODO Auto-generated constructor stub
	}

	public DataAccessException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public DataAccessException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public DataAccessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public DataAccessException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

}
