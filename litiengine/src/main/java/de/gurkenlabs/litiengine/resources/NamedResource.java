package de.gurkenlabs.litiengine.resources;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlTransient;
import javax.annotation.Nullable;

public abstract class NamedResource implements Resource {
  @Nullable @XmlAttribute
  private String name;

  @Nullable @XmlTransient
  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setName(@Nullable final String n) {
    this.name = n;
  }
}
