package com.punchbrain.awake.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.mappings.Xbox;
import com.punchbrain.awake.model.Player;
import com.punchbrain.awake.screens.LevelScreen;

public class LevelControllerInputAdapter extends ControllerAdapter {

   private final LevelScreen introScreen;
   private final Player player;

   public LevelControllerInputAdapter(LevelScreen introScreen, Player player) {
      this.introScreen = introScreen;
      this.player = player;
   }

   @Override
   public boolean buttonDown(Controller controller, int buttonIndex) {
      if (buttonIndex == getEscapeButton(controller)) {
         Gdx.app.exit();
         return true;
      }
      if (buttonIndex == getJumpButton(controller)) {
         player.jump();
         return true;
      }
      return super.buttonDown(controller, buttonIndex);
   }

   @Override
   public boolean axisMoved(Controller controller, int axisIndex, float value) {
      if (axisIndex == Xbox.L_STICK_HORIZONTAL_AXIS || axisIndex == Xbox.R_STICK_HORIZONTAL_AXIS) {
         if (value < -0.2f) {
            player.moveLeft();
         }
         if (value > 0.2f) {
            player.moveRight();
         }
         if (value < 0.2 && value > -0.2) {
            player.stop();
         }
      }
      return super.axisMoved(controller, axisIndex, value);
   }

   @Override
   public boolean buttonUp(Controller controller, int buttonIndex) {
      return super.buttonUp(controller, buttonIndex);
   }

   private int getEscapeButton(Controller controller) {
      if (Xbox.isXboxController(controller)) {
         return Xbox.START;
      }
      return -1;
   }

   private int getJumpButton(Controller controller) {
      if (Xbox.isXboxController(controller)) {
         return Xbox.A;
      }
      return -1;
   }
}
