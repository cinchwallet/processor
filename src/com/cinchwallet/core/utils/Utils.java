package com.cinchwallet.core.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;
import org.jpos.space.LocalSpace;
import org.jpos.space.Space;

import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.exception.SwitchException;
import com.cinchwallet.core.msg.IMFResponseCodes;


/**
 *
 * <code>Utils</code> defines a set of methods that perform common,
 * often re-used functions. Like other utility classes,
 * <code>RadicalUtils</code> also define these common methods under static
 * scope, which does not require instantiation the class before using the
 * method.
 *
 */
public class Utils {
    private static Map<String, String> terminalProvMap = new HashMap<String, String>();

    private static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");

    public static final SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Logs the entire request (leg 2) to be send to processor in a log file,
     * along with the current date.
     *
     * @param processorSpecfication
     * @param payLoad
     */
    public static void persistProcessorNetworkLogger(String processorSpecfication, String payLoad) {
	try {
	    StringBuilder log = new StringBuilder();
	    log.append(processorSpecfication);
	    log.append("::");
	    log.append("::");
	    DateFormat dateFormat = new SimpleDateFormat("MMddyyyy-hhmmss");
	    log.append(dateFormat.format(new Date()));
	    log.append("::");
	    log.append(payLoad);
	    CWLogger.appLog.info(log.toString());
	} catch (Exception _ex) {
	    CWLogger.appLog.error(_ex.getMessage(), _ex);
	}
    }

    /**
     * Write the request payload before sending it to processor/client, along
     * with current date and time.
     *
     * @param payload
     * @param source
     * @param specfication
     */
    public static void persistRAPNetworkLogger(String payload, String source, String specfication) {
	try {
	    StringBuilder log = new StringBuilder();
	    log.append(specfication);
	    log.append("::");
	    log.append(source);
	    log.append("::");
	    DateFormat dateFormat = new SimpleDateFormat("MMddyyyy-hhmmss");
	    log.append(dateFormat.format(new Date()));
	    log.append("::");
	    log.append(payload);
	    CWLogger.appLog.info(log.toString());
	} catch (Exception _ex) {
	    CWLogger.appLog.error(_ex.getMessage(), _ex);
	}
    }

    /**
     * Removes the special or unwanted characters with the blank character.
     *
     * @param pString - with some special characters
     * @return string after removing the special characters with the blank
     *         string.
     */
    public static String cleanString(String pString) {
	if (pString != null)
	    return pString.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "");
	else
	    return null;
    }

    /**
     * Checks whether given string is null or of length zero.
     *
     * @param string
     * @return true if given string is null or its length is zero.
     */
    public static boolean isEmpty(String string) {
	return string == null || string.trim().length() == 0;
    }

    /**
     * Converts the given Hexadecimal value into Bytes.
     *
     * @param str - String containing Hexadecimal value.
     * @return array of bytes - bytes equivalent of the given hexadecimal value.
     */
    public static byte[] hexToBytes(String str) {
	if (str == null) {
	    return null;
	} else if (str.length() < 2) {
	    return null;
	} else {
	    int len = str.length() / 2;
	    byte[] buffer = new byte[len];
	    for (int i = 0; i < len; i++) {
		buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
	    }
	    return buffer;
	}

    }

    /**
     * Converts the given Bytes into Hexadecimal value.
     *
     * @param array of bytes - bytes to be converted into HexaDecimal value.
     * @return string - HexaDecimal equivalent of the given Byte.
     */
    public static String bytesToHex(byte[] data) {
	if (data == null) {
	    return null;
	} else {
	    int len = data.length;
	    String str = "";
	    for (int i = 0; i < len; i++) {
		if ((data[i] & 0xFF) < 16)
		    str = str + "0" + java.lang.Integer.toHexString(data[i] & 0xFF);
		else
		    str = str + java.lang.Integer.toHexString(data[i] & 0xFF);
	    }
	    return str.toUpperCase();
	}
    }

    /**
     * Returns a string representation of the integer argument as an unsigned
     * integer in base&nbsp;16.
     * <p>
     * The unsigned integer value is the argument plus 2<sup>32</sup> if the
     * argument is negative; otherwise, it is equal to the argument. This value
     * is converted to a string of ASCII digits in hexadecimal (base&nbsp;16)
     * with no extra leading <code>0</code>s. If the unsigned magnitude is
     * zero, it is represented by a single zero character <code>'0'</code> (<code>'&#92;u0030'</code>);
     * otherwise, the first character of the representation of the unsigned
     * magnitude will not be the zero character. The following characters are
     * used as hexadecimal digits: <blockquote>
     *
     * <pre>
     * 0123456789abcdef
     * </pre>
     *
     * </blockquote> These are the characters <code>'&#92;u0030'</code>
     * through <code>'&#92;u0039'</code> and <code>'&#92;u0061'</code>
     * through <code>'&#92;u0066'</code>. If uppercase letters are desired,
     * the {@link java.lang.String#toUpperCase()} method may be called on the
     * result: <blockquote>
     *
     * <pre>
     * Integer.toHexString(n).toUpperCase()
     * </pre>
     *
     * </blockquote>
     *
     * @param i an integer to be converted to a string.
     * @return the string representation of the unsigned integer value
     *         represented by the argument in hexadecimal (base&nbsp;16).
     */
    public static String integerToHex(int i) {
	return Integer.toHexString(i);
    }

    public static String getFieldSeperator() {
	int fieldSeperator = 0x1c;
	return integerToHex(fieldSeperator);
    }

    /**
     * Produce the string of a desired length either by padding it with some
     * pre-defined character or by trimming the input string.
     *
     *
     * @param fillIn - input string required to be padded to make it of a fixed
     *                length.
     * @param totalLenght - total length of output string.
     * @param toFill - character to be padded.
     * @return string of length defined of value <i>totalLength<i>
     * @throws ISOException
     */
    public static String fillChars(String fillIn, int totalLenght, char toFill) throws ISOException {
	if (fillIn != null) {
	    if (fillIn.trim().length() < totalLenght) {
		fillIn = ISOUtil.padleft(fillIn, totalLenght, toFill);
	    } else if (fillIn.trim().length() > totalLenght) {
		fillIn = fillIn.substring(fillIn.trim().length() - totalLenght);
	    }
	}
	return fillIn;
    }

    public static Map<String, String> getTerminalProvMap() {
	return terminalProvMap;
    }

    public static void setTerminalProvMap(Map<String, String> pTerminalProvMap) {
	terminalProvMap = pTerminalProvMap;
    }

    /**
     * Returns the size of the given queue in specified space.
     *
     * @param sp - Acquirer Space
     * @param queue - Acquirer queue name.
     * @return size of the given queue.
     */
    public static int getAquirerQueueSize(Space<String, TransContext> sp, String queue) {
	if (sp instanceof LocalSpace)
	    return ((LocalSpace<String, TransContext>) sp).size(queue);
	return -1;
    }

    public static Date getTransactionDateTimeAsCurrentTime() throws SwitchException {
	try {
	    Calendar cal = Calendar.getInstance(); // today
	    return yyyyMMddHHmmss.parse(yyyyMMddHHmmss.format(cal.getTime()));
	} catch (Exception e) {
	    throw new SwitchException("INVALID TRANSACTION DATE TIME");
	}
    }

}
