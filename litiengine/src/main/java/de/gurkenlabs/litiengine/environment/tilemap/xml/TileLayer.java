package de.gurkenlabs.litiengine.environment.tilemap.xml;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import jakarta.xml.bind.annotation.XmlElement;

import de.gurkenlabs.litiengine.environment.tilemap.ITile;
import de.gurkenlabs.litiengine.environment.tilemap.ITileLayer;
import de.gurkenlabs.litiengine.environment.tilemap.ITilesetEntry;
import javax.annotation.Nullable;

public class TileLayer extends Layer implements ITileLayer {

  @Nullable @XmlElement
  private TileData data = null;

  @Nullable private transient List<ITile> tileList;

  @Nullable private transient Tile[][] tiles;

  /**
   * Instantiates a new {@code TileLayer} instance.
   */
  public TileLayer() {
    // keep for serialization
  }

  /**
   * Instantiates a new {@code TileLayer} instance with the specified data.
   *
   * @param data
   *          The tile data of this instance.
   */
  public TileLayer(TileData data) {
    this.data = data;
  }

  @Nullable @Override
  public ITile getTileByLocation(final Point2D location) {
    final Optional<ITile> tile = getTiles().stream().filter(x -> x.getTileCoordinate().equals(location)).findFirst();
    return tile.orElse(null);
  }

  @Nullable @Override
  public ITile getTile(int x, int y) {
    if (tiles == null || tiles.length == 0) {
      return null;
    }

    if (x < 0 || y < 0 || y >= tiles.length || x >= tiles[y].length) {
      return null;
    }

    return tiles[y][x];
  }

  @Override
  public void setTile(int x, int y, ITile tile) {
    this.setTile(x, y, tile.getGridId());
  }

  @Override
  public void setTile(int x, int y, int gid) {
    if (getRawTileData() == null) {
      return;
    }

    Tile tile = getRawTileData().getTiles().get(x + y * getWidth());
    if (tile == null) {
      return;
    }

    tile.setGridId(gid);

    if (getMap() != null) {
      ITilesetEntry entry = getMap().getTilesetEntry(gid);
      if (entry != null) {
        tile.setTilesetEntry(entry);
      }
    }
  }

  @Nullable @Override
  public List<ITile> getTiles() {
    return this.tileList;
  }

  @Override
  public int getWidth() {
    if (data != null && data.isInfinite()) {
      return data.getWidth();
    }

    return super.getWidth();
  }

  @Override
  public int getHeight() {
    if (data != null && data.isInfinite()) {
      return data.getHeight();
    }

    return super.getHeight();
  }

  protected List<Tile> getData() {
    return data.getTiles();
  }

  @Nullable protected TileData getRawTileData() {
    return data;
  }

  @Override
  void finish(@Nullable URL location) throws TmxException {
    super.finish(location);
    this.tileList = new CopyOnWriteArrayList<>();
    this.tiles = new Tile[getHeight()][getWidth()];
    for (int i = 0; i < getData().size(); i++) {
      final int x = i % getWidth();
      final int y = i / getWidth();
      final Tile tile = getData().get(i);
      tile.setTileCoordinate(new Point(x, y));
      this.tileList.add(tile);
      this.tiles[y][x] = tile;
      getData().get(i).setTilesetEntry(getMap().getTilesetEntry(tile.getGridId()));
    }
  }
}
