package qlsl.androiddesign.exception;

public class SoftwareException extends Exception {

	private static final long serialVersionUID = -6821108702373842294L;

	private int statusCode = -1;

	public SoftwareException(String msg) {
		super(msg);
	}

	public SoftwareException(Exception cause) {
		super(cause);
	}

	public SoftwareException(String msg, int statusCode) {
		super(msg);
		this.statusCode = statusCode;
	}

	public SoftwareException(String msg, Exception cause) {
		super(msg, cause);
	}

	public SoftwareException(String msg, Exception cause, int statusCode) {
		super(msg, cause);
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	public SoftwareException() {
		super();
	}

	public SoftwareException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public SoftwareException(Throwable throwable) {
		super(throwable);
	}

	public SoftwareException(int statusCode) {
		super();
		this.statusCode = statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
