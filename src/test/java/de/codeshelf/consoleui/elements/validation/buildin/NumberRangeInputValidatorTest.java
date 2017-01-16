package de.codeshelf.consoleui.elements.validation.buildin;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * User: ${FULL_NAME}
 * Date: 15.06.16
 */
public class NumberRangeInputValidatorTest {

  @Test
  public void testNRIV() {
    NumberRangeInputValidator validator = new NumberRangeInputValidator(1000, 2000);

    assertTrue(validator.isValid("1000"));
    assertTrue(validator.isValid("2000"));
    assertTrue(validator.isValid("1234"));
    assertFalse(validator.isValid("not a number"));
    assertFalse(validator.isValid("999"));
    assertFalse(validator.isValid(""));
  }

  @Test
  public void testNRIVNegative() {
    NumberRangeInputValidator validator = new NumberRangeInputValidator(-2000, -100);

    assertTrue(validator.isValid("-100"));
    assertTrue(validator.isValid("-2000"));
    assertTrue(validator.isValid("-123"));
    assertFalse(validator.isValid("not a number"));
    assertFalse(validator.isValid("999"));
    assertFalse(validator.isValid(""));
  }


}