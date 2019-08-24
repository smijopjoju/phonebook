package my.demo.phonebook.exceptions;

public class ContentReadException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ContentReadException() {
        super();
    }

    public ContentReadException(String message) {
        super(message);
    }

    public ContentReadException(Throwable cause) {
        super(cause);
    }

    public ContentReadException(String message, Throwable cause) {
        super(message,cause);
    }
}
