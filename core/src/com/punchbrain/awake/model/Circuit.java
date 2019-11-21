package com.punchbrain.awake.model;

import com.badlogic.gdx.physics.box2d.Body;
import de.bitbrain.braingdx.world.GameObject;

public class Circuit {

   private final GameObject lamp;
   private final GameObject flipSwitch;

   private boolean isOn;

   public Circuit(GameObject lamp, GameObject flipSwitch) {
      isOn = false;
      this.lamp = lamp;
      this.flipSwitch = flipSwitch;
   }

   public void flipSwitch(){
      isOn = !isOn;
   };

   public GameObject getLamp() {
      return lamp;
   }

   public GameObject getFlipSwitch() {
      return flipSwitch;
   }
}
