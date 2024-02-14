package de.gurkenlabs.litiengine.environment.tilemap.xml;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.annotation.Nullable;

public class WangTile {

  @XmlAttribute
  private int tileid;

  @Nullable @XmlAttribute
  @XmlJavaTypeAdapter(IntegerArrayAdapter.class)
  private int[] wangid;

  public int getTileId() {
    return tileid;
  }

  @Nullable public int[] getWangId() {
    return wangid;
  }
}
