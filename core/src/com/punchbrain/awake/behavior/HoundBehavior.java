package com.punchbrain.awake.behavior;

import static com.badlogic.gdx.math.MathUtils.clamp;

import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.punchbrain.awake.event.GameOverEvent;
import de.bitbrain.braingdx.behavior.BehaviorAdapter;
import de.bitbrain.braingdx.event.GameEventManager;
import de.bitbrain.braingdx.graphics.lighting.LightingManager;
import de.bitbrain.braingdx.util.Colors;
import de.bitbrain.braingdx.util.DeltaTimer;
import de.bitbrain.braingdx.world.GameObject;

public class HoundBehavior extends BehaviorAdapter {

    private static final float SNAPSHOT_INTERVAL = 5f;
    private static final float MAX_SNAPSHOT_SIZE = 5;

    private final GameObject player;
    private final DeltaTimer timer = new DeltaTimer();

    private Vector2 target;
    private final Queue<Vector2> snapshots = new LinkedList<Vector2>();
    private final GameEventManager eventManager;
    private final LightingManager lightingManager;

    public HoundBehavior(GameObject player, GameEventManager eventManager, LightingManager lightingManager) {
        this.player = player;
        this.eventManager = eventManager;
        this.lightingManager = lightingManager;
    }

    @Override
    public void update(final GameObject hound, final float delta) {
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
            Vector2 movement = target.cpy().sub(hound.getPosition()).scl(0.2f);
            hound.move(movement.x * delta, movement.y * delta);
        }

        // check if hound got the boy
        if (player.collidesWith(hound)) {
            eventManager.publish(new GameOverEvent());
        }

        // update ambient light depending on distance
        float distance = player.getPosition().cpy().sub(hound.getPosition()).len();
        float ratio = clamp(250f / distance, 0.7f, 2f);
        lightingManager.setAmbientLight(Colors.darken(com.punchbrain.awake.Colors.BACKGROUND, ratio));
    }

    private boolean readyToPoll(GameObject hound) {
        return target == null || target.cpy().sub(hound.getPosition()).len() < 64f;
    }
}
