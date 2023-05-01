package ch.want.funnel.extension.util.fileaccess;

/**
 * A warning-only exception, to be thrown on recoverable errors only
 */
public class FileAccessWarnException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FileAccessWarnException() {
        super();
    }

    public FileAccessWarnException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FileAccessWarnException(final String message) {
        super(message);
    }

    public FileAccessWarnException(final Throwable cause) {
        super(cause);
    }
}
