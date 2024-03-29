package de.gurkenlabs.utiliti.components;

import de.gurkenlabs.litiengine.environment.tilemap.IMapObject;

public interface EntityController extends Controller {
  void select(IMapObject mapObject);

  void refresh(int mapId);

  void remove(IMapObject mapObject);
}
