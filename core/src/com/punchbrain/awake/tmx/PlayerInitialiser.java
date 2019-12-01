package com.punchbrain.awake.tmx;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.punchbrain.awake.animation.PlayerDirection;
import com.punchbrain.awake.assets.Assets;
import com.punchbrain.awake.behavior.PlayerUpdateBehavior;
import com.punchbrain.awake.model.Player;
import de.bitbrain.braingdx.context.GameContext2D;
import de.bitbrain.braingdx.event.GameEventListener;
import de.bitbrain.braingdx.tmx.TiledMapEvents;
import de.bitbrain.braingdx.tweens.SharedTweenManager;
import de.bitbrain.braingdx.world.GameObject;

import static com.punchbrain.awake.GameObjectType.PLAYER;
import static com.punchbrain.awake.GameObjectType.TELEPORT;

public class PlayerInitialiser implements GameEventListener<TiledMapEvents.OnLoadGameObjectEvent> {

   private final GameContext2D context;
   private Player player;
   private final String targetTeleportId;

   private GameObject targetTeleport;
   private GameObject playerObject;

   public PlayerInitialiser(GameContext2D context, String targetTeleportId) {
      this.context = context;
      this.targetTeleportId = targetTeleportId;
   }

   public Player getPlayer() {
      return player;
   }

   @Override
   public void onEvent(TiledMapEvents.OnLoadGameObjectEvent event) {
      final GameObject object = event.getObject();
      if (PLAYER.isTypeOf(object)) {
         context.getGameCamera().setTrackingTarget(object);
         context.getGameCamera().setTargetTrackingSpeed(0.02f);
         context.getGameCamera().setDefaultZoomFactor(0.2f);
         object.setDimensions(26, 52);

         Body body = context.getPhysicsManager().attachBody(createBodyDef(object), createBodyFixtureDef(), object);
         this.player = new Player(object, body);
         this.playerObject = object;
         playerObject.setAttribute(PlayerDirection.class, PlayerDirection.STANDING_LEFT);

         PlayerUpdateBehavior behavior = new PlayerUpdateBehavior(player);
         context.getBehaviorManager().apply(behavior, playerObject);
         if (targetTeleport != null) {
            teleportPlayer(playerObject, targetTeleport);
         }
      } else if (isTargetTeleport(object)) {
         this.targetTeleport = object;
         if (player != null) {
            teleportPlayer(playerObject, targetTeleport);
         }
      }
   }

   private boolean isTargetTeleport(GameObject object) {
      return targetTeleportId != null && TELEPORT.isTypeOf(object) && object.getAttribute("id", String.class).equals(targetTeleportId);
   }

   private void teleportPlayer(final GameObject playerObject, final GameObject teleportObject) {
      Tween.call(new TweenCallback() {
         @Override
         public void onEvent(int type, BaseTween<?> source) {
            context.getAudioManager().spawnSound(Assets.Sounds.DOOR_CLOSE, teleportObject.getLeft(), teleportObject.getTop(), (float) (0.8f + Math.random() * 0.3f), 1f, 358f);
         }
      }).delay(0.2f).start(SharedTweenManager.getInstance());
      Vector2 pos = getTargetTeleportPosition(teleportObject, playerObject);
      player.setPosition(pos.x, pos.y);
      context.getGameCamera().focusCentered(playerObject);
   }

   private Vector2 getTargetTeleportPosition(GameObject teleport, GameObject target) {
      String id = teleport.getAttribute("id", String.class);
      if (id.contains("left")) {
         return teleport.getPosition().add(target.getWidth(), 0);
      } else if (id.contains("right")) {
         return teleport.getPosition().sub(target.getWidth(), 0);
      }
      return teleport.getPosition().add(target.getWidth() / 2f - target.getWidth() / 2f, 0);
   }

   private BodyDef createBodyDef(GameObject object) {
      BodyDef playerBodyDef = new BodyDef();
      playerBodyDef.type = BodyDef.BodyType.DynamicBody;
      playerBodyDef.position.set(object.getLeft() + object.getWidth() / 2f, object.getTop() + object.getHeight() / 2f);
      playerBodyDef.fixedRotation = true;
      playerBodyDef.gravityScale = 2f;
      return playerBodyDef;
   }

   private FixtureDef createBodyFixtureDef() {
      FixtureDef bodyFixtureDef = new FixtureDef();
      PolygonShape playerBody = new PolygonShape();
      playerBody.set(new float[]{
            -8f, 2f,
            0f, 4f,
            8f, 2f,
            -8f, -32f,
            8f, -32f
      });
      bodyFixtureDef.shape = playerBody;
      bodyFixtureDef.density = 0.00001f;
      bodyFixtureDef.friction = 0f;
      bodyFixtureDef.restitution = 0f;
      return bodyFixtureDef;
   }
}

