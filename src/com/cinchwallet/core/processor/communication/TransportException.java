package com.cinchwallet.core.processor.communication;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 */
public class TransportException extends Exception {

    public TransportException() {
        super();
    }

    public TransportException(Throwable cause) {
        super(cause);
    }

    public TransportException(String message) {
        super(message);
    }

    public TransportException(String message, Throwable cause) {
        super(message, cause);
    }

    public void printStackTrace() {
        super.printStackTrace();
        if (getCause() != null) {
            getCause().printStackTrace();
        }
    }

    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
        if (getCause() != null) {
            getCause().printStackTrace(s);
        }
    }

    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
        if (getCause() != null) {
            getCause().printStackTrace(s);
        }
    }
}
