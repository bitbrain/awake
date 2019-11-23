package com.punchbrain.awake.tmx;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.punchbrain.awake.model.Player;
import de.bitbrain.braingdx.context.GameContext2D;
import de.bitbrain.braingdx.event.GameEventListener;
import de.bitbrain.braingdx.tmx.TiledMapEvents;
import de.bitbrain.braingdx.world.GameObject;

import static com.punchbrain.awake.GameObjectType.COLLISION;

public class CollisionInitialiser implements GameEventListener<TiledMapEvents.OnLoadGameObjectEvent> {

   private final GameContext2D context;
   private Player player;

   public CollisionInitialiser(GameContext2D context) {
      this.context = context;
   }

   @Override
   public void onEvent(TiledMapEvents.OnLoadGameObjectEvent event) {
      GameObject object = event.getObject();
      if (COLLISION.isTypeOf(object)) {
         //bodydef
         BodyDef bodyDef = new BodyDef();
         bodyDef.type = BodyDef.BodyType.StaticBody;
         bodyDef.position.set(object.getLeft() + object.getWidth() / 2f, object.getTop() + object.getHeight() / 2f);
         Body body = context.getBox2DWorld().createBody(bodyDef);

         //shape
         PolygonShape shape = new PolygonShape();
         shape.setAsBox(object.getWidth() / 2f, object.getHeight() / 2f);

         //fixture
         FixtureDef fixture = new FixtureDef();
         fixture.friction = 0f;
         fixture.shape = shape;

         body.createFixture(fixture);
         shape.dispose();
      }
   }
}
