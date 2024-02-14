package de.gurkenlabs.litiengine.environment.tilemap.xml;

import de.gurkenlabs.litiengine.environment.tilemap.ITerrain;
import de.gurkenlabs.litiengine.environment.tilemap.ITerrainSet;
import de.gurkenlabs.litiengine.environment.tilemap.TerrainType;
import jakarta.xml.bind.annotation.*;

import java.util.List;
import javax.annotation.Nullable;

@XmlAccessorType(XmlAccessType.FIELD)
public class WangSet extends CustomPropertyProvider implements ITerrainSet {

  @Nullable @XmlAttribute
  private String name;

  @Nullable @XmlAttribute(name = "class")
  private String wangSetClass;

  @Nullable @XmlAttribute
  private TerrainType type;

  @XmlAttribute
  private int tile; // think this is unused in Tiled but still serialized

  @Nullable @XmlElement(type = WangColor.class)
  private List<ITerrain> wangcolor;

  @Nullable @XmlElement(name = "wangtile")
  private List<WangTile> wangtiles;

  @Nullable @Override
  public String getName() {
    return this.name;
  }

  @Nullable @Override
  public TerrainType getType() {
    return this.type;
  }

  @Nullable @Override
  public List<ITerrain> getTerrains() {
    return this.wangcolor;
  }

  @Override
  public ITerrain[] getTerrains(int tileId) {
    final int TERRAIN_COUNT = 8;
    var terrains = new ITerrain[TERRAIN_COUNT];

    for (var wangtile : this.wangtiles) {
      if (wangtile.getTileId() == tileId) {
        for(int i = 0; i < TERRAIN_COUNT; i++){
          var terrain = wangtile.getWangId()[i];
          terrains[i] = terrain == 0 ? null : this.getTerrains().get(wangtile.getWangId()[i] - 1);
        }

        break;
      }
    }

    return terrains;
  }
}
