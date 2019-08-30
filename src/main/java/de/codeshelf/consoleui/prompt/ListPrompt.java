package de.codeshelf.consoleui.prompt;

import de.codeshelf.consoleui.elements.ListChoice;
import de.codeshelf.consoleui.elements.items.ConsoleUIItemIF;
import de.codeshelf.consoleui.elements.items.impl.ListItem;
import de.codeshelf.consoleui.prompt.reader.ConsoleReaderImpl;
import de.codeshelf.consoleui.prompt.reader.Reader;
import de.codeshelf.consoleui.prompt.renderer.CUIRenderer;

import java.io.IOException;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * ListPrompt implements the list choice handling.
 *
 * User: Andreas Wegmann
 * Date: 01.01.16
 */
public class ListPrompt extends AbstractListablePrompt implements PromptIF<ListChoice,ListResult> {
  // the list to let the user choose from
  private ListChoice listChoice;

  CUIRenderer itemRenderer = CUIRenderer.getRenderer();

  public ListPrompt() throws IOException {
    super();
  }

  private void render() {
    int itemNumber = 0;

    if (renderHeight == 0) {
      renderHeight = 2 + itemList.size();
    } else {
      System.out.println(ansi().cursorUp(renderHeight));
    }

    System.out.println(renderMessagePrompt(listChoice.getMessage()));
    for (ConsoleUIItemIF listItem : itemList) {
      String renderedItem = itemRenderer.render(listItem,(selectedItemIndex == itemNumber));
      System.out.println(renderedItem);
      itemNumber++;
    }
  }

  public ListResult prompt(ListChoice listChoice) throws IOException {
    this.listChoice = listChoice;
    itemList = listChoice.getListItemList();
    if (reader == null) {
      reader = new ConsoleReaderImpl();
    }
    reader.addAllowedPrintableKey('j');
    reader.addAllowedPrintableKey('k');
    reader.addAllowedPrintableKey(' ');
    reader.addAllowedSpecialKey(Reader.SpecialKey.DOWN);
    reader.addAllowedSpecialKey(Reader.SpecialKey.UP);
    reader.addAllowedSpecialKey(Reader.SpecialKey.ENTER);

    selectedItemIndex = getFirstSelectableItemIndex();

    render();
    Reader.ReaderInput readerInput = reader.read();
    while (readerInput.getSpecialKey() != Reader.SpecialKey.ENTER) {
      if (readerInput.getSpecialKey() == Reader.SpecialKey.PRINTABLE_KEY) {
        if (readerInput.getPrintableKey().equals('j')) {
          selectedItemIndex = getNextSelectableItemIndex();
        } else if (readerInput.getPrintableKey().equals('k')) {
          selectedItemIndex = getPreviousSelectableItemIndex();
        }
      } else if (readerInput.getSpecialKey() == Reader.SpecialKey.DOWN) {
        selectedItemIndex = getNextSelectableItemIndex();
      } else if (readerInput.getSpecialKey() == Reader.SpecialKey.UP) {
        selectedItemIndex = getPreviousSelectableItemIndex();
      }

      render();
      readerInput = reader.read();
    }

    ListItem listItem = (ListItem) itemList.get(selectedItemIndex);
    ListResult selection=new ListResult(listItem.getName());
    renderMessagePromptAndResult(listChoice.getMessage(),((ListItem) itemList.get(selectedItemIndex)).getText());
    return selection ;
  }
}
