package de.gurkenlabs.litiengine.environment.tilemap.xml;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import javax.annotation.Nullable;

public class BooleanIntegerAdapter extends XmlAdapter<Integer, Boolean> {
  @Nullable @Override
  public Boolean unmarshal(Integer s) {
    return s == null ? null : s == 1;
  }

  @Nullable @Override
  public Integer marshal(Boolean c) {
    if (c == null) {
      return null;
    }

    return c ? 1 : 0;
  }
}
