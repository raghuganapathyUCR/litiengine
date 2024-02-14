package de.gurkenlabs.litiengine.abilities;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.abilities.effects.Effect;
import de.gurkenlabs.litiengine.abilities.effects.Effect.EffectAppliedListener;
import de.gurkenlabs.litiengine.abilities.effects.Effect.EffectCeasedListener;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityPivot;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.util.geom.GeometricUtilities;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.Nullable;

@AbilityInfo
public abstract class Ability implements IRenderable {

  private final Collection<AbilityCastListener> abilityCastListeners;
  private final AbilityAttributes attributes;

  private final List<Effect> effects;
  private final Creature executor;
  private final EntityPivot entityPivot;

  private String name;
  private String description;
  private boolean multiTarget;
  private CastType castType;

  @Nullable private AbilityExecution currentExecution;

  /**
   * Initializes a new instance of the {@code Ability} class.
   *
   * @param executor The executing entity
   */
  protected Ability(final Creature executor) {
    this.abilityCastListeners = ConcurrentHashMap.newKeySet();
    this.effects = new CopyOnWriteArrayList<>();

    final AbilityInfo info = this.getClass().getAnnotation(AbilityInfo.class);
    this.attributes = new AbilityAttributes(info);
    this.executor = executor;
    this.name = info.name();
    this.multiTarget = info.multiTarget();
    this.description = info.description();
    this.castType = info.castType();
    this.entityPivot =
      new EntityPivot(executor, info.origin(), info.pivotOffsetX(), info.pivotOffsetY());
  }

  public void onCast(final AbilityCastListener listener) {
    abilityCastListeners.add(listener);
  }

  public void removeAbilityCastListener(AbilityCastListener listener) {
    abilityCastListeners.remove(listener);
  }

  public void onEffectApplied(final EffectAppliedListener listener) {
    for (final Effect effect : getEffects()) {
      // registers to all effects and their follow up effects recursively
      onEffectApplied(effect, listener);
    }
  }

  public void onEffectCeased(final EffectCeasedListener listener) {
    for (final Effect effect : getEffects()) {
      // registers to all effects and their follow up effects recursively
      onEffectCeased(effect, listener);
    }
  }

  public void addEffect(final Effect effect) {
    getEffects().add(effect);
  }

  public Shape calculateImpactArea() {
    return internalCalculateImpactArea(getExecutor().getAngle());
  }

  public Ellipse2D calculatePotentialImpactArea() {
    final int range = getAttributes().impact().get();
    final double arcX = getPivot().getPoint().getX() - range * 0.5;
    final double arcY = getPivot().getPoint().getY() - range * 0.5;

    return new Ellipse2D.Double(arcX, arcY, range, range);
  }

  public boolean canCast() {
    return !getExecutor().isDead() && !isOnCooldown();
  }

  public boolean isOnCooldown() {
    return (getCurrentExecution() != null
      && getCurrentExecution().getExecutionTicks() > 0
      && Game.time().since(getCurrentExecution().getExecutionTicks()) < getAttributes()
      .cooldown().get());
  }

  /**
   * Casts the ability by the temporal conditions of the specified game loop and the spatial circumstances of the specified environment. An ability
   * execution will be taken out that start applying all the effects of this ability.
   *
   * @return An {@link AbilityExecution} object that wraps all information about this execution of the ability.
   */
  @Nullable public AbilityExecution cast() {
    if (!canCast()) {
      return null;
    }
    this.currentExecution = new AbilityExecution(this);

    for (final AbilityCastListener listener : abilityCastListeners) {
      listener.abilityCast(currentExecution);
    }

    return getCurrentExecution();
  }

  public AbilityAttributes getAttributes() {
    return attributes;
  }

  public CastType getCastType() {
    return castType;
  }

  public float getCooldownInSeconds() {
    return (float) (getAttributes().cooldown().get() * 0.001);
  }

  @Nullable public AbilityExecution getCurrentExecution() {
    return currentExecution;
  }

  public String getDescription() {
    return description;
  }

  public Creature getExecutor() {
    return executor;
  }

  public String getName() {
    return name;
  }

  public EntityPivot getPivot() {
    return entityPivot;
  }

  public float getRemainingCooldownInSeconds() {
    if (getCurrentExecution() == null
      || getExecutor() == null
      || getExecutor().isDead()) {
      return 0;
    }

    // calculate cooldown in seconds
    return (float) (!canCast()
      ? (getAttributes().cooldown().get()
      - Game.time().since(getCurrentExecution().getExecutionTicks()))
      * 0.001
      : 0);
  }

  public boolean isActive() {
    return getCurrentExecution() != null
      && Game.time().since(getCurrentExecution().getExecutionTicks()) < getAttributes()
      .duration().get();
  }

  public boolean isMultiTarget() {
    return multiTarget;
  }

  @Override
  public void render(final Graphics2D g) {
    g.setColor(new Color(255, 255, 0, 25));
    Game.graphics().renderShape(g, calculateImpactArea(), true);
    final Stroke oldStroke = g.getStroke();
    g.setStroke(new BasicStroke(2f));
    g.setColor(new Color(255, 255, 0, 50));
    Game.graphics().renderOutline(g, calculateImpactArea(), true);
    g.setStroke(oldStroke);
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setMultiTarget(boolean multiTarget) {
    this.multiTarget = multiTarget;
  }

  public void setCastType(CastType castType) {
    this.castType = castType;
  }

  void setCurrentExecution(AbilityExecution ae) {
    this.currentExecution = ae;
  }

  public List<Effect> getEffects() {
    return effects;
  }

  protected Shape internalCalculateImpactArea(final double angle) {
    final int impact = getAttributes().impact().get();
    final int impactAngle = getAttributes().impactAngle().get();
    final double arcX = getPivot().getPoint().getX() - impact;
    final double arcY = getPivot().getPoint().getY() - impact;

    // project
    final Point2D appliedRange =
      GeometricUtilities.project(
        new Point2D.Double(arcX, arcY), angle, getAttributes().range().get() * 0.5);
    final double start = angle - 90 - (impactAngle / 2.0);
    if (impactAngle % 360 == 0) {
      return new Ellipse2D.Double(appliedRange.getX(), appliedRange.getY(), impact * 2d, impact * 2d);
    }

    return new Arc2D.Double(
      appliedRange.getX(), appliedRange.getY(), impact * 2d, impact * 2d, start, impactAngle, Arc2D.PIE);
  }

  private void onEffectApplied(final Effect effect, final EffectAppliedListener listener) {
    effect.onEffectApplied(listener);

    for (final Effect followUp : effect.getFollowUpEffects()) {
      onEffectApplied(followUp, listener);
    }
  }

  private void onEffectCeased(final Effect effect, final EffectCeasedListener listener) {
    effect.onEffectCeased(listener);

    for (final Effect followUp : effect.getFollowUpEffects()) {
      onEffectCeased(followUp, listener);
    }
  }

  @FunctionalInterface
  public interface AbilityCastListener extends EventListener {

    void abilityCast(AbilityExecution execution);
  }
}
