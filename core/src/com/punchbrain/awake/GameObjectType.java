package com.punchbrain.awake;

import de.bitbrain.braingdx.world.GameObject;

public enum GameObjectType {
   PLAYER,
   COLLISION;

   public boolean isTypeOf(GameObject object) {
      return PLAYER.name().equals(object.getType());
   }

}
