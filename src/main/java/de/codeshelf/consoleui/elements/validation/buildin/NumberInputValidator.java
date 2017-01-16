package de.codeshelf.consoleui.elements.validation.buildin;

import de.codeshelf.consoleui.elements.validation.AbstractInputValidator;
import org.apache.commons.validator.routines.AbstractNumberValidator;
import org.apache.commons.validator.routines.LongValidator;

/**
 * This class provides a validator which accepts natural numbers (Long).
 */
public class NumberInputValidator extends AbstractInputValidator {

  private AbstractNumberValidator numberValidator = LongValidator.getInstance();

  public NumberInputValidator() {
    initMessageFromResource("number_validation_error");
  }

  @Override
  public boolean isValid(String input) {
    numberValidator.isValid(input);
    return true;
  }
}
