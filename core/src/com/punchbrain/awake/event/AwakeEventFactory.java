package com.punchbrain.awake.event;

import com.badlogic.gdx.maps.MapProperties;
import de.bitbrain.braingdx.event.GameEvent;
import de.bitbrain.braingdx.event.GameEventFactory;
import de.bitbrain.braingdx.world.GameObject;

public class AwakeEventFactory implements GameEventFactory {

   @Override
   public GameEvent create(GameObject eventObject, GameObject producerObject) {
      if ("teleport".equals(eventObject.getType())) {
         MapProperties properties = (MapProperties)eventObject.getAttribute(MapProperties.class);
         return new TeleportEvent((String) properties.get("target"));
      }
      return null;
   }

   @Override
   public Object[] identifiers() {
      return new Object[]{"teleport"};
   }
}
