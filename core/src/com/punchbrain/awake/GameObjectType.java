package com.punchbrain.awake;

import de.bitbrain.braingdx.world.GameObject;

public enum GameObjectType {
   PLAYER,
   COLLISION,
   TELEPORT,
   LIGHT,
   CIRCUIT_LAMP,
   CIRCUIT_FLIP_SWITCH;

   public boolean isTypeOf(GameObject object) {
      return object.getType() != null && this.name().equalsIgnoreCase(object.getType().toString());
   }

}
