package de.gurkenlabs.litiengine.environment.tilemap.xml;

import java.awt.Color;
import java.awt.Dimension;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.environment.tilemap.IMapObject;
import de.gurkenlabs.litiengine.environment.tilemap.IMapObjectLayer;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.annotation.Nullable;

public class MapObjectLayer extends Layer implements IMapObjectLayer {
  public static final String DEFAULT_MAPOBJECTLAYER_NAME = "default";

  @XmlElement(name = "object")
  private ArrayList<MapObject> objects = new ArrayList<>();

  @Nullable @XmlAttribute(name = "color")
  @XmlJavaTypeAdapter(ColorAdapter.class)
  private Color color;

  private transient final List<IMapObject> mapObjects = new CopyOnWriteArrayList<>();

  private transient boolean added;

  /**
   * Instantiates a new {@code MapObjectLayer} instance.
   */
  public MapObjectLayer() {
    super();
  }

  /**
   * Instantiates a new {@code MapObjectLayer} instance by copying from the specified original.
   *
   * @param original
   *          the layer we want to copy
   */
  public MapObjectLayer(MapObjectLayer original) {
    super(original);
    int mapId = Game.world().environment().getNextMapId();
    for (IMapObject obj : original.getMapObjects()) {
      this.addMapObject(new MapObject((MapObject) obj, mapId));
      mapId++;
    }
    if (original.getColor() != null) {
      this.setColor(original.getColor());
    }
  }

  private void loadMapObjects() {
    if (!this.added) {
      if (this.objects != null) {
        this.mapObjects.addAll(this.objects);
      }

      this.added = true;
    }
  }

  @Override
  public List<IMapObject> getMapObjects() {
    loadMapObjects();
    return this.mapObjects;
  }

  @Override
  public void removeMapObject(IMapObject mapObject) {
    this.mapObjects.remove(mapObject);
    this.objects.remove(mapObject);

    if (mapObject instanceof MapObject mo) {
      mo.setLayer(null);
    }
  }

  @Nullable @Override
  public String toString() {
    return this.getName();
  }

  @Override
  public void addMapObject(IMapObject mapObject) {
    loadMapObjects();
    this.mapObjects.add(mapObject);
    if (mapObject instanceof MapObject mo) {
      if (mo.getLayer() != null && !mo.getLayer().equals(this)) {
        mo.getLayer().removeMapObject(mo);
      }

      this.objects.add(mo);
      mo.setLayer(this);
    }
  }

  @Nullable @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public void setColor(Color color) {
    this.color = color;
  }

  @Override
  public Collection<IMapObject> getMapObjects(String... types) {
    List<IMapObject> objs = new ArrayList<>();
    for (IMapObject mapObject : this.getMapObjects()) {
      if (mapObject != null && Arrays.stream(types).anyMatch(type -> type.equals(mapObject.getType()))) {
        objs.add(mapObject);
      }
    }
    return objs;
  }

  @Override
  public Collection<IMapObject> getMapObjects(int... mapIDs) {
    List<IMapObject> objs = new ArrayList<>();
    for (IMapObject mapObject : this.getMapObjects()) {
      if (mapObject != null && Arrays.stream(mapIDs).anyMatch(id -> id == mapObject.getId())) {
        objs.add(mapObject);
      }
    }
    return objs;
  }

  @Override
  protected void afterUnmarshal(Unmarshaller u, Object parent) {
    if (this.objects == null) {
      this.objects = new ArrayList<>();
    }

    super.afterUnmarshal(u, parent);
  }

  @Override
  void finish(@Nullable URL location) throws TmxException {
    super.finish(location);
    for (MapObject object : this.objects) {
      object.finish(location);
    }
  }
}
