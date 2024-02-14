package de.gurkenlabs.litiengine.graphics.animation;

import java.util.EventListener;
import javax.annotation.Nullable;

public interface KeyFrameListener extends EventListener {
  public void currentFrameChanged(@Nullable KeyFrame previousFrame, KeyFrame currentFrame);
}
