package de.codeshelf.consoleui.prompt.reader;


import org.jline.reader.Completer;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Interface for Reader implementation. The Reader encapsulates the reading of single character strokes as well as
 * the reading of a line (via jline).
 * <p>
 * User: Andreas Wegmann
 * Date: 02.01.16
 */
public interface Reader {

  enum SpecialKey {
    UP,
    DOWN,
    ENTER,
    BACKSPACE,
    PRINTABLE_KEY,  // not really a special key, but indicates an ordinary printable key
  }

  void setAllowedSpecialKeys(Set<SpecialKey> allowedSpecialKeys);

  void setAllowedPrintableKeys(Set<Character> allowedPrintableKeys);

  /**
   * Register a single character in the allowed key set. This is used to register keystrokes for menu shortcuts with
   * the {@link #read()} method.
   *
   * @param character character to be allowed.
   */
  void addAllowedPrintableKey(Character character);

  /**
   * Register a special key (for example up, down, left and right) as allowed key for the {@link #read()} method.
   *
   * @param specialKey special key to allow
   */
  void addAllowedSpecialKey(SpecialKey specialKey);

  /**
   * Perform a single key read operation.
   *
   * @return ReaderInput result of the read operation. Contains ReaderInput#specialKey or ReaderInput#printableKey
   * after a single key read.
   */
  ReaderInput read() throws IOException;

  /**
   * Read a string with link input (via jline).
   *
   * @param completer a list of completers to use
   * @param prompt    the prompt printed before the input field
   * @param value     a value to be set in the input field (to be edited)
   * @param mask      a masking character to be used as printed for each entered key (used for password entry fields)
   * @return ReaderInput as result of input
   * @throws IOException
   */
  ReaderInput readLine(List<Completer> completer, String prompt, String value, Character mask) throws IOException;

  class ReaderInput {
    private SpecialKey specialKey;
    private Character printableKey;
    private String lineInput;

    /**
     * Constructor with SpecialKey.
     *
     * @param specialKey initial value
     */
    public ReaderInput(SpecialKey specialKey) {
      this.specialKey = specialKey;
    }

    public ReaderInput(SpecialKey specialKey, Character printableKey) {
      this.specialKey = specialKey;
      this.printableKey = printableKey;
    }

    public ReaderInput(SpecialKey specialKey, String lineInput) {
      this.specialKey = specialKey;
      this.lineInput = lineInput;
    }

    public SpecialKey getSpecialKey() {
      return specialKey;
    }

    public Character getPrintableKey() {
      return printableKey;
    }

    public String getLineInput() {
      return lineInput;
    }
  }
}
