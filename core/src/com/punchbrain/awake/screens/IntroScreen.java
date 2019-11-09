package com.punchbrain.awake.screens;

import static com.punchbrain.awake.Colors.AMBIENT_LIGHT;
import static com.punchbrain.awake.Colors.TORCH_LIGHT;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.punchbrain.awake.AwakeGame;
import com.punchbrain.awake.Colors;
import com.punchbrain.awake.assets.Assets;
import com.punchbrain.awake.input.intro.IntroControllerInputAdapter;
import com.punchbrain.awake.input.intro.IntroKeyboardInputAdapter;
import com.punchbrain.awake.logic.AutoMovementBehavior;
import de.bitbrain.braingdx.GameContext;
import de.bitbrain.braingdx.assets.SharedAssetManager;
import de.bitbrain.braingdx.graphics.GameObjectRenderManager;
import de.bitbrain.braingdx.graphics.lighting.LightingManager;
import de.bitbrain.braingdx.graphics.lighting.PointLightBehavior;
import de.bitbrain.braingdx.graphics.pipeline.layers.RenderPipeIds;
import de.bitbrain.braingdx.graphics.postprocessing.AutoReloadPostProcessorEffect;
import de.bitbrain.braingdx.graphics.postprocessing.effects.Vignette;
import de.bitbrain.braingdx.graphics.renderer.SpriteRenderer;
import de.bitbrain.braingdx.input.InputManager;
import de.bitbrain.braingdx.physics.PhysicsManager;
import de.bitbrain.braingdx.screens.AbstractScreen;
import de.bitbrain.braingdx.tmx.TiledMapType;
import de.bitbrain.braingdx.world.GameObject;

public class IntroScreen extends AbstractScreen<AwakeGame> {

   public IntroScreen(AwakeGame game) {
      super(game);
   }

   @Override
   protected void onCreate(GameContext context) {
      setBackgroundColor(Colors.BACKGROUND);

      setupInput(context.getInputManager());
      setupGraphics(context);
      setupPhysics(context.getPhysicsManager());
      setupWorld(context);
   }

   private void setupInput(InputManager inputManager) {
      inputManager.register(new IntroControllerInputAdapter(this));
      inputManager.register(new IntroKeyboardInputAdapter(this));
   }

   private void setupGraphics(GameContext context) {
      LightingManager.LightingConfig config = new LightingManager.LightingConfig();
      config.rays(200);
      context.getLightingManager().setConfig(config);


      context.getRenderManager().register("Luca", new SpriteRenderer(Assets.Textures.PLAYER_TEXTURE));
      context.getLightingManager().setAmbientLight(AMBIENT_LIGHT);

      AutoReloadPostProcessorEffect<Vignette> vignette = context.getShaderManager().createVignetteEffect();
      context.getRenderPipeline().addEffects(RenderPipeIds.PARTICLES, vignette);
   }

   private void setupPhysics(PhysicsManager physicsManager) {
      // First we create a body definition
      BodyDef bodyDef = new BodyDef();
      // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
      bodyDef.type = BodyDef.BodyType.DynamicBody;
      // Set our body's starting position in the world
      bodyDef.position.set(64, 32);
      // Create our body in the world using our body definition
      physicsManager.addBody(bodyDef, 32, 32, "asdf");
   }

   private void setupWorld(GameContext context) {
      GameObject object = context.getGameWorld().addObject();
      object.setType("Luca");
      object.setDimensions(32, 32);
      object.setZIndex(999);
      context.getBehaviorManager().apply(new AutoMovementBehavior(), object);

      context.getGameCamera().setZoomScalingFactor(1f);
      context.getGameCamera().setTrackingTarget(object, true);

      context.getBehaviorManager().apply(new PointLightBehavior(TORCH_LIGHT, 500, context.getLightingManager()), object);


      TiledMap map = SharedAssetManager.getInstance().get(Assets.TiledMaps.TEST, TiledMap.class);
      context.getTiledMapManager().load(map, context.getGameCamera().getInternalCamera(), TiledMapType.ORTHOGONAL);


   }
}
