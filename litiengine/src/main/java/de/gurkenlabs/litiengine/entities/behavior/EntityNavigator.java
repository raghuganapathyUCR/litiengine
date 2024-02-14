package de.gurkenlabs.litiengine.entities.behavior;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.IMobileEntity;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.util.geom.GeometricUtilities;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class EntityNavigator implements IUpdateable, IRenderable {

  private static final float DEFAULT_ACCEPTABLE_ERROR = 0.3f;

  private final List<Predicate<IMobileEntity>> cancelNavigationConditions;
  private final List<NavigationListener> listeners;

  private final IMobileEntity entity;
  @Nullable private final PathFinder pathFinder;

  private int currentSegment;
  @Nullable private Path path;
  private float acceptableError;

  /**
   * Instantiates a new entity navigator.
   *
   * @param entity
   *          The entity that will be navigated by this instance
   * @param pathFinder
   *          The pathfinder that is used to navigate the entity
   */
  public EntityNavigator(final IMobileEntity entity, @Nullable final PathFinder pathFinder) {
    this.cancelNavigationConditions = new CopyOnWriteArrayList<>();
    this.listeners = new CopyOnWriteArrayList<>();
    this.entity = entity;
    this.pathFinder = pathFinder;
    this.setAcceptableError(DEFAULT_ACCEPTABLE_ERROR);
    Game.loop().attach(this);
  }

  /**
   * Instantiates a new entity navigator without a pre-initialized PathFinder.
   *
   * @param entity
   *          The entity that will be navigated by this instance
   */
  public EntityNavigator(final IMobileEntity entity) {
    this(entity, null);
  }

  public void addNavigationListener(NavigationListener listener) {
    this.listeners.add(listener);
  }

  public void removeNavigationListener(NavigationListener listener) {
    this.listeners.remove(listener);
  }

  public void cancelNavigation(final Predicate<IMobileEntity> predicate) {
    if (!this.cancelNavigationConditions.contains(predicate)) {
      this.cancelNavigationConditions.add(predicate);
    }
  }

  public IMobileEntity getEntity() {
    return entity;
  }

  @Nullable public Path getPath() {
    return this.path;
  }

  @Nullable public PathFinder getPathFinder() {
    return this.pathFinder;
  }

  public float getAcceptableError() {
    return this.acceptableError;
  }

  public boolean isNavigating() {
    return getPath() != null;
  }

  public boolean navigate(final Path2D path) {
    this.path = new Path(path);
    return getPath() != null;
  }

  public boolean navigate(final Point2D target) {
    if (this.getPathFinder() != null) {
      this.path = getPathFinder().findPath(getEntity(), target);
    }

    return getPath() != null;
  }

  @Override
  public void render(Graphics2D g) {
    if (this.getPath() == null) {
      return;
    }

    g.setColor(Color.MAGENTA);
    Game.graphics().renderOutline(g, getPath().getPath());
  }

  public void rotateTowards(final Point2D target) {
    final double angle =
        GeometricUtilities.calcRotationAngleInDegrees(
            getEntity().getCollisionBox().getCenterX(),
            getEntity().getCollisionBox().getCenterY(),
            target.getX(),
            target.getY());
    getEntity().setAngle((float) angle);
  }

  public void setAcceptableError(float acceptableError) {
    this.acceptableError = acceptableError;
  }

  public void stop() {
    this.currentSegment = 0;
    this.path = null;

    for (NavigationListener listener : this.listeners) {
      listener.stopped();
    }
  }

  @Override
  public void update() {
    if (!this.isNavigating()) {
      return;
    }

    if (getPath() == null) {
      return;
    }

    for (final Predicate<IMobileEntity> pred : this.cancelNavigationConditions) {
      if (pred.test(getEntity())) {
        stop();
        return;
      }
    }

    final PathIterator pi = getPath().getPath().getPathIterator(null);
    if (pi.isDone()) {
      stop();
      return;
    }

    // although at max 6 elements are returned, sometimes the path
    // implementation tries to access index 20 ... don't know why, but this
    // prevents it
    final double[] startCoordinates = new double[22];
    final double[] coordinates = new double[22];
    for (int i = 0; i <= this.currentSegment; i++) {
      if (pi.isDone()) {
        stop();
        return;
      }

      pi.currentSegment(startCoordinates);
      pi.next();
    }

    if (pi.isDone()) {
      stop();
      return;
    }

    pi.currentSegment(coordinates);

    final double distance =
        GeometricUtilities.distance(
            getEntity().getCollisionBox().getCenterX(),
            getEntity().getCollisionBox().getCenterY(),
            coordinates[0],
            coordinates[1]);
    if (distance < getAcceptableError()) {
      ++this.currentSegment;
      return;
    }

    final double angle =
        GeometricUtilities.calcRotationAngleInDegrees(
            getEntity().getCollisionBox().getCenterX(),
            getEntity().getCollisionBox().getCenterY(),
            coordinates[0],
            coordinates[1]);
    final float pixelsPerTick = getEntity().getTickVelocity();
    Game.physics()
        .move(
            getEntity(),
            (float) angle,
            (float) (distance < pixelsPerTick ? distance : pixelsPerTick));
  }
}
