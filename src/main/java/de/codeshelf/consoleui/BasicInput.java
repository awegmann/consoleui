package de.codeshelf.consoleui;

import de.codeshelf.consoleui.elements.validation.buildin.EMailAddressInputValidator;
import de.codeshelf.consoleui.elements.validation.buildin.IPv4AddressInputValidator;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.PromtResultItemIF;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import jline.TerminalFactory;
import jline.console.completer.StringsCompleter;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.util.HashMap;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * User: Andreas Wegmann
 * Date: 29.11.15
 */
public class BasicInput {

  public static void main(String[] args) throws InterruptedException {
    AnsiConsole.systemInstall();
    System.out.println(ansi().eraseScreen().render("@|red,italic Hello|@ @|green World|@\n@|reset " +
            "This is a demonstration of ConsoleUI java library. It provides a simple console interface\n" +
            "for querying information from the user. ConsoleUI is inspired by Inquirer.js which is written\n" +
            "in JavaScript.|@"));


    try {
      ConsolePrompt prompt = new ConsolePrompt();
      PromptBuilder promptBuilder = prompt.getPromptBuilder();


      promptBuilder.createInputPrompt()
              .name("ip")
              .message("Please enter an IP address")
              //.defaultValue("John Doe")
              //.mask('*')
              .addCompleter(new StringsCompleter("127.0.0.1", "172.16.0.1", "192.168.0.100"))
              .addValidator(new IPv4AddressInputValidator())
              .addPrompt();

      promptBuilder.createInputPrompt()
              .name("email")
              .message("Please enter your e-mail address")
              //.defaultValue("John Doe")
              //.mask('*')
              .addValidator(new EMailAddressInputValidator())
              .addPrompt();

      HashMap<String, ? extends PromtResultItemIF> result = prompt.prompt(promptBuilder.build());
      System.out.println("result = " + result);

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        TerminalFactory.get().restore();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }}
