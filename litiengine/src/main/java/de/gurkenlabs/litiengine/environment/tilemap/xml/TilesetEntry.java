package de.gurkenlabs.litiengine.environment.tilemap.xml;

import java.awt.image.BufferedImage;
import java.net.URL;

import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;

import de.gurkenlabs.litiengine.environment.tilemap.IMapObjectLayer;
import de.gurkenlabs.litiengine.environment.tilemap.ITileAnimation;
import de.gurkenlabs.litiengine.environment.tilemap.ITileset;
import de.gurkenlabs.litiengine.environment.tilemap.ITilesetEntry;
import de.gurkenlabs.litiengine.resources.Resources;
import javax.annotation.Nullable;

@XmlAccessorType(XmlAccessType.FIELD)
public class TilesetEntry extends CustomPropertyProvider implements ITilesetEntry {
  @XmlTransient
  private Tileset tileset;

  @Nullable @XmlAttribute
  private Integer id;

  @Nullable @XmlAttribute
  private String terrain;

  @Nullable @XmlElement
  private TileAnimation animation;

  @Nullable @XmlElement
  private MapImage image;

  @Nullable @XmlAttribute
  private String type;

  @Nullable @XmlElement(name = "objectgroup")
  private MapObjectLayer collisionData;

  /**
   * Instantiates a new {@code TilesetEntry}.
   */
  public TilesetEntry() {
    // keep for serialization
  }

  /**
   * Instantiates a new {@code TilesetEntry} from the specified tileset.
   *
   * @param tileset
   *          The tileset that contains this entry.
   * @param id
   *          The identifier of this instance.
   */
  public TilesetEntry(Tileset tileset, int id) {
    this.tileset = tileset;
    this.id = id;
  }

  @Override
  public int getId() {
    if (this.id == null) {
      return 0;
    }

    return this.id;
  }

  @Nullable @Override
  public ITileAnimation getAnimation() {
    return this.animation;
  }

  @Nullable @Override
  public BufferedImage getImage() {
    if (this.animation == null) {
      return this.getBasicImage();
    }
    return this.tileset.getTile(this.animation.getCurrentFrame().getTileId()).getBasicImage();
  }

  @Nullable @Override
  public BufferedImage getBasicImage() {
    if (this.image != null) {
      return Resources.images().get(this.image.getAbsoluteSourcePath());
    }
    return this.tileset.getSpritesheet().getSprite(this.getId(), this.tileset.getMargin(), this.tileset.getSpacing());
  }

  @Override
  public ITileset getTileset() {
    return this.tileset;
  }

  @Nullable @Override
  public String getType() {
    return this.type;
  }

  @Nullable @Override
  public IMapObjectLayer getCollisionInfo() {
    return this.collisionData;
  }

  @Override
  void finish(@Nullable URL location) throws TmxException {
    super.finish(location);
    if (this.image != null) {
      this.image.finish(location);
    }
  }

  boolean shouldBeSaved() {
    return this.terrain != null || this.image != null || this.animation != null || this.type != null;
  }

  @SuppressWarnings("unused")
  private void afterUnmarshal(Unmarshaller u, Object parent) {
    this.tileset = (Tileset) parent;
  }
}
