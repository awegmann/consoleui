package de.codeshelf.consoleui.prompt.reader;

import org.jline.keymap.BindingReader;
import org.jline.keymap.KeyMap;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.LineReaderImpl;
import org.jline.terminal.Attributes;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;
import org.jline.utils.NonBlockingReader;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of Reader with jline.
 * <p>
 * User: Andreas Wegmann
 * Date: 02.01.16
 */
public class ConsoleReaderImpl implements Reader {
  LineReader console;

  private Set<SpecialKey> allowedSpecialKeys;
  private Set<Character> allowedPrintableKeys;
  private KeyMap map;


  public ConsoleReaderImpl() throws IOException {
    allowedPrintableKeys = new HashSet<>();
    allowedSpecialKeys = new HashSet<>();
  }

  public void setAllowedSpecialKeys(Set<SpecialKey> allowedSpecialKeys) {
    this.allowedSpecialKeys.clear();
    this.allowedSpecialKeys.addAll(allowedSpecialKeys);
  }

  public void setAllowedPrintableKeys(Set<Character> allowedPrintableKeys) {
    this.allowedPrintableKeys.clear();
    this.allowedPrintableKeys.addAll(allowedPrintableKeys);
  }

  public void addAllowedPrintableKey(Character character) {
    this.allowedPrintableKeys.add(character);
  }

  public void addAllowedSpecialKey(SpecialKey specialKey) {
    this.allowedSpecialKeys.add(specialKey);
  }

  public ReaderInput read() throws IOException {
    Terminal terminal = null;
    Attributes attributes = null;

    try {
      KeyMap map = new KeyMap();
      terminal = TerminalBuilder.terminal();
      attributes = terminal.enterRawMode();

      for (SpecialKey key : allowedSpecialKeys) {
        switch (key) {
          case UP:
            map.bind(new ReaderInput(SpecialKey.UP), map.key( terminal, InfoCmp.Capability.key_up));
            break;
          case DOWN:
            map.bind(new ReaderInput(SpecialKey.DOWN), map.key( terminal, InfoCmp.Capability.key_down));
            break;
          case BACKSPACE:
            map.bind(new ReaderInput(SpecialKey.BACKSPACE), map.key( terminal, InfoCmp.Capability.back_tab));
            break;
          case ENTER:
            map.bind(new ReaderInput(SpecialKey.ENTER), map.key( terminal, InfoCmp.Capability.key_enter));
            break;
        }
      }

      for (Character key : allowedPrintableKeys) {
        map.bind(new ReaderInput(SpecialKey.PRINTABLE_KEY, key), Character.toString(key));
      }

      NonBlockingReader nonBlockingReader = terminal.reader();
      BindingReader bindingReader = new BindingReader(nonBlockingReader);

      return (ReaderInput) bindingReader.readBinding(map);
    } finally {
      if (terminal != null && attributes != null)
        terminal.setAttributes(attributes);

    }
  }

  /**
   * Wrapper around JLine 2 library.
   *
   * @param completer List of completes to use
   * @param prompt    the text to display as prompt left side from the input
   * @param mask      optional mask character (may be used for password entry)
   * @return a ReaderInput object with results
   */
  public ReaderInput readLine(List<Completer> completer, String prompt, String value, Character mask) throws IOException {

    LineReaderBuilder builder = LineReaderBuilder.builder();

    if (completer != null) {
      for (Completer c : completer) {
        builder.completer(c);
      }
    }
    console = builder.build();
    String readLine;
    if (mask == null) {
      readLine = console.readLine(prompt);
    } else {
      readLine = console.readLine(prompt, mask);
    }


    return new ReaderInput(SpecialKey.PRINTABLE_KEY, readLine);
  }
}
