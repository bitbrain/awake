package com.punchbrain.awake.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import de.bitbrain.braingdx.world.GameObject;

public class Player {

   private final GameObject player;
   private final Body body;

   public Player(GameObject player, Body body) {
      this.player = player;
      this.body = body;
   }


   public void moveLeft() {
      body.applyForce(-1, 0f, 0f, 0f, true);
   }

   public void moveRight() {
      body.applyForce(1, 0f, 0f, 0f, true);
   }

   public void jump() {

   }
}
