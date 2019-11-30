package com.punchbrain.awake.event;

import de.bitbrain.braingdx.event.GameEvent;
import de.bitbrain.braingdx.event.GameEventFactory;
import de.bitbrain.braingdx.world.GameObject;

public class AwakeEventFactory implements GameEventFactory {

   @Override
   public GameEvent create(GameObject eventObject, GameObject producerObject) {
      if ("teleport".equals(eventObject.getType())) {
         final String file = eventObject.getAttribute("file",String.class);
         final String id = eventObject.getAttribute("id", String.class);
         final String target = eventObject.getAttribute("target", String.class);
         return new TeleportEvent(file, id, target, producerObject);
      }
      return null;
   }

   @Override
   public Object[] identifiers() {
      return new Object[]{"teleport"};
   }
}
