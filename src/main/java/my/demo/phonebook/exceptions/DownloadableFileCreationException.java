package my.demo.phonebook.exceptions;

public class DownloadableFileCreationException extends RuntimeException {

    public DownloadableFileCreationException() {
        super();
    }

    public DownloadableFileCreationException(String message) {
        super(message);
    }

    public DownloadableFileCreationException(Throwable cause) {
        super(cause);
    }

    public DownloadableFileCreationException(String message, Throwable cause) {
        super(message,cause);
    }
}
