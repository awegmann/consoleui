package de.codeshelf.consoleui.prompt;

import de.codeshelf.consoleui.elements.InputValue;
import de.codeshelf.consoleui.elements.validation.InputValueValidator;
import de.codeshelf.consoleui.prompt.reader.ConsoleReaderImpl;
import de.codeshelf.consoleui.prompt.reader.ReaderIF;
import de.codeshelf.consoleui.prompt.renderer.CUIRenderer;
import jline.console.completer.Completer;
import org.fusesource.jansi.Ansi;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Implementation of the input choice prompt. The user will be asked for a string input value.
 * With support of completers an automatic expansion of strings and filenames can be configured.
 * Defining a mask character, a password like input is possible.
 * <p>
 * User: Andreas Wegmann<p>
 * Date: 06.01.16
 */
public class InputPrompt extends AbstractPrompt implements PromptIF<InputValue, InputResult> {

  private InputValue inputElement;
  private ReaderIF reader;
  CUIRenderer itemRenderer = CUIRenderer.getRenderer();

  /**
   * Default constructor.
   *
   * @throws IOException
   */
  public InputPrompt() throws IOException {
  }


  public InputResult prompt(InputValue inputElement) throws IOException {
    this.inputElement = inputElement;

    if (reader == null) {
      reader = new ConsoleReaderImpl();
    }

    if (renderHeight == 0) {
      renderHeight = 1;
    } else {
      System.out.println(ansi().cursorUp(renderHeight));
    }

    String prompt = renderMessagePrompt(this.inputElement.getMessage()) + itemRenderer.renderOptionalDefaultValue(this.inputElement);


    //System.out.print(prompt + itemRenderer.renderValue(this.inputElement));
    //System.out.flush();
    List<Completer> completer = inputElement.getCompleter();
    Character mask = inputElement.getMask();
    boolean valid = false;
    String lineInput = inputElement.getValue();
    do {
      ReaderIF.ReaderInput readerInput = reader.readLine(completer, prompt, lineInput, mask);

      lineInput = readerInput.getLineInput();

      if (inputElement.getValidator() != null) {
        if (lineInput != null && lineInput.trim().length() > 0) {
          valid = inputElement.getValidator().isValid(lineInput);
          if (!valid) {
            renderErrorMessage(inputElement.getValidator().getErrorMessage());
          }
        }
      } else {
        valid = true;
      }
    } while (!valid);


    if (lineInput == null || lineInput.trim().length() == 0) {
      lineInput = inputElement.getDefaultValue();
    }

    String result;
    if (mask == null) {
      result = lineInput;
    } else {
      result = "";
      if (lineInput != null) {
        for (int i = 0; i < lineInput.length(); i++) {
          result += mask;
        }
      }
    }

    renderMessagePromptAndResult(inputElement.getMessage(), result);

    return new InputResult(lineInput);
  }

  private void renderErrorMessage(String errorMessage) {
    System.out.print(ansi().fg(Ansi.Color.RED).a("!!>  ").reset().a(errorMessage).eraseLine().cursorLeft(Integer.MAX_VALUE));
    System.out.print(ansi().cursorUp(1));
    System.out.flush();
    renderHeight = 1;
  }
}
