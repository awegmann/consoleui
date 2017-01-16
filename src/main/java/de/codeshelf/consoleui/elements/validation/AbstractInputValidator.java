package de.codeshelf.consoleui.elements.validation;

import java.util.ResourceBundle;

/**
 * Abstract base class for validating user input.
 */
public abstract class AbstractInputValidator implements InputValueValidator {

  protected final ResourceBundle resourceBundle;
  /**
   * Error message to display
   */
  private String errorMessage;

  @Override
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * Set error message.
   */
  @Override
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
   * Basic constructor which takes the error massage to display if user input is not valid.
   *
   * @param errorMessage message to display on invalid user input
   */
  public AbstractInputValidator(String errorMessage) {
    this();
    this.errorMessage = errorMessage;
  }

  /**
   * Default constructor.
   */
  public AbstractInputValidator() {
    resourceBundle = ResourceBundle.getBundle("consoleui_messages");
  }

  protected void initMessageFromResource(String errorKey) {
      this.setErrorMessage(resourceBundle.getString(errorKey).trim());
  }
}
