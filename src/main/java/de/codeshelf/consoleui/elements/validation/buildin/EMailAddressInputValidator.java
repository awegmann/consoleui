package de.codeshelf.consoleui.elements.validation.buildin;

import de.codeshelf.consoleui.elements.validation.AbstractInputValidator;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * Input validator for e-mail address.
 */
public class EMailAddressInputValidator extends AbstractInputValidator {
  /**
   * Basic constructor which takes the error massage to display if user input is not valid.
   *
   * @param errorMessage message to display on invalid user input
   */
  public EMailAddressInputValidator(String errorMessage) {
    super(errorMessage);

  }

  public EMailAddressInputValidator() {
    super();
    initMessageFromResource("email_validation_error");
  }

  @Override
  public boolean isValid(String input) {
    EmailValidator emailValidator= EmailValidator.getInstance();
    return emailValidator.isValid(input);
  }
}
