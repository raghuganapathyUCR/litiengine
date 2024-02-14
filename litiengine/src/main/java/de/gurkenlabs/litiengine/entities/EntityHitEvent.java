package de.gurkenlabs.litiengine.entities;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.abilities.Ability;
import java.util.EventObject;
import javax.annotation.Nullable;

public class EntityHitEvent extends EventObject {
  private static final long serialVersionUID = 1582822545149624579L;
  private final int damage;

  private final boolean kill;
  @Nullable private final transient ICombatEntity executor;
  private final transient ICombatEntity hitEntity;
  @Nullable private final transient Ability ability;
  private final long time;

  EntityHitEvent(
      final ICombatEntity hitEntity, @Nullable final Ability ability, final int damage, final boolean kill) {
    super(hitEntity);
    this.executor = ability != null ? ability.getExecutor() : null;
    this.hitEntity = hitEntity;
    this.ability = ability;
    this.damage = damage;
    this.kill = kill;
    this.time = Game.time().now();
  }

  public int getDamage() {
    return this.damage;
  }

  @Nullable public ICombatEntity getExecutor() {
    return this.executor;
  }

  public ICombatEntity getHitEntity() {
    return this.hitEntity;
  }

  public boolean wasKilled() {
    return this.kill;
  }

  @Nullable public Ability getAbility() {
    return this.ability;
  }

  public long getTime() {
    return time;
  }
}
