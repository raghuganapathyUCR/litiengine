package de.gurkenlabs.litiengine.environment.tilemap;

import de.gurkenlabs.litiengine.util.AlphanumComparator;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.util.List;
import javax.annotation.Nullable;

public interface IMap extends ILayerList, Comparable<IMap> {

  /**
   * Gets the tilesets.
   *
   * @return the tilesets
   */
  List<ITileset> getTilesets();

  @Nullable ITilesetEntry getTilesetEntry(int gid);

  /**
   * Gets the orientation.
   *
   * @return the orientation
   */
  IMapOrientation getOrientation();

  @Nullable URL getPath();

  /**
   * Gets the renderorder.
   *
   * @return the renderorder
   */
  RenderOrder getRenderOrder();

  /**
   * Gets the size in pixels.
   *
   * @return the size in pixels
   */
  Dimension getSizeInPixels();

  /**
   * Gets the map width in tiles.
   *
   * @return the width in tiles
   */
  int getWidth();

  /**
   * Gets the map height in tiles.
   *
   * @return the height in tiles
   */
  int getHeight();

  /**
   * Gets the sizein tiles.
   *
   * @return the sizein tiles
   */
  Dimension getSizeInTiles();

  Rectangle2D getBounds();

  /**
   * Gets the tile size.
   *
   * @return the tile size
   */
  Dimension getTileSize();

  /**
   * Gets the coordinates of the parallax origin in pixels.
   *
   * @return The parallax origin.
   */
  Point2D getParallaxOrigin();

  /**
   * Gets the horizontal tile size.
   *
   * @return the horizontal tile size
   */
  int getTileWidth();

  /**
   * Gets the vertical tile size.
   *
   * @return the vertical tile size
   */
  int getTileHeight();

  /**
   * Gets the straight edges' length for hexagonal maps.
   *
   * @return the hex side length
   */
  int getHexSideLength();

  /**
   * Gets the staggering axis
   *
   * @return the tile size
   */
  @Nullable StaggerAxis getStaggerAxis();

  /**
   * Gets the tile size.
   *
   * @return the tile size
   */
  @Nullable StaggerIndex getStaggerIndex();

  /**
   * Gets the version.
   *
   * @return the version
   */
  double getVersion();

  @Nullable String getTiledVersion();

  @Nullable String getName();

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  void setName(String name);

  int getNextObjectId();

  int getNextLayerId();

  @Nullable Color getBackgroundColor();

  boolean isInfinite();

  @Override
  default int compareTo(IMap map) {
    return AlphanumComparator.compareTo(this.getName(), map.getName());
  }
}
