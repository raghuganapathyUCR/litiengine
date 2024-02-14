package de.gurkenlabs.litiengine.environment.tilemap;

import java.awt.image.BufferedImage;
import javax.annotation.Nullable;

public interface ITilesetEntry extends ICustomPropertyProvider {

  int getId();

  @Nullable ITileAnimation getAnimation();

  /**
   * Gets the current image for this tileset entry.
   *
   * @return The current image for this tileset entry, accounting for animation.
   */
  BufferedImage getImage();

  /**
   * Gets the "standard" image for this tileset entry, without applying any animations.
   *
   * @return The standard image for this tileset entry
   */
  @Nullable BufferedImage getBasicImage();

  /**
   * Gets the tileset that this entry belongs to.
   *
   * @return The tileset for this entry
   */
  ITileset getTileset();

  @Nullable String getType();

  @Nullable IMapObjectLayer getCollisionInfo();
}
