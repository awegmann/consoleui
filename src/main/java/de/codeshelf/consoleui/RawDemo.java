package de.codeshelf.consoleui;

import org.jline.keymap.BindingReader;
import org.jline.keymap.KeyMap;
import org.jline.terminal.Attributes;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;
import org.jline.utils.NonBlockingReader;

import java.io.IOException;

enum Action {
  Up, Left, Right, Down,
  Quit, ESC
};

/**
 * Author: Andreas Wegmann
 * Date: 2019-06-10
 */
public class RawDemo {
  public static void main(String[] args) throws IOException {

    Terminal terminal = TerminalBuilder.terminal();

    Attributes attributes = terminal.enterRawMode();
    NonBlockingReader nonBlockingReader = terminal.reader();
    BindingReader bindingReader = new BindingReader(nonBlockingReader);

    KeyMap map = new KeyMap();
    String key = map.key(terminal, InfoCmp.Capability.key_left);

    //map.bind(Action.ESC, "\033");
    map.bind(Action.Up, "\033[A");
    map.bind(Action.Up, terminal.getStringCapability(InfoCmp.Capability.cursor_up));
    map.bind(Action.Up, "e");
    map.bind(Action.Up, "E");
    map.bind(Action.Left, "\033[D");
    map.bind(Action.Left, terminal.getStringCapability(InfoCmp.Capability.cursor_left));
    map.bind(Action.Left, "s");
    map.bind(Action.Left, "S");
    map.bind(Action.Right, "\033[C");
    map.bind(Action.Right, terminal.getStringCapability(InfoCmp.Capability.cursor_right));
    map.bind(Action.Right, "f");
    map.bind(Action.Right, "F");
    map.bind(Action.Down, "\033[B");
    map.bind(Action.Down, terminal.getStringCapability(InfoCmp.Capability.cursor_down));
    map.bind(Action.Down, "c");
    map.bind(Action.Down, "C");
    map.bind(Action.Quit, "q");
    map.setAmbiguousTimeout(50);


    Object o = null;
    while (Action.Quit != o) {
      o = bindingReader.readBinding(map);
      System.out.println("(int)c = " + o.toString());
    }
    terminal.setAttributes(attributes);
  }
}
