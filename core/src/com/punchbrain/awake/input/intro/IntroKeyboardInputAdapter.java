package com.punchbrain.awake.input.intro;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.punchbrain.awake.model.Player;
import com.punchbrain.awake.screens.IntroScreen;

public class IntroKeyboardInputAdapter extends InputAdapter {

   private final IntroScreen screen;
   private final Player player;

   public IntroKeyboardInputAdapter(IntroScreen screen, Player player) {
      this.screen = screen;
      this.player = player;
   }

   @Override
   public boolean keyDown(int keycode) {
      if (keycode == Input.Keys.ESCAPE) {
         Gdx.app.exit();
         return true;
      }
      if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
         player.moveLeft();
      }
      if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
         player.moveRight();
      }
      if (keycode == Input.Keys.SPACE) {
         player.jump();
      }
      return true;
   }
}
