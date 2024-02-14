package de.gurkenlabs.litiengine.environment.tilemap.xml;

import de.gurkenlabs.litiengine.environment.tilemap.ITerrain;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.awt.*;
import javax.annotation.Nullable;

@XmlRootElement(name = "wangcolor")
@XmlAccessorType(XmlAccessType.FIELD)
public class WangColor implements ITerrain {

  @Nullable @XmlAttribute
  private String name;

  @Nullable @XmlAttribute(name = "class")
  private String wangColorClass;

  @Nullable @XmlJavaTypeAdapter(ColorAdapter.class)
  @XmlAttribute
  private Color color;

  @XmlAttribute
  private int tile;

  @XmlAttribute
  private double probability;

  @Nullable @Override
  public String getName() {
    return this.name;
  }

  @Nullable @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public double getProbability() {
    return this.probability;
  }
}
