package de.gurkenlabs.litiengine.environment.tilemap.xml;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import de.gurkenlabs.litiengine.Align;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.environment.tilemap.IMapObjectText;
import javax.annotation.Nullable;

public class Text implements IMapObjectText {
  @Nullable @XmlAttribute
  private String fontfamily;

  @Nullable @XmlAttribute
  private Integer pixelsize;

  @Nullable @XmlAttribute
  private Integer wrap;

  @Nullable @XmlAttribute
  @XmlJavaTypeAdapter(ColorAdapter.class)
  private Color color;

  @Nullable @XmlAttribute
  private Integer bold;

  @Nullable @XmlAttribute
  private Integer italic;

  @Nullable @XmlAttribute
  private Integer underline;

  @Nullable @XmlAttribute
  private Integer strikeout;

  @Nullable @XmlAttribute
  private Integer kerning;

  @Nullable @XmlAttribute
  private Align halign;

  @Nullable @XmlAttribute
  private Valign valign;

  @Nullable @XmlValue
  private String text;

  @Nullable @Override
  public String getText() {
    return this.text;
  }

  @Override
  public Font getFont() {
    Map<TextAttribute, Object> properties = new HashMap<>();
    properties.put(TextAttribute.FAMILY, this.getFontName());
    properties.put(TextAttribute.SIZE, this.getPixelSize() * 0.75f); // pixels to points
    properties.put(TextAttribute.WEIGHT, this.isBold() ? TextAttribute.WEIGHT_BOLD : TextAttribute.WEIGHT_REGULAR);
    properties.put(TextAttribute.POSTURE, this.isItalic() ? TextAttribute.POSTURE_OBLIQUE : TextAttribute.POSTURE_REGULAR);
    properties.put(TextAttribute.UNDERLINE, this.isUnderlined() ? TextAttribute.UNDERLINE_ON : -1);
    properties.put(TextAttribute.STRIKETHROUGH, this.isStrikeout());
    properties.put(TextAttribute.KERNING, this.useKerning() ? TextAttribute.KERNING_ON : 0);
    return new Font(properties);
  }

  public String getFontName() {
    return this.fontfamily != null ? this.fontfamily : Font.SANS_SERIF;
  }

  public int getPixelSize() {
    return this.pixelsize != null ? this.pixelsize : 16;
  }

  @Override
  public boolean wrap() {
    return this.wrap != null && this.wrap != 0;
  }

  @Override
  public Color getColor() {
    return this.color != null ? this.color : Color.BLACK;
  }

  @Override
  public boolean isBold() {
    return this.bold != null && this.bold != 0;
  }

  @Override
  public boolean isItalic() {
    return this.italic != null && this.italic != 0;
  }

  @Override
  public boolean isUnderlined() {
    return this.underline != null && this.underline != 0;
  }

  @Override
  public boolean isStrikeout() {
    return this.strikeout != null && this.strikeout != 0;
  }

  @Override
  public boolean useKerning() {
    return this.kerning == null || this.kerning != 0;
  }

  @Nullable @Override
  public Align getAlign() {
    return this.halign;
  }

  @Nullable @Override
  public Valign getValign() {
    return this.valign;
  }
}
