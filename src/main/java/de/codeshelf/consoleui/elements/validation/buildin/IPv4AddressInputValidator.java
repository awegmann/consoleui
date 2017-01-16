package de.codeshelf.consoleui.elements.validation.buildin;

import de.codeshelf.consoleui.elements.validation.AbstractInputValidator;
import org.apache.commons.validator.routines.InetAddressValidator;

/**
 * This clas provides an input validator for IPv4 address.
 */
public class IPv4AddressInputValidator extends AbstractInputValidator {
  /**
   * Basic constructor which takes the error massage to display if user input is not valid.
   *
   * @param errorMessage message to display on invalid user input
   */
  public IPv4AddressInputValidator(String errorMessage) {
    super(errorMessage);
  }

  public IPv4AddressInputValidator() {
    super();
    initMessageFromResource("ipv4_validation_error");
  }

  @Override
  public boolean isValid(String input) {
    InetAddressValidator inetAddressValidator = InetAddressValidator.getInstance();
    return inetAddressValidator.isValid(input);
  }
}
