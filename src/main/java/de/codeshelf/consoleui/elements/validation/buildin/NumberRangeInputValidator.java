package de.codeshelf.consoleui.elements.validation.buildin;

import de.codeshelf.consoleui.elements.validation.AbstractInputValidator;
import org.apache.commons.validator.routines.LongValidator;

import java.text.MessageFormat;

/**
 * This class provides a validator that accepts natural numbers in a given range.
 */
public class NumberRangeInputValidator extends AbstractInputValidator {

  private LongValidator numberValidator = LongValidator.getInstance();
  private long lowerBorder;
  private long upperBorder;


  @Override
  protected void initMessageFromResource(String errorKey) {
    this.setErrorMessage(MessageFormat.format(resourceBundle.getString(errorKey),lowerBorder,upperBorder).trim());

  }

  public NumberRangeInputValidator(long lowerBorder, long upperBorder) {
    this.lowerBorder = lowerBorder;
    this.upperBorder = upperBorder;
    initMessageFromResource("number_with_range_validation_error");
  }

  @Override
  public boolean isValid(String input) {
    return numberValidator.isValid(input) && numberValidator.isInRange(Long.valueOf(input),lowerBorder,upperBorder);
  }

  @Override
  public String getErrorMessage() {
    return null;
  }

  @Override
  public void setErrorMessage(String errorMessage) {

  }
}
