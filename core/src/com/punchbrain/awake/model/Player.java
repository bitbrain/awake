package com.punchbrain.awake.model;

import com.badlogic.gdx.physics.box2d.Body;
import de.bitbrain.braingdx.world.GameObject;

public class Player {

   private static final float SPEED = 110f;

   enum JumpState {
      JUMPING, FALLING, WALKING;
   }

   private final GameObject player;
   private final Body body;

   private JumpState previousState;

   public Player(GameObject player, Body body) {
      this.player = player;
      this.body = body;
   }

   public void stop() {
      if (getState() == JumpState.WALKING) {
         body.setLinearVelocity(0f, body.getLinearVelocity().y);
      }
   }

   public void moveLeft() {
      body.setLinearVelocity(-SPEED, body.getLinearVelocity().y);
   }

   public void moveRight() {
      body.setLinearVelocity(SPEED, body.getLinearVelocity().y);
   }

   public void jump() {
      if (getState() == JumpState.WALKING) {
         body.setLinearVelocity(body.getLinearVelocity().x, 0f);
         body.applyForceToCenter(0, 20060f, true);
      }
      previousState = getState();
   }

   private JumpState getState() {
      if(body.getLinearVelocity().y > 0.1 || (body.getLinearVelocity().y < 0 && previousState == JumpState.JUMPING)) {
         return JumpState.JUMPING;
      } else if (body.getLinearVelocity().y < -0.1) {
         return JumpState.FALLING;
      } else {
         return JumpState.WALKING;
      }
   }
}
