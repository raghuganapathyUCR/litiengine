package de.gurkenlabs.litiengine.gui;

import de.gurkenlabs.litiengine.Align;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.input.Input;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

public class TextFieldComponent extends ImageComponent {

  public static final String DOUBLE_FORMAT = "[-+]?[0-9]*\\.?[0-9]*([eE][-+]?[0-9]*)?";
  public static final String INTEGER_FORMAT = "[0-9]{1,10}";
  private static final Logger log = Logger.getLogger(TextFieldComponent.class.getName());
  private final List<Consumer<String>> changeConfirmedConsumers;
  private boolean cursorVisible;
  private int flickerDelay = 300;
  @Nullable private String format;
  private String cursor = "_";
  @Nullable private String fullText;
  private long lastToggled;
  private int maxLength = 0;

  public TextFieldComponent(
      final double x, final double y, final double width, final double height, final String text) {
    super(x, y, width, height, text);
    this.changeConfirmedConsumers = new CopyOnWriteArrayList<>();
    setText(text);
    Input.keyboard().onKeyTyped(this::handleTypedKey);
    onClicked(
        e -> {
          if (!isSelected()) {
            setSelected(true);
          }
        });

    Input.mouse()
        .onClicked(
            e -> {
              if (isSelected() && !getBoundingBox().contains(Input.mouse().getLocation())) {
                acceptInput();
              }
            });

    setTextAlign(Align.LEFT);
    setAutomaticLineBreaks(true);
  }

  @Nullable public String getFormat() {
    return format;
  }

  public void setFormat(final String format) {
    this.format = format;
  }

  public String getCursor() {
    return cursor;
  }

  public void setCursor(String newCursor) {
    this.cursor = newCursor;
  }

  public int getMaxLength() {
    return maxLength;
  }

  public void setMaxLength(final int maxLength) {
    this.maxLength = maxLength;
  }

  @Override
  public String getText() {
    return fullText;
  }

  @Override
  public void setText(@Nullable final String text) {
    this.fullText = text;
  }

  @Override
  public String getTextToRender(Graphics2D g) {
    return isSelected() && cursorVisible ? super.getTextToRender(g) + getCursor()
        : super.getTextToRender(g) + "  ";
  }

  public void handleTypedKey(final KeyEvent event) {
    if (!canHandleInput()) {
      return;
    }
    switch (event.getKeyCode()) {
      case KeyEvent.VK_BACK_SPACE -> handleBackSpace();
      case KeyEvent.VK_ESCAPE, KeyEvent.VK_ENTER -> acceptInput();
      default -> handleNormalTyping(event);
    }
  }


  public boolean canHandleInput() {
    return !isSuspended() && isSelected() && isVisible() && isEnabled();
  }

  public void onChangeConfirmed(final Consumer<String> cons) {
    changeConfirmedConsumers.add(cons);
  }

  @Override
  public void render(final Graphics2D g) {
    super.render(g);
    if (isSelected() && Game.time().since(lastToggled) >= flickerDelay) {
      this.cursorVisible = !this.cursorVisible;
      this.lastToggled = Game.time().now();
    }
  }

  public void setFlickerDelay(int flickerDelayMillis) {
    this.flickerDelay = flickerDelayMillis;
  }

  private void acceptInput() {
    setSelected(false);
    changeConfirmedConsumers.forEach(c -> c.accept(getText()));
    log.log(
        Level.INFO,
        "{0} typed into TextField with ComponentID {1}",
        new Object[] {getText(), getComponentId()});
  }

  private void handleBackSpace() {
    if (Input.keyboard().isPressed(KeyEvent.VK_SHIFT)) {
      while (getText().length() >= 1
          && getText().charAt(getText().length() - 1) == ' ') {
        setText(getText().substring(0, getText().length() - 1));
      }

      while (getText().length() >= 1
          && getText().charAt(getText().length() - 1) != ' ') {
        setText(getText().substring(0, getText().length() - 1));
      }
    } else if (getText().length() >= 1) {
      setText(getText().substring(0, getText().length() - 1));
    }

    if (isKnownNumericFormat() && (getText() == null || getText().isEmpty())) {
      setText("0");
    }
  }

  private void handleNormalTyping(KeyEvent event) {
    if (getMaxLength() > 0 && getText().length() >= getMaxLength()) {
      return;
    }

    final char text = event.getKeyChar();
    if (text == KeyEvent.CHAR_UNDEFINED) {
      return;
    }

    // regex check to ensure certain formats
    if (getFormat() != null && !getFormat().isEmpty()) {
      final Pattern pat = Pattern.compile(getFormat());
      final Matcher mat = pat.matcher(getText() + text);
      if (!mat.matches()) {
        return;
      }
    }

    if (isKnownNumericFormat() && getText().equals("0")) {
      setText("");
    }

    setText(getText() + text);
  }

  private boolean isKnownNumericFormat() {
    return getFormat() != null
        && (getFormat().equals(INTEGER_FORMAT) || getFormat().equals(DOUBLE_FORMAT));
  }
}
