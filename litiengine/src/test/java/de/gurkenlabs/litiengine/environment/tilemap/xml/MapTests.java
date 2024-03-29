package de.gurkenlabs.litiengine.environment.tilemap.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

import de.gurkenlabs.litiengine.environment.tilemap.*;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.io.URLAdapter;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class MapTests {
  @BeforeEach
  public void clearResources() {
    Resources.maps().clear();
  }

  @Test
  void testBasicProperties() {
    IMap map =
        Resources.maps().get("de/gurkenlabs/litiengine/environment/tilemap/xml/test-map.tmx");

    assertEquals(1.1, map.getVersion());
    assertEquals("1.10.2", map.getTiledVersion());
    assertEquals(MapOrientations.ORTHOGONAL, map.getOrientation());
    assertEquals(RenderOrder.RIGHT_DOWN, map.getRenderOrder());
    assertEquals(256, map.getSizeInPixels().width);
    assertEquals(256, map.getSizeInPixels().height);
    assertEquals(16, map.getTileSize().width);
    assertEquals(16, map.getTileSize().height);
    assertEquals(16, map.getSizeInTiles().width);
    assertEquals(16, map.getSizeInTiles().height);
    assertEquals(1.11, map.getParallaxOrigin().getX());
    assertEquals(2, map.getParallaxOrigin().getY());

    assertEquals(10, map.getNextLayerId());
    assertEquals(1, map.getNextObjectId());

    assertEquals("test-map", map.getName());
    assertEquals(MapTests.class.getResource("test-map.tmx"), map.getPath());
    assertEquals(new Color(0xaa3df675, true), map.getBackgroundColor());
    assertEquals(new Rectangle2D.Double(0, 0, 256, 256), map.getBounds());
    assertEquals(2, map.getTilesets().size());
    assertEquals(1, ((TmxMap) map).getExternalTilesets().size());
    assertEquals("external-tileset", map.getTilesets().get(1).getName());
    assertEquals(1, map.getTileLayers().size());
    assertEquals(16, map.getTileLayers().getFirst().getSizeInTiles().width);
    assertEquals(16, map.getTileLayers().getFirst().getSizeInTiles().height);
    assertEquals(0, map.getImageLayers().size());
    assertEquals(1, map.getRenderLayers().size());
    assertEquals(0, map.getMapObjectLayers().size());
    assertEquals(0, map.getProperties().size());
    assertEquals(0, map.getMapObjects().size());
  }

  @ParameterizedTest
  @MethodSource("getTileCustomProperties")
  void testTileCustomProperties(
      int tileLayers, int x, int y, String propertyName, String expectedValue) {
    IMap map =
        Resources.maps().get("de/gurkenlabs/litiengine/environment/tilemap/xml/test-map.tmx");
    if (!Resources.maps().contains(map)) {
      fail();
    }
    assertEquals(
        expectedValue,
        map.getTileLayers().get(tileLayers).getTile(x, y).getStringValue(propertyName));
  }

  @Test
  void testSettingProperties() {
    TmxMap map =
        (TmxMap) Resources.maps()
            .get("de/gurkenlabs/litiengine/environment/tilemap/xml/test-map.tmx");
    map.setOrientation(MapOrientations.ISOMETRIC_STAGGERED);
    map.setTiledVersion("0.0.0");
    map.setVersion(2.0);
    map.setWidth(64);
    map.setHeight(64);

    map.setTileHeight(32);
    map.setTileWidth(32);
    map.setName("test");
    map.setRenderOrder(RenderOrder.RIGHT_UP);

    assertEquals(64, map.getSizeInTiles().width);
    assertEquals(64, map.getSizeInTiles().height);
    assertEquals(MapOrientations.ISOMETRIC_STAGGERED, map.getOrientation());
    assertEquals("0.0.0", map.getTiledVersion());
    assertEquals(2.0, map.getVersion());
    assertEquals(32, map.getTileSize().width);
    assertEquals(32, map.getTileSize().height);
    assertEquals("test", map.getName());
    assertEquals(RenderOrder.RIGHT_UP, map.getRenderOrder());
  }

  @Test
  void testImageLayers(){
    IMap map =
      Resources.maps()
        .get("de/gurkenlabs/litiengine/environment/tilemap/xml/test-imagelayer.tmx");
    assertEquals(1, map.getImageLayers().size());

    IImageLayer layer = map.getImageLayers().getFirst();
    assertEquals("blue_galaxy", layer.getName());
    assertEquals(-32, layer.getOffsetX());
    assertEquals(-32, layer.getOffset().getX());
    assertEquals(-12000, layer.getOffsetY());
    assertEquals(-12000, layer.getOffset().getY());
    assertEquals(1.3, layer.getHorizontalParallaxFactor());
    assertEquals(1.2, layer.getVerticalParallaxFactor());
    assertTrue(layer.repeatHorizontally());
    assertTrue(layer.repeatVertically());

    assertEquals("blue_pixeled.png", layer.getImage().getSource());
    assertEquals(672, layer.getImage().getWidth());
    assertEquals(24000, layer.getImage().getHeight());
  }

  @Test
  void testMapObjectLayers() {
    IMap map =
        Resources.maps()
            .get("de/gurkenlabs/litiengine/environment/tilemap/xml/test-mapobject.tmx");
    assertEquals(1, map.getMapObjectLayers().size());

    IMapObjectLayer layer = map.getMapObjectLayers().getFirst();
    assertEquals("test", layer.getName());
    assertEquals(4, layer.getMapObjects().size());
    assertEquals(16, layer.getSizeInTiles().width);
    assertEquals(new Color(195, 65, 0, 200), layer.getTintColor());

    IMapObject object = map.getMapObject(1);

    assertEquals(layer, map.getMapObjectLayer(object));

    assertEquals(1, map.getMapObjects("TEST_TYPE").size());
    assertEquals(4, map.getMapObjects().size());
    assertEquals("TEST_TYPE", object.getType());
    assertEquals("bar", object.getStringValue("foo"));
    assertEquals(0.1f, object.getX());
    assertEquals(0.1f, object.getY());
    assertEquals(10.1f, object.getWidth());
    assertEquals(10.1f, object.getHeight());
    assertEquals("bar", object.getStringValue("foo"));

    map.addLayer(mock(MapObjectLayer.class));
    assertEquals(2, map.getMapObjectLayers().size());

    map.removeMapObject(1);

    assertNull(map.getMapObject(1));

    map.removeLayer(layer);

    assertEquals(1, map.getMapObjectLayers().size());

    map.removeLayer(1);

    assertEquals(0, map.getMapObjectLayers().size());
  }

  @ParameterizedTest(name = "testDecimalFloatAdapter value={0}, expected={1}")
  @CsvSource({"1f, '1'", "1.0f, '1'", "1.00f, '1'", "1.1f, '1.1'", "1.00003f, '1.00003'"})
  void testDecimalFloatAdapter(float value, String expected) throws Exception {
    // arrange
    DecimalFloatAdapter adapter = new DecimalFloatAdapter();

    // act
    String actual = adapter.marshal(value);

    // assert
    assertEquals(expected, actual);
  }

  @Test
  void testInfiniteMap() {
    TmxMap map =
        (TmxMap) Resources.maps()
            .get(
                "de/gurkenlabs/litiengine/environment/tilemap/xml/test-infinite-map.tmx");

    assertTrue(map.isInfinite());
    assertEquals(64, map.getWidth());
    assertEquals(64, map.getHeight());
    assertEquals(2, map.getTileLayers().size());
    assertEquals(2, map.getTileLayers().size());

    assertEquals(1, map.getTileLayers().getFirst().getTile(15, 24).getGridId());
  }

  @Test
  void testURLAdapter() {
    // this test is only for the marshalling
    // the unmarshalling is covered by other map tests

    URL map = MapTests.class.getResource("test-map.tmx");
    URL tileset = MapTests.class.getResource("res/external-tileset.tsx");
    URL image1 = MapTests.class.getResource("tile.png");
    URL image2 = MapTests.class.getResource("tiles-test.png");

    URLAdapter ma = new URLAdapter(map);
    URLAdapter ta = new URLAdapter(tileset);
    assertEquals("#", ma.marshal(map));
    assertEquals("tile.png", ma.marshal(image1));
    assertEquals("res/external-tileset.tsx", ma.marshal(tileset));
    assertEquals("../tiles-test.png", ta.marshal(image2));
  }

  private static Stream<Arguments> getTileCustomProperties() {
    return Stream.of(
        Arguments.of(0, 5, 3, "foo", "bar"),
        Arguments.of(0, 9, 5, "baz", "bap"),
        Arguments.of(0, 10, 10, "custom", "multiline\nproperty"));
  }
}
