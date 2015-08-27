package com.cinchwallet.core.validation;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericTypeValidator;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.util.ValidatorUtils;

import com.cinchwallet.core.constant.SwitchConstants;


/**
 * This class contains basic methods for performing validation. Every Method must be defined as public static and returns boolean.
 * <p>
 * Every method has two parameter :
 * <p>
 * Object bean - instance of the object, which need to be validated.
 * <p>
 * Field field - member variable of bean, over which validation rule would be applied.
 */
public class GenericValidation {
    /**
     * Checks if the field is required.
     *
     * @param bean - object over which validation needs to be done.
     * @param field - field of the object requires to be validated.
     *
     * @return boolean - If the field isn't <code>null</code> and has a length
     *         greater than zero, returns <code>true</code>, otherwise
     *         <code>false</code>.
     */
    public static boolean validateRequired(Object bean, Field field) {
	String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	return !GenericValidator.isBlankOrNull(value);
    }

    /**
     * Checks if the field can be successfully converted to a <code>byte</code>.
     *
     *
     * @param bean - object over which validation needs to be done.
     * @param field - field of the object requires to be validated.
     * @return boolean - If the field's value can be successfully converted to a
     *         <code>byte</code> returns <code>true</code>, otherwise
     *         <code>false</code>.
     */
    public static boolean validateByte(Object bean, Field field) {
	String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	return GenericValidator.isByte(value);
    }

    /**
     * Checks if the field can be successfully converted to a <code>short</code>.
     *
     *
     * @param bean - object over which validation needs to be done.
     * @param field - field of the object requires to be validated.
     * @return boolean - If the field's value can be successfully converted to a
     *         <code>short</code> returns <code>true</code>, otherwise
     *         <code>false</code>.
     */
    public static boolean validateShort(Object bean, Field field) {
	String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	return GenericValidator.isShort(value);
    }

    /**
     * Checks if the field can be successfully converted to a <code>int</code>.
     *
     *
     * @param bean - object over which validation needs to be done.
     * @param field - field of the object requires to be validated.
     * @return boolean - If the field's value can be successfully converted to a
     *         <code>int</code> returns <code>true</code>, otherwise
     *         <code>false</code>.
     */
    public static boolean validateInt(Object bean, Field field) {
	String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	return GenericValidator.isInt(value);
    }

    /**
     * Checks if field is positive assuming it is an integer
     *
     *
     * @param bean - object over which validation needs to be done.
     * @param field - field of the object requires to be validated.
     * @return boolean - If the value is greater than zero, returns <code>true</code>, otherwise
     *         <code>false</code>.
     */
    public static boolean validatePositive(Object bean, Field field) {
	String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	return GenericTypeValidator.formatInt(value).intValue() > 0;
    }

    /**
     * Checks if the field can be successfully converted to a <code>long</code>.
     *
     *
     * @param bean - object over which validation needs to be done.
     * @param field - field of the object requires to be validated.
     * @return boolean - If the value can be successfully converted to a
     *         <code>long</code> returns <code>true</code>, otherwise
     *         <code>false</code>.
     */
    public static boolean validateLong(Object bean, Field field) {
	String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	return GenericValidator.isLong(value);
    }

    /**
     * Checks if the field can be successfully converted to a <code>float</code>.
     *
     *
     * @param bean - object over which validation needs to be done.
     * @param field - field of the object requires to be validated.
     * @return boolean - If the value can be successfully converted to a
     *         <code>float</code> returns <code>true</code>, otherwise
     *         <code>false</code>.
     */
    public static boolean validateFloat(Object bean, Field field) {
	String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	return GenericValidator.isFloat(value);
    }

    /**
     * Checks if the field can be successfully converted to a
     * <code>double</code>.
     *
     *
     * @param bean - object over which validation needs to be done.
     * @param field - field of the object requires to be validated.
     * @return boolean - If the value can be successfully converted to a
     *         <code>double</code> returns <code>true</code>, otherwise
     *         <code>false</code>.
     */
    public static boolean validateDouble(Object bean, Field field) {
	String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	return GenericValidator.isDouble(value);
    }

