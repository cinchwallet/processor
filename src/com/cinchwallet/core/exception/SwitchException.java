package com.cinchwallet.core.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * 
 * An <code>RPSException</code> indicates an exception scenario in the
 * application.
 * 
 */

public class SwitchException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new exception with null as its detail message.
     */
    public SwitchException() {
	super();
    }

    /**
     * 
     * Constructs a new exception with the specified cause and a detail message
     * of (cause==null ? null : cause.toString()) (which typically contains the
     * class and detail message of cause). This constructor is useful for
     * exceptions that are little more than wrappers for other throwables (for
     * example, PrivilegedActionException).
     * 
     * @param cause - the cause (which is saved for later retrieval by the
     *                Throwable.getCause() method). (A null value is permitted,
     *                and indicates that the cause is nonexistent or unknown.)
     */
    public SwitchException(Throwable cause) {
	super(cause);
    }

    /**
     * Constructs a new exception with the specified detail message. The cause
     * is not initialized, and may subsequently be initialized by a call to
     * Throwable.initCause(java.lang.Throwable).
     * 
     * @param message - the detail message. The detail message is saved for
     *                later retrieval by the Throwable.getMessage() method.
     */
    public SwitchException(String message) {
	super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * <p>
     * Note that the detail message associated with cause is not automatically
     * incorporated in this exception's detail message.
     * 
     * @param message - the detail message (which is saved for later retrieval
     *                by the Throwable.getMessage() method).
     * @param cause - the cause (which is saved for later retrieval by the
     *                Throwable.getCause() method). (A null value is permitted,
     *                and indicates that the cause is nonexistent or unknown.)
     */
    public SwitchException(String message, Throwable cause) {
	super(message, cause);
    }

    /**
     * Prints this throwable and its backtrace to the standard error stream.
     * This method prints a stack trace for this Throwable object on the error
     * output stream that is the value of the field System.err. The first line
     * of output contains the result of the toString() method for this object.
     */
    public void printStackTrace() {
	super.printStackTrace();
	if (getCause() != null) {
	    getCause().printStackTrace();
	}
    }

    /**
     * Prints this throwable and its backtrace to the specified print stream.
     * 
     * @param s - PrintStream to use for output
     */
    public void printStackTrace(PrintStream s) {
	super.printStackTrace(s);
	if (getCause() != null) {
	    getCause().printStackTrace(s);
	}
    }

    /**
     * Prints this throwable and its backtrace to the specified print writer.
     * 
     * @param s - PrintWriter to use for output
     */
    public void printStackTrace(PrintWriter s) {
	super.printStackTrace(s);
	if (getCause() != null) {
	    getCause().printStackTrace(s);
	}
    }
}
