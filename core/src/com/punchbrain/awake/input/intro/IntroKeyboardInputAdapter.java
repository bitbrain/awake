package com.punchbrain.awake.input.intro;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.punchbrain.awake.screens.IntroScreen;

public class IntroKeyboardInputAdapter extends InputAdapter {

   private final IntroScreen screen;

   public IntroKeyboardInputAdapter(IntroScreen screen) {
      this.screen = screen;
   }

   @Override
   public boolean keyDown(int keycode) {
      if (keycode == Input.Keys.ESCAPE) {
         Gdx.app.exit();
         return true;
      }
      return true;
   }
}
