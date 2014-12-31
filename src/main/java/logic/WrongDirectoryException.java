package logic;

public class WrongDirectoryException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public WrongDirectoryException(String message) {
        super(message);
    }

    public WrongDirectoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
