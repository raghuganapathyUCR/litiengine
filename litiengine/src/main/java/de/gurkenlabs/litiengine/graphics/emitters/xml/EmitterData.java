package de.gurkenlabs.litiengine.graphics.emitters.xml;

import de.gurkenlabs.litiengine.Align;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.configuration.Quality;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.graphics.emitters.particles.ParticleType;
import de.gurkenlabs.litiengine.physics.Collision;
import de.gurkenlabs.litiengine.resources.Resource;
import de.gurkenlabs.litiengine.util.ColorHelper;
import de.gurkenlabs.litiengine.util.MathUtilities;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

import java.awt.Color;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;

@XmlRootElement(name = "emitter")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmitterData implements Serializable, Resource {
  public static final Color DEFAULT_COLOR = new Color(0, 165, 188, 204);
  public static final String DEFAULT_SPRITESHEET = "";
  public static final String DEFAULT_NAME = "Custom Emitter";
  public static final String DEFAULT_TEXT = "LITI";
  public static final boolean DEFAULT_ANIMATE_SPRITE = true;
  public static final boolean DEFAULT_LOOP_SPRITE = true;
  public static final boolean DEFAULT_FADE = true;
  public static final boolean DEFAULT_FADE_ON_COLLISION = false;
  public static final boolean DEFAULT_OUTLINE_ONLY = false;
  public static final boolean DEFAULT_ANTIALIASING = false;
  public static final Collision DEFAULT_COLLISION = Collision.NONE;
  public static final ParticleType DEFAULT_PARTICLE_TYPE = ParticleType.RECTANGLE;
  public static final Quality DEFAULT_REQUIRED_QUALITY = Quality.VERYLOW;
  public static final Align DEFAULT_ORIGIN_ALIGN = Align.CENTER;
  public static final Valign DEFAULT_ORIGIN_VALIGN = Valign.MIDDLE;
  public static final float DEFAULT_WIDTH = 16f;
  public static final float DEFAULT_HEIGHT = 16f;
  public static final float DEFAULT_COLOR_VARIANCE = 0f;
  public static final float DEFAULT_ALPHA_VARIANCE = 0f;
  public static final int DEFAULT_UPDATERATE = 40;
  public static final int DEFAULT_SPAWNAMOUNT = 20;
  public static final int DEFAULT_SPAWNRATE = 100;
  public static final int DEFAULT_MAXPARTICLES = 400;
  public static final int DEFAULT_DURATION = 0;
  public static final int DEFAULT_MIN_PARTICLE_TTL = 400;
  public static final int DEFAULT_MAX_PARTICLE_TTL = 1500;
  public static final float DEFAULT_MIN_OFFSET_X = -4f;
  public static final float DEFAULT_MAX_OFFSET_X = 4f;
  public static final float DEFAULT_MIN_OFFSET_Y = -4f;
  public static final float DEFAULT_MAX_OFFSET_Y = 4f;
  public static final float DEFAULT_MIN_DELTA_WIDTH = -.1f;
  public static final float DEFAULT_MAX_DELTA_WIDTH = .1f;
  public static final float DEFAULT_MIN_DELTA_HEIGHT = -.1f;
  public static final float DEFAULT_MAX_DELTA_HEIGHT = .1f;
  public static final float DEFAULT_MIN_ACCELERATION_X = -.01f;
  public static final float DEFAULT_MAX_ACCELERATION_X = .01f;
  public static final float DEFAULT_MIN_ACCELERATION_Y = -.01f;
  public static final float DEFAULT_MAX_ACCELERATION_Y = .01f;
  public static final float DEFAULT_MIN_ANGLE = 0;
  public static final float DEFAULT_MAX_ROTATION = 360;
  public static final float DEFAULT_MIN_DELTA_ANGLE = -1;
  public static final float DEFAULT_MAX_DELTA_ANGLE = 1;
  public static final float DEFAULT_MIN_VELOCITY_X = -.1f;
  public static final float DEFAULT_MAX_VELOCITY_X = .1f;
  public static final float DEFAULT_MIN_VELOCITY_Y = -.1f;
  public static final float DEFAULT_MAX_VELOCITY_Y = .1f;
  public static final float DEFAULT_MIN_WIDTH = 2f;
  public static final float DEFAULT_MAX_WIDTH = 6f;
  public static final float DEFAULT_MIN_HEIGHT = 2f;
  public static final float DEFAULT_MAX_HEIGHT = 6f;
  @Serial
  private static final long serialVersionUID = 50238884097993529L;
  @XmlElement
  private float alphaVariance;

  @XmlElement
  private boolean animateSprite;

  @XmlElement
  private boolean loopSprite;

  @XmlElement
  private Collision collision;

  @XmlElement
  private Quality requiredQuality;

  @XmlElement
  private float colorVariance;

  @XmlElementWrapper
  @XmlElement(name = "color")
  private List<String> colors;

  @Nullable @XmlTransient
  private List<Color> decodedColors;

  @XmlElement
  private ParticleParameter deltaHeight;

  @XmlElement
  private ParticleParameter deltaWidth;

  @XmlElement
  private ParticleParameter velocityX;

  @XmlElement
  private ParticleParameter velocityY;

  @XmlAttribute
  private int emitterDuration;

  @XmlElement
  private boolean fade;

  @XmlElement
  private boolean fadeOnCollision;

  @XmlElement
  private boolean outlineOnly;

  @XmlElement
  private boolean antiAliasing;

  @XmlElement
  private ParticleParameter accelerationX;

  @XmlElement
  private ParticleParameter accelerationY;

  @XmlElement
  private ParticleParameter angle;

  @XmlElement
  private ParticleParameter deltaAngle;

  @XmlAttribute
  private float height;

  @XmlAttribute
  private int maxParticles;

  @Nullable @XmlAttribute
  private String name;

  @XmlElement
  private Align originAlign;

  @XmlElement
  private Valign originValign;

  @XmlElement
  private ParticleParameter particleHeight;

  @XmlElement
  private ParticleParameter particleTTL;

  @XmlElementWrapper
  @XmlElement(name = "text")
  private List<String> texts;

  @XmlAttribute
  private ParticleType particleType;

  @XmlElement
  private ParticleParameter particleWidth;

  @XmlAttribute
  private int spawnAmount;

  @XmlAttribute
  private int spawnRate;

  @Nullable @XmlElement
  private String spritesheet;

  @XmlAttribute
  private int updateRate;

  @XmlAttribute
  private float width;

  @XmlElement
  private ParticleParameter offsetX;

  @XmlElement
  private ParticleParameter offsetY;

  public EmitterData() {
    // initialize fields required for rendering and updating properly.
    this.requiredQuality = DEFAULT_REQUIRED_QUALITY;
    this.offsetX = new ParticleParameter();
    this.offsetY = new ParticleParameter();
    this.deltaWidth = new ParticleParameter();
    this.deltaHeight = new ParticleParameter();
    this.angle = new ParticleParameter();
    this.deltaAngle = new ParticleParameter();
    this.velocityX = new ParticleParameter();
    this.velocityY = new ParticleParameter();
    this.accelerationX = new ParticleParameter();
    this.accelerationY = new ParticleParameter();
    this.particleWidth = new ParticleParameter();
    this.particleHeight = new ParticleParameter();
    this.particleTTL = new ParticleParameter();
    this.collision = DEFAULT_COLLISION;
    this.particleType = DEFAULT_PARTICLE_TYPE;
    this.originValign = DEFAULT_ORIGIN_VALIGN;
    this.originAlign = DEFAULT_ORIGIN_ALIGN;
    this.setColor(DEFAULT_COLOR);
  }

  @XmlTransient
  public float getAlphaVariance() {
    return this.alphaVariance;
  }

  public void setAlphaVariance(final float alphaVariance) {
    this.alphaVariance = MathUtilities.clamp(alphaVariance, 0, 1);
  }

  @XmlTransient
  public Collision getCollision() {
    return this.collision;
  }

  public void setCollision(final Collision collision) {
    this.collision = collision;
  }

  @XmlTransient
  public Quality getRequiredQuality() {
    return this.requiredQuality;
  }

  public void setRequiredQuality(final Quality minQuality) {
    this.requiredQuality = minQuality;
  }

  @XmlTransient
  public float getColorVariance() {
    return this.colorVariance;
  }

  public void setColorVariance(final float colorVariance) {
    this.colorVariance = MathUtilities.clamp(colorVariance, 0, 1);
  }

  @XmlTransient
  public List<String> getColors() {
    return this.colors;
  }

  public List<Color> getDecodedColors() {
    if (this.decodedColors != null) {
      return this.decodedColors;
    }

    List<Color> newColors = new ArrayList<>();
    for (var color : this.getColors()) {
      Color decoded = ColorHelper.decode(color);
      newColors.add(decoded != null ? decoded : DEFAULT_COLOR);
    }

    this.decodedColors = newColors;
    return this.decodedColors;
  }

  public void setColors(final List<String> colors) {
    this.colors = colors;
    this.decodedColors = null;
  }

  public void setColors(final Color... colors) {
    this.colors = Arrays.stream(colors).map(ColorHelper::encode).toList();
  }

  @XmlTransient
  public ParticleParameter getDeltaHeight() {
    return this.deltaHeight;
  }

  public void setDeltaHeight(final ParticleParameter deltaHeight) {
    this.deltaHeight = deltaHeight;
  }

  @XmlTransient
  public ParticleParameter getDeltaWidth() {
    return this.deltaWidth;
  }

  public void setDeltaWidth(final ParticleParameter deltaWidth) {
    this.deltaWidth = deltaWidth;
  }

  @XmlTransient
  public ParticleParameter getAngle() {
    return this.angle;
  }

  public void setAngle(final ParticleParameter angle) {
    this.angle = angle;
  }

  @XmlTransient
  public ParticleParameter getDeltaAngle() {
    return this.deltaAngle;
  }

  @XmlTransient
  public ParticleParameter getVelocityX() {
    return this.velocityX;
  }

  public void setVelocityX(final ParticleParameter velocityX) {
    this.velocityX = velocityX;
  }

  @XmlTransient
  public ParticleParameter getVelocityY() {
    return this.velocityY;
  }

  public void setVelocityY(final ParticleParameter velocityY) {
    this.velocityY = velocityY;
  }

  @XmlTransient
  public int getEmitterDuration() {
    return this.emitterDuration;
  }

  public void setEmitterDuration(final int emitterDuration) {
    this.emitterDuration = emitterDuration;
  }

  @XmlTransient
  public ParticleParameter getAccelerationX() {
    return this.accelerationX;
  }

  public void setAccelerationX(final ParticleParameter accelerationX) {
    this.accelerationX = accelerationX;
  }

  @XmlTransient
  public ParticleParameter getAccelerationY() {
    return this.accelerationY;
  }

  public void setAccelerationY(final ParticleParameter accelerationY) {
    this.accelerationY = accelerationY;
  }

  @XmlTransient
  public float getHeight() {
    return this.height;
  }

  public void setHeight(final float height) {
    this.height = height;
  }

  @XmlTransient
  public int getMaxParticles() {
    return this.maxParticles;
  }

  public void setMaxParticles(final int maxParticles) {
    this.maxParticles = maxParticles;
  }

  @Nullable @XmlTransient
  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setName(@Nullable final String name) {
    this.name = name;
  }

  @XmlTransient
  public Align getOriginAlign() {
    return this.originAlign;
  }

  public void setOriginAlign(final Align align) {
    this.originAlign = align;
  }

  @XmlTransient
  public Valign getOriginValign() {
    return this.originValign;
  }

  public void setOriginValign(final Valign valign) {
    this.originValign = valign;
  }

  @XmlTransient
  public ParticleParameter getParticleHeight() {
    return this.particleHeight;
  }

  public void setParticleHeight(final ParticleParameter particleHeight) {
    this.particleHeight = particleHeight;
  }

  @XmlTransient
  public ParticleParameter getParticleTTL() {
    return this.particleTTL;
  }

  public void setParticleTTL(final ParticleParameter particleTTL) {
    this.particleTTL = particleTTL;
  }

  @XmlTransient
  public List<String> getTexts() {
    return this.texts;
  }

  public void setTexts(final List<String> texts) {
    this.texts = texts;
  }

  @XmlTransient
  public ParticleType getParticleType() {
    return this.particleType;
  }

  public void setParticleType(final ParticleType particleType) {
    this.particleType = particleType;
  }

  @XmlTransient
  public ParticleParameter getParticleWidth() {
    return this.particleWidth;
  }

  public void setParticleWidth(final ParticleParameter particleWidth) {
    this.particleWidth = particleWidth;
  }

  @XmlTransient
  public ParticleParameter getParticleOffsetX() {
    return this.offsetX;
  }

  public void setParticleOffsetX(final ParticleParameter x) {
    this.offsetX = x;
  }

  @XmlTransient
  public ParticleParameter getParticleOffsetY() {
    return this.offsetY;
  }

  public void setParticleOffsetY(final ParticleParameter y) {
    this.offsetY = y;
  }

  @XmlTransient
  public int getSpawnAmount() {
    return this.spawnAmount;
  }

  public void setSpawnAmount(final int spawnAmount) {
    this.spawnAmount = spawnAmount;
  }

  @XmlTransient
  public int getSpawnRate() {
    return this.spawnRate;
  }

  public void setSpawnRate(final int spawnRate) {
    this.spawnRate = spawnRate;
  }

  @Nullable @XmlTransient
  public String getSpritesheet() {
    return this.spritesheet;
  }

  public void setSpritesheet(final String spritesheetName) {
    this.spritesheet = spritesheetName;
  }

  public void setSpritesheet(final Spritesheet spritesheet) {
    this.spritesheet = spritesheet.getName();
  }

  @XmlTransient
  public int getUpdateRate() {
    return this.updateRate;
  }

  public void setUpdateRate(final int updateRate) {
    if (updateRate == 0) {
      return;
    }

    this.updateRate = updateRate;
  }

  @XmlTransient
  public float getWidth() {
    return this.width;
  }

  public void setWidth(final float width) {
    this.width = width;
  }

  public boolean isAnimatingSprite() {
    return this.animateSprite;
  }

  public boolean isLoopingSprite() {
    return this.loopSprite;
  }

  public boolean isFading() {
    return this.fade;
  }

  public boolean isFadingOnCollision() {
    return this.fadeOnCollision;
  }

  public boolean isOutlineOnly() {
    return this.outlineOnly;
  }

  public void setOutlineOnly(final boolean outlineOnly) {
    this.outlineOnly = outlineOnly;
  }

  public boolean isAntiAliased() {
    return this.antiAliasing;
  }

  public void setAnimateSprite(final boolean animateSprite) {
    this.animateSprite = animateSprite;
  }

  public void setLoopSprite(final boolean loopSprite) {
    this.loopSprite = loopSprite;
  }

  public void setColor(final Color color) {
    final List<String> tmpList = new ArrayList<>();
    tmpList.add(ColorHelper.encode(color));
    this.colors = tmpList;
  }

  public void initDefaults() {
    this.width = DEFAULT_WIDTH;
    this.height = DEFAULT_HEIGHT;
    this.originAlign = DEFAULT_ORIGIN_ALIGN;
    this.originValign = DEFAULT_ORIGIN_VALIGN;
    this.offsetX.setMinValue(DEFAULT_MIN_OFFSET_X);
    this.offsetX.setMaxValue(DEFAULT_MAX_OFFSET_X);
    this.offsetY.setMinValue(DEFAULT_MIN_OFFSET_Y);
    this.offsetY.setMaxValue(DEFAULT_MAX_OFFSET_Y);
    this.deltaWidth.setMinValue(DEFAULT_MIN_DELTA_WIDTH);
    this.deltaWidth.setMaxValue(DEFAULT_MAX_DELTA_WIDTH);
    this.deltaHeight.setMinValue(DEFAULT_MIN_DELTA_HEIGHT);
    this.deltaHeight.setMaxValue(DEFAULT_MAX_DELTA_HEIGHT);
    this.angle.setMinValue(DEFAULT_MIN_ANGLE);
    this.angle.setMaxValue(DEFAULT_MAX_ROTATION);
    this.deltaAngle.setMinValue(DEFAULT_MIN_DELTA_ANGLE);
    this.deltaAngle.setMaxValue(DEFAULT_MAX_DELTA_ANGLE);
    this.velocityX.setMinValue(DEFAULT_MIN_VELOCITY_X);
    this.velocityX.setMaxValue(DEFAULT_MAX_VELOCITY_X);
    this.velocityY.setMinValue(DEFAULT_MIN_VELOCITY_Y);
    this.velocityY.setMaxValue(DEFAULT_MAX_VELOCITY_Y);
    this.accelerationX.setMinValue(DEFAULT_MIN_ACCELERATION_X);
    this.accelerationX.setMaxValue(DEFAULT_MAX_ACCELERATION_X);
    this.accelerationY.setMinValue(DEFAULT_MIN_ACCELERATION_Y);
    this.accelerationY.setMaxValue(DEFAULT_MAX_ACCELERATION_Y);
    this.particleWidth.setMinValue(DEFAULT_MIN_WIDTH);
    this.particleWidth.setMaxValue(DEFAULT_MAX_WIDTH);
    this.particleHeight.setMinValue(DEFAULT_MIN_HEIGHT);
    this.particleHeight.setMaxValue(DEFAULT_MAX_HEIGHT);
    this.particleTTL.setMinValue(DEFAULT_MIN_PARTICLE_TTL);
    this.particleTTL.setMaxValue(DEFAULT_MAX_PARTICLE_TTL);

    this.setColor(DEFAULT_COLOR);
    this.emitterDuration = DEFAULT_DURATION;
    this.colorVariance = DEFAULT_COLOR_VARIANCE;
    this.alphaVariance = DEFAULT_ALPHA_VARIANCE;
    this.updateRate = DEFAULT_UPDATERATE;
    this.maxParticles = DEFAULT_MAXPARTICLES;
    this.name = DEFAULT_NAME;
    this.setText(DEFAULT_TEXT);
    this.spawnAmount = DEFAULT_SPAWNAMOUNT;
    this.spawnRate = DEFAULT_SPAWNRATE;
    this.animateSprite = DEFAULT_ANIMATE_SPRITE;
    this.loopSprite = DEFAULT_LOOP_SPRITE;
    this.spritesheet = DEFAULT_SPRITESHEET;
    this.fade = DEFAULT_FADE;
    this.fadeOnCollision = DEFAULT_FADE_ON_COLLISION;
    this.outlineOnly = DEFAULT_OUTLINE_ONLY;
    this.antiAliasing = DEFAULT_ANTIALIASING;
  }

  public void setDeltaRotation(final ParticleParameter deltaRotation) {
    this.deltaAngle = deltaRotation;
  }

  public void setFade(final boolean fade) {
    this.fade = fade;
  }

  public void setFadeOnCollision(final boolean fadeOnCollision) {
    this.fadeOnCollision = fadeOnCollision;
  }

  public void setAntiAliasing(final boolean antiAliasing) {
    this.antiAliasing = antiAliasing;
  }

  public void setText(final String text) {
    final List<String> tmpList = new ArrayList<>();
    tmpList.add(text);
    this.texts = tmpList;
  }
}
