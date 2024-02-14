package de.gurkenlabs.litiengine.entities.behavior;
import javax.annotation.Nullable;

public abstract class Transition implements Comparable<Transition> {
  private final int priority;
  @Nullable private State targetState;

  protected Transition(final int priority) {
    this.priority = priority;
  }

  protected Transition(final int priority, final State targetState) {
    this(priority);
    this.targetState = targetState;
  }

  @Override
  public int compareTo(final Transition other) {
    return Integer.compare(this.getPriority(), other.getPriority());
  }

  @Nullable public State getNextState() {
    return this.targetState;
  }

  public int getPriority() {
    return this.priority;
  }

  protected abstract boolean conditionsFullfilled();
}
