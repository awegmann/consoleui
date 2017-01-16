package de.codeshelf.consoleui.prompt.builder;

import de.codeshelf.consoleui.elements.InputValue;
import de.codeshelf.consoleui.elements.validation.InputValueValidator;
import jline.console.completer.Completer;

import java.util.ArrayList;

/**
 * Created by Andreas Wegmann on 22.01.16.
 */
public class InputValueBuilder {
  private final PromptBuilder promptBuilder;
  private String name;
  private String defaultValue;
  private String message;
  private Character mask;
  private ArrayList<Completer> completers;
  private InputValueValidator validator;

  public InputValueBuilder(PromptBuilder promptBuilder) {
    this.promptBuilder = promptBuilder;
  }

  public InputValueBuilder name(String name) {
    this.name = name;
    return this;
  }

  public InputValueBuilder defaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
    return this;
  }

  public InputValueBuilder message(String message) {
    this.message = message;
    return this;
  }

  public InputValueBuilder addCompleter(Completer completer) {
    if (completers == null) {
      completers = new ArrayList<Completer>();
    }
    this.completers.add(completer);
    return this;
  }

  public InputValueBuilder addValidator(InputValueValidator inputValidator) {
    this.validator = inputValidator;
    return this;
  }

  public InputValueBuilder mask(char mask) {
    this.mask = mask;
    return this;
  }

  public PromptBuilder addPrompt() {
    InputValue inputValue = new InputValue(name, message, defaultValue, defaultValue);
    if (completers != null) {
      inputValue.setCompleter(completers);
    }
    if (mask != null) {
      inputValue.setMask(mask);
    }
    if (validator != null) {
      inputValue.setValidator(validator);
    }
    promptBuilder.addPrompt(inputValue);
    return promptBuilder;
  }

}
