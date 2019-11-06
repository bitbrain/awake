package com.punchbrain.awake.screens;

import com.punchbrain.awake.AwakeGame;
import com.punchbrain.awake.Colors;
import com.punchbrain.awake.input.intro.IntroControllerInputAdapter;
import com.punchbrain.awake.input.intro.IntroKeyboardInputAdapter;
import de.bitbrain.braingdx.GameContext;
import de.bitbrain.braingdx.graphics.GameObjectRenderManager;
import de.bitbrain.braingdx.input.InputManager;
import de.bitbrain.braingdx.physics.PhysicsManager;
import de.bitbrain.braingdx.screens.AbstractScreen;

public class IntroScreen extends AbstractScreen<AwakeGame> {

   public IntroScreen(AwakeGame game) {
      super(game);
   }

   @Override
   protected void onCreate(GameContext context) {
      setBackgroundColor(Colors.BACKGROUND);

      setupInput(context.getInputManager());
      setupGraphics(context.getRenderManager());
      setupPhysics(context.getPhysicsManager());
      setupWorld(context);
   }

   private void setupInput(InputManager inputManager) {
      inputManager.register(new IntroControllerInputAdapter(this));
      inputManager.register(new IntroKeyboardInputAdapter(this));
   }

   private void setupGraphics(GameObjectRenderManager renderManager) {
      // TODO
   }

   private void setupPhysics(PhysicsManager physicsManager) {
      // TODO
   }

   private void setupWorld(GameContext context) {
      // TODO
   }
}
