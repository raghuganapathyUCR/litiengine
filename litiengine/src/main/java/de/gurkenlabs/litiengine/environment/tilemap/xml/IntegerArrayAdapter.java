package de.gurkenlabs.litiengine.environment.tilemap.xml;

import de.gurkenlabs.litiengine.util.ArrayUtilities;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import javax.annotation.Nullable;


public class IntegerArrayAdapter extends XmlAdapter<String, int[]> {
  @Override
  public int[] unmarshal(String v) throws Exception {
    return ArrayUtilities.splitInt(v);
  }

  @Nullable @Override
  public String marshal(int[] v) throws Exception {
    return ArrayUtilities.join(v);
  }
}
