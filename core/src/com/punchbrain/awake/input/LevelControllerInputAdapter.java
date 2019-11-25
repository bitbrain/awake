package com.punchbrain.awake.input;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.punchbrain.awake.screens.LevelScreen;

public class LevelControllerInputAdapter extends ControllerAdapter {

   private final LevelScreen introScreen;

   public LevelControllerInputAdapter(LevelScreen introScreen) {
      this.introScreen = introScreen;
   }

   @Override
   public boolean buttonDown(Controller controller, int buttonIndex) {
      // TODO
      return super.buttonDown(controller, buttonIndex);
   }

   @Override
   public boolean buttonUp(Controller controller, int buttonIndex) {
      return super.buttonUp(controller, buttonIndex);
   }
}
