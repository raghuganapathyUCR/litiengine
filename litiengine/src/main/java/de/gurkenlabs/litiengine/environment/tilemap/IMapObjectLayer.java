package de.gurkenlabs.litiengine.environment.tilemap;

import java.awt.Color;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;

public interface IMapObjectLayer extends ILayer {

  /**
   * Gets the shapes.
   *
   * @return the shapes
   */
  List<IMapObject> getMapObjects();

  void addMapObject(IMapObject mapObject);

  void removeMapObject(IMapObject mapObject);

  @Nullable Color getColor();

  void setColor(Color color);

  Collection<IMapObject> getMapObjects(String... type);

  Collection<IMapObject> getMapObjects(int... mapIDs);
}
