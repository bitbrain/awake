package com.punchbrain.awake.event;

import de.bitbrain.braingdx.event.GameEvent;
import de.bitbrain.braingdx.world.GameObject;

public class TeleportEvent implements GameEvent {

   private final String file;
   private final String id;
   private final String target;
   private final GameObject producer;

   public TeleportEvent(String file, String id, String target, GameObject producer) {
      this.file = file;
      this.id = id;
      this.target = target;
      this.producer = producer;
   }

   public String getFile() {
      return file;
   }

   public String getId() {
      return id;
   }

   public String getTarget() {
      return target;
   }

   public GameObject getProducer() {
      return producer;
   }
}
