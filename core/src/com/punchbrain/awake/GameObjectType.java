package com.punchbrain.awake;

import de.bitbrain.braingdx.world.GameObject;

public enum GameObjectType {
   PLAYER,
   COLLISION,
   TELEPORT,
   LIGHT;

   public boolean isTypeOf(GameObject object) {
      return object.getType() != null && this.name().equalsIgnoreCase(object.getType().toString());
   }

}