    /**
     * Checks if the field is an e-mail address.
     *
     *
     * @param bean - object over which validation needs to be done.
     * @param field - field of the object requires to be validated.
     * @return boolean - If the field is an valid e-mail address, returns <code>true</code>, otherwise
     *         <code>false</code>.
     */
    public static boolean validateEmail(Object bean, Field field) {
	String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	return GenericValidator.isEmail(value);
    }


    /**
     * Checks if the field is of given length.
     *
     *
     * @param bean - object over which validation needs to be done.
     * @param field - field of the object requires to be validated.
     * @return boolean If the value is of provided length, returns <code>true</code>, otherwise
     *         <code>false</code>.
     */
    public static boolean validateConstant(Object bean, Field field) {
	String value = ValidatorUtils.getValueAsString(bean, field.getProperty()).trim();
	String constantValue = field.getVarValue("constant");
	return value.length() == Integer.valueOf(constantValue);
    }

    /**
     * Checks if the field's value lies in given set of value.
     *
     *
     * @param bean - object over which validation needs to be done.
     * @param field - field of the object requires to be validated.
     * @return boolean If the value lies in given set of value, returns <code>true</code>, otherwise
     *         <code>false</code>.
     */
    public static boolean validateEnum(Object bean, Field field) {
	String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	String[] enumValue = field.getVarValue("enum").split(",");
	List<String> enumArray = Arrays.asList(enumValue);
	return enumArray.contains(value);
    }

    /**
     * Checks if the field is a valid date and comply with the provided date pattern.
     * The setLenient method is set to <code>false</code> for all.</p>
     *
     * @param bean - object over which validation needs to be done.
     * @param field - field of the object requires to be validated.
     * @return boolean  - If value of the field is a valid date and can be formatted into given pattern, returns <code>true</code>, otherwise
     *         <code>false</code>.
     */
    public static boolean validateDate(Object bean, Field field) {
	String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	String datePattern = field.getVarValue("datepattern");
	return GenericValidator.isDate(value, datePattern, false);
    }


    /**
     * Checks if the PAN provided complies with the rule given by OneInc.
     *
     * @param bean - object over which validation needs to be done.
     * @param field - field of the object requires to be validated.
     *
     * @return boolean - If the PAN isn't <code>null</code> and fulfils the
     *         requirement PAN should have, returns <code>true</code>, otherwise
     *         <code>false</code>.
     */
    public boolean validatePan(Object bean, Field field) {
	String pan = ValidatorUtils.getValueAsString(bean, field.getProperty());
	if (pan != null && (pan.trim().length() != SwitchConstants.PAN_LEN_16)) {
	    if (pan.trim().length() != SwitchConstants.PAN_LEN_19) {
		return false;
	    }
	} else if (pan != null && (pan.trim().length() != SwitchConstants.PAN_LEN_19)) {
	    if (pan.trim().length() != SwitchConstants.PAN_LEN_16) {
		return false;
	    }
	}
/*	if(pan!=null)
	    validatePANWithLUHN(pan);
*/	return true;
    }


    /**
     * Checks if the field is optional.
     *
     * @param bean - object over which validation needs to be done.
     * @param field - field of the object requires to be validated.
     *
     * @return true if the field is not present in the request. If field is
     *         there, it must be of given length.
     */
    public boolean validateOptionalConstant(Object bean, Field field) {
	boolean flag = true;
	String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	if (value == null)
	    return flag;
	String fieldLength = field.getVarValue("constant");
	flag = validateConstant(value.trim(), fieldLength);
	return flag;
    }


    /**
     * Checks the length of value.
     *
     * @param value - String value whose length needs to be check
     * @param fieldLength - field length
     * @return true if value given is of length fieldLength.
     */
    private boolean validateConstant(String value, String fieldLength) {
	return value.length() == Integer.valueOf(fieldLength);
    }


    public boolean optionalAmount(Object bean, Field field) {
	boolean flag = true;
	String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	if (value == null)
	    return flag;
	return GenericValidator.isDouble(value);
    }

    public boolean validateVariableLengthfield(Object bean, Field field) {
	String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	String fieldLength = field.getVarValue("length");
	return value.length() <= Integer.valueOf(fieldLength);
    }

    public boolean optionalDate(Object bean, Field field) {
	boolean flag = true;
	String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	if (value == null)
	    return flag;
	String datePattern = field.getVarValue("datepattern");
	return GenericValidator.isDate(value, datePattern, false);
    }

}
