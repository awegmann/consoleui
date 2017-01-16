package de.codeshelf.consoleui.elements.validation;

/**
 * User: ${FULL_NAME}
 * Date: 12.05.16
 */
public interface InputValueValidator {
  /**
   * Core validation method. Must be implemented on concrete sub class. Takes the user
   * input, validates it and returns <code>false</code> if input is not appropriate.
   *
   * @param input user input to validate
   * @return true if input is valid.
   */
  boolean isValid(String input);

  /**
   * Return the error message to display if user input is not valid.
   * @return error message for user
   */
  String getErrorMessage();

  void setErrorMessage(String errorMessage);
}
