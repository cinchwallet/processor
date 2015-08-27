package com.cinchwallet.core.exception;

/**
 *
 * An <code>OneIncValidationException</code> indicates that inappropriate request
 * was encountered. This often means that the data is invalid in and of itself,
 * from the perspective of the Specification.
 * <p>
 * An example would be an undefined or null mti/function code. However, the
 * exception might simply mean that the data was invalid in the context it was
 * used, or that the object to which the data was given was unable to parse or
 * use it. For example, system might not able to parse the amount provided in
 * field 4 of the request.
 *
 */
public class CWValidationException extends SwitchException {
    private static final long serialVersionUID = 1L;
    private String            m_irc;

    /**
     * Constructs a new exception with the specified detail message and irc.
     *
     * @param message - the detail message (which is saved for later retrieval
     *                by the Throwable.getMessage() method).
     * @param irc - the IRC which indicate the code for system failure.
     *
     */
    public CWValidationException(String message, String irc) {
	super("IRC: " + irc + " - " + message);
	m_irc = irc;
    }

    /**
     * Constructs a new exception with the specified detail message, irc and cause.
     * <p>
     * Note that the detail message associated with cause is not automatically
     * incorporated in this exception's detail message.
     *
     * @param message - the detail message (which is saved for later retrieval
     *                by the Throwable.getMessage() method).
     * @param irc - the IRC which indicate the code for system failure.
     * @param cause - the cause (which is saved for later retrieval by the
     *                Throwable.getCause() method). (A null value is permitted,
     *                and indicates that the cause is nonexistent or unknown.)
     */
    public CWValidationException(String message, String irc, Throwable cause) {
	super("IRC: " + irc + " - " + message, cause);
	m_irc = irc;
    }

    public String getIRC() {
	return m_irc;
    }

    public void setIRC(final String irc) {
	m_irc = irc;
    }
}
