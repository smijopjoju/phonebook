package my.demo.phonebook.exceptions;

public class CorreptedContactException extends RuntimeException {

    public CorreptedContactException() {
        super();
    }

    public CorreptedContactException(String message) {
        super(message);
    }

    public CorreptedContactException(Throwable cause) {
        super(cause);
    }

    public CorreptedContactException(String message, Throwable cause) {
        super(message,cause);
    }
}
