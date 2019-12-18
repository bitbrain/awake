package com.punchbrain.awake.event;

import de.bitbrain.braingdx.event.GameEvent;

public class TextEvent implements GameEvent {

   private String message;

   public TextEvent(String message) {
      this.message = message;
   }

   public String getMessage() {
      return message;
   }
}
