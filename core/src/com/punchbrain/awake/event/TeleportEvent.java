package com.punchbrain.awake.event;

import de.bitbrain.braingdx.event.GameEvent;

public class TeleportEvent implements GameEvent {

   private final String levelPath;

   public TeleportEvent(String levelPath) {
      this.levelPath = levelPath;
   }

   public String getLevelPath() {
      return levelPath;
   }
}
