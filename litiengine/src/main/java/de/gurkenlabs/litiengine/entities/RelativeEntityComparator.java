package de.gurkenlabs.litiengine.entities;

import java.util.Comparator;
import javax.annotation.Nullable;

public abstract class RelativeEntityComparator implements Comparator<IEntity> {
  @Nullable private IEntity relativeEntity;

  protected RelativeEntityComparator() {}

  protected RelativeEntityComparator(final IEntity relativeEntity) {
    this.relativeEntity = relativeEntity;
  }

  @Nullable public IEntity getRelativeEntity() {
    return this.relativeEntity;
  }

  public void setRelativeEntity(final IEntity relativeEntity) {
    this.relativeEntity = relativeEntity;
  }
}
