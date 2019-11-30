package com.punchbrain.awake.screens;

import static com.punchbrain.awake.GameObjectType.PLAYER;
import static com.punchbrain.awake.util.TmxUtil.loadTiledMap;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.punchbrain.awake.AwakeGame;
import com.punchbrain.awake.Colors;
import com.punchbrain.awake.animation.AnimationConfigFactory;
import com.punchbrain.awake.animation.PlayerDirection;
import com.punchbrain.awake.assets.Assets;
import com.punchbrain.awake.bootstrap.BootstrapFactory;
import com.punchbrain.awake.bootstrap.LevelBootstrap;
import com.punchbrain.awake.bootstrap.LevelStageBootstrap;
import com.punchbrain.awake.event.AwakeEventFactory;
import com.punchbrain.awake.event.GameOverEvent;
import com.punchbrain.awake.event.GameOverEventListener;
import com.punchbrain.awake.event.TeleportEvent;
import com.punchbrain.awake.event.TeleportEventListener;
import com.punchbrain.awake.input.LevelControllerInputAdapter;
import com.punchbrain.awake.input.LevelKeyboardInputAdapter;
import com.punchbrain.awake.tmx.PlayerInitialiser;
import de.bitbrain.braingdx.assets.SharedAssetManager;
import de.bitbrain.braingdx.context.GameContext2D;
import de.bitbrain.braingdx.graphics.animation.AnimationRenderer;
import de.bitbrain.braingdx.graphics.animation.AnimationSpriteSheet;
import de.bitbrain.braingdx.graphics.animation.AnimationTypeResolver;
import de.bitbrain.braingdx.graphics.lighting.LightingConfig;
import de.bitbrain.braingdx.graphics.pipeline.layers.RenderPipeIds;
import de.bitbrain.braingdx.input.InputManager;
import de.bitbrain.braingdx.screen.BrainGdxScreen2D;
import de.bitbrain.braingdx.tmx.TiledMapContext;
import de.bitbrain.braingdx.tmx.TiledMapEvents.OnLoadGameObjectEvent;
import de.bitbrain.braingdx.world.GameObject;

public class LevelScreen extends BrainGdxScreen2D<AwakeGame> {

   private PlayerInitialiser playerInitialiser;
   private final String targetSpawnId;
   private final String tiledMapFile;
   private GameContext2D context;

   public LevelScreen(AwakeGame game, String tiledMapFile, String targetSpawnId) {
      super(game);
      this.targetSpawnId = targetSpawnId;
      this.tiledMapFile = tiledMapFile;
   }

   public LevelScreen(AwakeGame game, String tiledMapFile) {
      super(game);
      this.targetSpawnId = null;
      this.tiledMapFile = tiledMapFile;
   }

   public void reset() {
      context.getScreenTransitions().out(new LevelScreen(getGame(), tiledMapFile, targetSpawnId), 0.3f);
   }


   @Override
   protected void onCreate(GameContext2D context) {
      this.context = context;
      context.getScreenTransitions().in(0.5f);
      context.setBackgroundColor(Colors.BACKGROUND);
      setupGraphics(context);
      setupEvents(context);
      TiledMapContext tmxContext = setupTiled(context);
      setupPhysics(context);
      setupInput(context.getInputManager());

      bootstrap(context, tmxContext);
   }

   private TiledMapContext setupTiled(GameContext2D context) {
      TiledMapContext tmxContext = loadTiledMap(tiledMapFile, context);
      tmxContext.setEventFactory(new AwakeEventFactory());
      return tmxContext;
   }

   private void setupEvents(GameContext2D context) {
      this.playerInitialiser = new PlayerInitialiser(context, targetSpawnId);
      context.getEventManager().register(playerInitialiser, OnLoadGameObjectEvent.class);
      context.getEventManager().register(new TeleportEventListener(context, this), TeleportEvent.class);
      context.getEventManager().register(new GameOverEventListener(context, this), GameOverEvent.class);
   }

   private void setupInput(InputManager inputManager) {
      inputManager.register(new LevelControllerInputAdapter(this));
      inputManager.register(new LevelKeyboardInputAdapter(this, playerInitialiser.getPlayer()));
   }

   private void setupGraphics(GameContext2D context) {
       Texture playerTexture = SharedAssetManager.getInstance().get(Assets.Textures.PLAYER_TILESET);
       AnimationSpriteSheet playerSheet = new AnimationSpriteSheet(playerTexture, 26, 52);
       context.getRenderManager().register(PLAYER.name(), new AnimationRenderer(playerSheet, AnimationConfigFactory.playerAnimationConfig,
                                                                                new AnimationTypeResolver<GameObject>() {
                                                                                    @Override
                                                                                    public Object getAnimationType(GameObject object) {
                                                                                        return object.getAttribute(PlayerDirection.class);
                                                                                    }
                                                                                }));
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

   private void bootstrap(final GameContext2D context, final TiledMapContext tmxContext) {
      for (LevelBootstrap bootstrap : BootstrapFactory.getBoostraps()) {
         if (bootstrap.isApplicable(tiledMapFile)) {
            bootstrap.boostrap(context, tmxContext);
         }
      }
   }
}
