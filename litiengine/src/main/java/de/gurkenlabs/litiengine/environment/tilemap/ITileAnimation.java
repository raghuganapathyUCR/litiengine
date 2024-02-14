package de.gurkenlabs.litiengine.environment.tilemap;

import java.util.List;
import javax.annotation.Nullable;

public interface ITileAnimation {
  public List<ITileAnimationFrame> getFrames();

  public int getTotalDuration();

  public ITileAnimationFrame getCurrentFrame();
}
