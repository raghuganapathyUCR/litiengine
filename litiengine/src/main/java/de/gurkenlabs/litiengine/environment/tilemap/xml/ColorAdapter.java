package de.gurkenlabs.litiengine.environment.tilemap.xml;

import java.awt.Color;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import de.gurkenlabs.litiengine.util.ColorHelper;
import javax.annotation.Nullable;

public class ColorAdapter extends XmlAdapter<String, Color> {

  @Nullable @Override
  public Color unmarshal(String v) {
    return ColorHelper.decode(v);
  }

  @Nullable @Override
  public String marshal(Color v) {
    return ColorHelper.encode(v);
  }
}
