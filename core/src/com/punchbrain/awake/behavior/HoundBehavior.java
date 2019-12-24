package com.punchbrain.awake.behavior;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static com.punchbrain.awake.GameObjectType.CIRCUIT_LAMP;

import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.punchbrain.awake.event.GameOverEvent;
import com.punchbrain.awake.model.Circuit;
import com.punchbrain.awake.model.map.CircuitModelMap;
import de.bitbrain.braingdx.behavior.BehaviorAdapter;
import de.bitbrain.braingdx.event.GameEventManager;
import de.bitbrain.braingdx.graphics.lighting.LightingManager;
import de.bitbrain.braingdx.util.Colors;
import de.bitbrain.braingdx.util.DeltaTimer;
import de.bitbrain.braingdx.world.GameObject;

public class HoundBehavior extends BehaviorAdapter {

    private static final float SNAPSHOT_INTERVAL = 10f;
    private static final float MAX_SNAPSHOT_SIZE = 3;

    private final GameObject player;
    private final DeltaTimer timer = new DeltaTimer();

    private Vector2 target;
    private final Queue<Vector2> snapshots = new LinkedList<Vector2>();
    private final GameEventManager eventManager;
    private final LightingManager lightingManager;
    private final CircuitModelMap circuitModelMap;
    private boolean inLight;

    public HoundBehavior(GameObject player, GameEventManager eventManager, LightingManager lightingManager, CircuitModelMap circuitModelMap) {
        this.player = player;
        this.eventManager = eventManager;
        this.lightingManager = lightingManager;
        this.circuitModelMap = circuitModelMap;
    }

    @Override
    public void update(final GameObject hound, final float delta) {

        // TODO: inject a lookup map to find circuit by game object
        // TODO: introduce object to model stereotype Map<GameObject, Model> which can be added to the context
        // Map<Model.class,Map<GameObject, Model>>
        // Or, class ModelMap<T, >

        // TODO: Add a radial resolver
        // TODO: difference in space, plus radius
        // TODO: util method: radialColision(GameObject, radius1, GameObject, radius2)

        // TODO: talk about making this more of a controller class, with resolvers on the models
        if(inLight){
            // this bool needs to be updated before it's always returned so that it must be checked every time.
            inLight = false;
            return;
        }
        timer.update(delta);
        if (timer.reached(SNAPSHOT_INTERVAL)) {
            timer.reset();
            snapshots.add(player.getPosition().cpy());
            if (snapshots.size() > MAX_SNAPSHOT_SIZE) {
                target = snapshots.poll();
            }
        }
        if (readyToPoll(hound) && snapshots.size() > 0) {
            if (target == null) {
                target = snapshots.poll().cpy();
            } else {
                target = snapshots.poll().cpy();
            }
        }

        if (target != null) {
            Vector2 movement = target.cpy().sub(hound.getPosition()).scl(0.7f * (snapshots.size() + 1f));
            hound.move(movement.x * delta, movement.y * delta);
        }

        // check if hound got the boy
        if (player.collidesWith(hound)) {
            eventManager.publish(new GameOverEvent());
        }

        // update ambient light depending on distance
        float distance = player.getPosition().cpy().sub(hound.getPosition()).len();
        float ratio = clamp(750f / distance, 0.6f, 2f);
        lightingManager.setAmbientLight(Colors.darken(com.punchbrain.awake.Colors.BACKGROUND, ratio));
    }

    @Override
    public void update(GameObject hound, GameObject object, float delta){
        if(CIRCUIT_LAMP.isTypeOf(object)){
            Circuit circuit = circuitModelMap.getFromLamp(object);
            if(circuit.getState() == Circuit.State.ON){
                double xSep = hound.getLeft() - object.getLeft();
                double ySep = hound.getTop() -  object.getTop();
                double rSep = Math.pow(Math.pow(xSep, 2) + Math.pow(ySep, 2), 0.5);
                if(rSep < circuit.getLightRadius()){
                    System.out.println("Contact!");
                    inLight = true;
                    System.out.println(inLight);
                    return;
                }
                System.out.println(inLight);
            }
        }
    }

    private boolean readyToPoll(GameObject hound) {
        return target == null || target.cpy().sub(hound.getPosition()).len() < 396f;
    }
}
