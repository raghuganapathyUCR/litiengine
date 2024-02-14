package de.gurkenlabs.litiengine.resources;

import de.gurkenlabs.litiengine.util.AlphanumComparator;
import javax.annotation.Nullable;

public interface Resource extends Comparable<Resource> {

  /**
   * Gets the name.
   *
   * @return the name
   */
  @Nullable String getName();

  void setName(@Nullable String name);

  @Override
  default int compareTo(Resource obj) {
    return AlphanumComparator.compareTo(this.getName(), obj.getName());
  }
}
