package my.demo.phonebook.exceptions;

public class EmptyFileException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmptyFileException() {
        super();
    }

    public EmptyFileException(String message) {
        super(message);
    }

    public EmptyFileException(Throwable cause) {
        super(cause);
    }

    public EmptyFileException(String message, Throwable cause){
        super(message,cause);
    }
}
