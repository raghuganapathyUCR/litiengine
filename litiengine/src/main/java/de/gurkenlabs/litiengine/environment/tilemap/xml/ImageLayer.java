package de.gurkenlabs.litiengine.environment.tilemap.xml;

import java.awt.Color;
import java.net.URL;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import de.gurkenlabs.litiengine.environment.tilemap.IImageLayer;
import de.gurkenlabs.litiengine.environment.tilemap.IMapImage;
import javax.annotation.Nullable;

public class ImageLayer extends Layer implements IImageLayer {

  @XmlAttribute
  private boolean repeatx;

  @XmlAttribute
  private boolean repeaty;

  @XmlElement
  private MapImage image;

  @Nullable @XmlAttribute
  @XmlJavaTypeAdapter(ColorAdapter.class)
  private Color trans;

  @Override
  public IMapImage getImage() {
    return this.image;
  }

  @Nullable @Override
  public Color getTransparentColor() {
    return this.trans;
  }

  @Override
  public boolean repeatHorizontally() {
    return this.repeatx;
  }

  @Override
  public boolean repeatVertically() {
    return this.repeaty;
  }

  @Override
  public double getOffsetX() {
    if (this.isInfiniteMap()) {
      TmxMap map = (TmxMap) this.getMap();
      return super.getOffsetX() - map.getChunkOffsetX() * map.getTileWidth();
    }

    return super.getOffsetX();
  }

  @Override
  public double getOffsetY() {
    if (this.isInfiniteMap()) {
      TmxMap map = (TmxMap) this.getMap();
      return super.getOffsetX() - map.getChunkOffsetY() * map.getTileHeight();
    }

    return super.getOffsetY();
  }

  private boolean isInfiniteMap() {
    return this.getMap() != null && this.getMap().isInfinite() && this.getMap() instanceof TmxMap;
  }

  @Override
  void finish(@Nullable URL location) throws TmxException {
    super.finish(location);
    this.image.finish(location);
  }
}
