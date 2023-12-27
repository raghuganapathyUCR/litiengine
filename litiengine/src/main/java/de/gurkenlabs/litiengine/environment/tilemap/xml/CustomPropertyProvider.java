package de.gurkenlabs.litiengine.environment.tilemap.xml;

import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import de.gurkenlabs.litiengine.environment.tilemap.IMapObject;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import de.gurkenlabs.litiengine.environment.tilemap.ICustomProperty;
import de.gurkenlabs.litiengine.environment.tilemap.ICustomPropertyProvider;


@XmlAccessorType(XmlAccessType.FIELD)
public class CustomPropertyProvider implements ICustomPropertyProvider {
  @XmlElement
  @XmlJavaTypeAdapter(CustomPropertyAdapter.class)
  private Map<String, ICustomProperty> properties;

  public CustomPropertyProvider() {
    this.properties = new Hashtable<>(); // use Hashtable because it rejects null keys and null values
  }

  /**
   * Copy Constructor for copying instances of CustomPropertyProviders.
   *
   * @param propertyProviderToBeCopied the PropertyProvider we want to copy
   */
  public CustomPropertyProvider(ICustomPropertyProvider propertyProviderToBeCopied) {
    this.properties = propertyProviderToBeCopied.getProperties().entrySet().stream()
      .collect(Collectors.toMap(Entry::getKey, e -> new CustomProperty((e.getValue()))));
  }

  @Override
  public Map<String, ICustomProperty> getProperties() {
    return this.properties;
  }

  @Override
  public boolean hasCustomProperty(String propertyName) {
    return this.getProperties().containsKey(propertyName);
  }

  @Override
  public ICustomProperty getProperty(String propertyName) {
    return this.getProperties().get(propertyName);
  }

  @Override
  public void setValue(String propertyName, ICustomProperty value) {
    if (value != null) {
      this.getProperties().put(propertyName, value);
    }
  }

  @Override
  public String getStringValue(String propertyName) {
    return this.getStringValue(propertyName, null);
  }

  @Override
  public String getStringValue(String propertyName, String defaultValue) {
    ICustomProperty property = this.getProperty(propertyName);
    if (property == null) {
      return defaultValue;
    }
    return property.getAsString();
  }


  @Override
  public char getCharValue(String propertyName) {
    return this.getCharValue(propertyName, (char)0);
  }

  @Override
  public char getCharValue(String propertyName, char defaultValue) {
    ICustomProperty property = this.getProperty(propertyName);
    if (property == null) {
      return defaultValue;
    }
    return property.getAsChar();
  }

  @Override
  public int getIntValue(String propertyName) {
    return this.getIntValue(propertyName, 0);
  }

  @Override
  public int getIntValue(String propertyName, int defaultValue) {
    ICustomProperty property = this.getProperty(propertyName);
    if (property == null) {
      return defaultValue;
    }
    return property.getAsInt();
  }

  @Override
  public long getLongValue(String propertyName, long defaultValue) {
    ICustomProperty property = this.getProperty(propertyName);
    if (property == null) {
      return defaultValue;
    }
    return property.getAsLong();
  }

  @Override
  public short getShortValue(String propertyName) {
    return this.getShortValue(propertyName, (short) 0);
  }

  @Override
  public short getShortValue(String propertyName, short defaultValue) {
    ICustomProperty property = this.getProperty(propertyName);
    if (property == null) {
      return defaultValue;
    }
    return property.getAsShort();
  }

  @Override
  public byte getByteValue(String propertyName) {
    return this.getByteValue(propertyName, (byte) 0);
  }

  @Override
  public byte getByteValue(String propertyName, byte defaultValue) {
    ICustomProperty property = this.getProperty(propertyName);
    if (property == null) {
      return defaultValue;
    }
    return property.getAsByte();
  }

  @Override
  public boolean getBoolValue(String propertyName) {
    return this.getBoolValue(propertyName, false);
  }

  @Override
  public boolean getBoolValue(String propertyName, boolean defaultValue) {
    ICustomProperty property = this.getProperty(propertyName);
    if (property == null) {
      return defaultValue;
    }
    return property.getAsBool();
  }

  @Override
  public float getFloatValue(String propertyName) {
    return this.getFloatValue(propertyName, 0f);
  }

  @Override
  public float getFloatValue(String propertyName, float defaultValue) {
    ICustomProperty property = this.getProperty(propertyName);
    if (property == null) {
      return defaultValue;
    }
    return property.getAsFloat();
  }

  @Override
  public double getDoubleValue(String propertyName) {
    return this.getDoubleValue(propertyName, 0.0);
  }

  @Override
  public double getDoubleValue(String propertyName, double defaultValue) {
    ICustomProperty property = this.getProperty(propertyName);
    if (property == null) {
      return defaultValue;
    }

    return property.getAsDouble();
  }

  @Override
  public Color getColorValue(String propertyName) {
    return this.getColorValue(propertyName, null);
  }

  @Override
  public Color getColorValue(String propertyName, Color defaultValue) {
    ICustomProperty property = this.getProperty(propertyName);
    if (property == null) {
      return defaultValue;
    }

    Color value = property.getAsColor();
    if (value == null) {
      return defaultValue;
    }

    return value;
  }

