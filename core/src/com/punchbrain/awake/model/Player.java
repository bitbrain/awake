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

   public void stop() {
      body.setLinearVelocity(0f, 0f);
   }

   public void moveLeft() {
      body.setLinearVelocity(-50f, 0);
   }

   public void moveRight() {
      body.setLinearVelocity(50f, 0);
   }

   public void jump() {
      // TODO
      body.applyForceToCenter(0f,64, true);
   }
}
