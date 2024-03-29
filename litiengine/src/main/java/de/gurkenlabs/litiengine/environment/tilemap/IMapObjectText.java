package de.gurkenlabs.litiengine.environment.tilemap;

import java.awt.Color;
import java.awt.Font;

import de.gurkenlabs.litiengine.Align;
import de.gurkenlabs.litiengine.Valign;
import javax.annotation.Nullable;

public interface IMapObjectText {
  @Nullable public String getText();

  public Font getFont();

  public boolean wrap();

  public Color getColor();

  @Nullable public Align getAlign();

  @Nullable public Valign getValign();

  public boolean isBold();

  public boolean isItalic();

  public boolean isUnderlined();

  public boolean isStrikeout();

  public boolean useKerning();
}