  @Override
  public <T extends Enum<T>> T getEnumValue(String propertyName, Class<T> enumType) {
    return this.getEnumValue(propertyName, enumType, null);
  }

  @Override
  public <T extends Enum<T>> T getEnumValue(String propertyName, Class<T> enumType, T defaultValue) {
    ICustomProperty property = this.getProperty(propertyName);
    if (property == null) {
      return defaultValue;
    }

    T value = property.getAsEnum(enumType);
    if (value == null) {
      return defaultValue;
    }

    return value;
  }

  @Override
  public URL getFileValue(String propertyName) {
    return this.getFileValue(propertyName, null);
  }

  @Override
  public URL getFileValue(String propertyName, URL defaultValue) {
    ICustomProperty property = this.getProperty(propertyName);
    if (property == null) {
      return defaultValue;
    }

    URL value = property.getAsFile();
    if (value == null) {
      return defaultValue;
    }

    return value;
  }

  @Override
  public int getMapObjectId(String propertyName) {
    ICustomProperty property = this.getProperty(propertyName);
    if (property == null) {
      return 0;
    }

    return property.getMapObjectId();
  }

  private ICustomProperty createPropertyIfAbsent(String propertyName) {
    return this.getProperties().computeIfAbsent(propertyName, n -> new CustomProperty());
  }

  @Override
  public void setValue(String propertyName, URL value) {
    ICustomProperty property = createPropertyIfAbsent(propertyName);
    property.setType(CustomPropertyType.FILE);
    property.setValue(value);
  }

  @Override
  public void setValue(String propertyName, String value) {
    if (value != null) {
      ICustomProperty property = createPropertyIfAbsent(propertyName);
      property.setType(CustomPropertyType.STRING);
      property.setValue(value);
    } else {
      this.getProperties().remove(propertyName);
    }
  }

  @Override
  public void setValue(String propertyName, boolean value) {
    ICustomProperty property = createPropertyIfAbsent(propertyName);
    property.setType(CustomPropertyType.BOOL);
    property.setValue(value);
  }

  @Override
  public void setValue(String propertyName, byte value) {
    ICustomProperty property = createPropertyIfAbsent(propertyName);
    property.setType(CustomPropertyType.INT);
    property.setValue(value);
  }

  @Override
  public void setValue(String propertyName, short value) {
    setValue(propertyName, (int) value);
  }

  @Override
  public void setValue(String propertyName, char value) {
    setValue(propertyName, String.valueOf(value));
  }

  @Override
  public void setValue(String propertyName, int value) {
    ICustomProperty property = createPropertyIfAbsent(propertyName);
    property.setType(CustomPropertyType.INT);
    property.setValue(value);
  }

  @Override
  public void setValue(String propertyName, long value) {
    ICustomProperty property = createPropertyIfAbsent(propertyName);
    property.setType(CustomPropertyType.INT);
    property.setValue(value);
  }

  @Override
  public void setValue(String propertyName, float value) {
    ICustomProperty property = createPropertyIfAbsent(propertyName);
    property.setType(CustomPropertyType.FLOAT);
    property.setValue(value);
  }

  @Override
  public void setValue(String propertyName, double value) {
    ICustomProperty property = createPropertyIfAbsent(propertyName);
    property.setType(CustomPropertyType.FLOAT);
    property.setValue(value);
  }

  @Override
  public void setValue(String propertyName, Color value) {
    ICustomProperty property = createPropertyIfAbsent(propertyName);
    property.setType(CustomPropertyType.COLOR);
    property.setValue(value);
  }

  @Override
  public void setValue(String propertyName, Enum<?> value) {
    ICustomProperty property = createPropertyIfAbsent(propertyName);
    property.setType(CustomPropertyType.STRING);
    property.setValue(value);
  }

  @Override
  public void setValue(String propertyName, IMapObject value) {
    ICustomProperty property = createPropertyIfAbsent(propertyName);
    property.setType(CustomPropertyType.OBJECT);
    property.setValue(value.getId());
  }

  @Override
  public void setProperties(Map<String, ICustomProperty> props) {
    this.getProperties().clear();
    if (props != null) {
      this.getProperties().putAll(props);
    }
  }

  @Override
  public void removeProperty(String propertyName) {
    this.getProperties().remove(propertyName);
  }

  @SuppressWarnings("unused")
  private void afterUnmarshal(Unmarshaller u, Object parent) {
    if (this.properties == null) {
      this.properties = new Hashtable<>();
    }
  }

  void finish(URL location) throws TmxException {
    // blank base case
  }

  @Override
  public List<String> getCommaSeparatedStringValues(String propertyName, String defaultValue) {
    List<String> values = new ArrayList<>();
    String valuesStr = this.getStringValue(propertyName, defaultValue);
    if (valuesStr != null && !valuesStr.isEmpty()) {
      for (String value : valuesStr.split(",")) {
        if (value != null) {
          values.add(value);
        }
      }
    }
    return values;
  }
}
