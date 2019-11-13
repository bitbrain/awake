package com.punchbrain.awake;

import de.bitbrain.braingdx.world.GameObject;

public enum GameObjectType {
   PLAYER,
   COLLISION,
   TELEPORT;

   public boolean isTypeOf(GameObject object) {
      return this.name().equals(object.getType());
   }

}
