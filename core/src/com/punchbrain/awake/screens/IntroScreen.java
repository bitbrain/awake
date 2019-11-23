package com.punchbrain.awake.screens;

import com.punchbrain.awake.AwakeGame;
import com.punchbrain.awake.Colors;
import com.punchbrain.awake.assets.Assets;
import com.punchbrain.awake.input.intro.IntroControllerInputAdapter;
import com.punchbrain.awake.input.intro.IntroKeyboardInputAdapter;
import com.punchbrain.awake.tmx.CollisionInitialiser;
import com.punchbrain.awake.tmx.PlayerInitialiser;
import de.bitbrain.braingdx.context.GameContext2D;
import de.bitbrain.braingdx.graphics.lighting.LightingConfig;
import de.bitbrain.braingdx.graphics.pipeline.layers.RenderPipeIds;
import de.bitbrain.braingdx.graphics.renderer.SpriteRenderer;
import de.bitbrain.braingdx.input.InputManager;
import de.bitbrain.braingdx.screen.BrainGdxScreen2D;
import de.bitbrain.braingdx.tmx.TiledMapEvents.OnLoadGameObjectEvent;

import static com.punchbrain.awake.GameObjectType.PLAYER;
import static com.punchbrain.awake.util.TmxUtil.loadTiledMap;

public class IntroScreen extends BrainGdxScreen2D<AwakeGame> {

   private PlayerInitialiser playerInitialiser;

   public IntroScreen(AwakeGame game) {
      super(game);
   }

   @Override
   protected void onCreate(GameContext2D context) {
      context.setBackgroundColor(Colors.BACKGROUND);

      setupEvents(context);

      loadTiledMap(Assets.TiledMaps.BOYS_ROOM, context);

      setupGraphics(context);
      setupPhysics(context);
      setupInput(context.getInputManager());
   }

   private void setupEvents(GameContext2D context) {
      context.getEventManager().register(new CollisionInitialiser(context), OnLoadGameObjectEvent.class);
      this.playerInitialiser = new PlayerInitialiser(context);
      context.getEventManager().register(playerInitialiser, OnLoadGameObjectEvent.class);
   }

   private void setupInput(InputManager inputManager) {
      inputManager.register(new IntroControllerInputAdapter(this));
      inputManager.register(new IntroKeyboardInputAdapter(this, playerInitialiser.getPlayer()));
   }

   private void setupGraphics(GameContext2D context) {
      context.getRenderManager().register(PLAYER.name(), new SpriteRenderer(Assets.Textures.PLAYER));
      LightingConfig lightingConfig = new LightingConfig();
      lightingConfig.rays(200);
      lightingConfig.blur(false);
      context.getLightingManager().setConfig(lightingConfig);
      context.getLightingManager().setAmbientLight(Colors.BACKGROUND);

      context.getRenderPipeline().addEffects(RenderPipeIds.WORLD_UI, context.getShaderManager().createBloomEffect());

   }

   private void setupPhysics(GameContext2D context) {
      context.getPhysicsManager().setGravity(0f, -98);
   }
}
