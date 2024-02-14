package de.gurkenlabs.litiengine.environment.tilemap;

import java.awt.Dimension;
import java.util.List;

import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.resources.Resource;
import javax.annotation.Nullable;

public interface ITileset extends ICustomPropertyProvider, Resource {

  /**
   * Gets the first grid id.
   *
   * @return the first grid id
   */
  int getFirstGridId();

  /**
   * Gets the image.
   *
   * @return the image
   */
  @Nullable IMapImage getImage();

  @Nullable Spritesheet getSpritesheet();

  int getMargin();

  int getSpacing();

  /**
   * Gets the tile dimension.
   *
   * @return the tile dimension
   */
  Dimension getTileDimension();

  int getColumns();

  @Nullable ITileOffset getTileOffset();

  int getTileHeight();

  /**
   * Gets the tile width.
   *
   * @return the tile width
   */
  int getTileWidth();

  int getTileCount();

  @Nullable ITilesetEntry getTile(int id);

  boolean containsTile(ITile tile);

  boolean containsTile(ITilesetEntry entry);

  boolean containsTile(int tileId);

  /**
   * Gets the terrain sets defined by this tile set.
   *
   * @return The terrain sets of this instance.
   */
  @Nullable List<ITerrainSet> getTerrainSets();
}
