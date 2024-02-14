package de.gurkenlabs.litiengine.environment;

import java.util.ArrayList;
import java.util.Collection;

import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.environment.tilemap.IMapObject;
import de.gurkenlabs.litiengine.environment.tilemap.MapObjectProperty;
import de.gurkenlabs.litiengine.environment.tilemap.MapObjectType;
import javax.annotation.Nullable;

public class SpawnpointMapObjectLoader extends MapObjectLoader {

  protected SpawnpointMapObjectLoader() {
    super(MapObjectType.SPAWNPOINT);
  }

  @Override
  public Collection<IEntity> load(Environment environment, IMapObject mapObject) {
    Collection<IEntity> entities = new ArrayList<>();
    if (!this.isMatchingType(mapObject)) {
      return entities;
    }

    final Direction direction = mapObject.getEnumValue(MapObjectProperty.SPAWN_DIRECTION, Direction.class, Direction.DOWN);
    final String spawnType = mapObject.getStringValue(MapObjectProperty.SPAWN_INFO, null);

    final Spawnpoint spawn = this.createSpawnpoint(mapObject, direction, spawnType);
    loadDefaultProperties(spawn, mapObject);

    entities.add(spawn);
    return entities;
  }

  protected Spawnpoint createSpawnpoint(IMapObject mapObject, Direction direction, String spawnType) {
    return new Spawnpoint(direction, spawnType);
  }
}
