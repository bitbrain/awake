package com.punchbrain.awake.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.punchbrain.awake.model.Player;
import com.punchbrain.awake.screens.LevelScreen;
import de.bitbrain.braingdx.util.Updateable;

import static com.badlogic.gdx.Input.Keys.*;

public class LevelKeyboardInputAdapter extends InputAdapter implements Updateable {

   private final LevelScreen screen;
   private final Player player;

   public LevelKeyboardInputAdapter(LevelScreen screen, Player player) {
      this.screen = screen;
      this.player = player;
   }

   @Override
   public boolean keyDown(int keycode) {
      if (keycode == Input.Keys.ESCAPE) {
         Gdx.app.exit();
         return true;
      }
      return false;
   }

   @Override
   public void update(float delta) {
      if (areKeysPressed(A, LEFT)) {
         player.moveLeft();
      } else if (areKeysPressed(D, RIGHT)) {
         player.moveRight();
      } else {
         player.stop();
      }
      if (areKeysPressed(SPACE)) {
         player.jump();
      }
   }

   private boolean areKeysPressed(int ... keys) {
      for (int key : keys) {
         if (!Gdx.input.isKeyPressed(key)) {
            continue;
         }
         return true;
      }
      return false;
   }
}
