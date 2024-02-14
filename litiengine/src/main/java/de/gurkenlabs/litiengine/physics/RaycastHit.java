package de.gurkenlabs.litiengine.physics;

import de.gurkenlabs.litiengine.entities.ICollisionEntity;
import java.awt.geom.Point2D;
import javax.annotation.Nullable;

public class RaycastHit {
  @Nullable private final Point2D point;
  private final ICollisionEntity entity;
  private final double distance;

  public RaycastHit(@Nullable Point2D point, ICollisionEntity entity, double distance) {
    this.point = point;
    this.entity = entity;
    this.distance = distance;
  }

  @Nullable public Point2D getPoint() {
    return this.point;
  }

  public ICollisionEntity getEntity() {
    return this.entity;
  }

  public double getDistance() {
    return this.distance;
  }
}
