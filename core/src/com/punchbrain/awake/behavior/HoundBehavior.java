package com.punchbrain.awake.behavior;

import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import de.bitbrain.braingdx.behavior.BehaviorAdapter;
import de.bitbrain.braingdx.util.DeltaTimer;
import de.bitbrain.braingdx.world.GameObject;

public class HoundBehavior extends BehaviorAdapter {

    private static final float SNAPSHOT_INTERVAL = 5f;
    private static final float MAX_SNAPSHOT_SIZE = 5;

    private final GameObject player;
    private final DeltaTimer timer = new DeltaTimer();

    private Vector2 target;
    private final Queue<Vector2> snapshots = new LinkedList<Vector2>();

    public HoundBehavior(GameObject player) {
        this.player = player;
    }

    @Override
    public void update(final GameObject hound, final float delta) {
        timer.update(delta);
        if (timer.reached(SNAPSHOT_INTERVAL)) {
            timer.reset();
            snapshots.add(player.getPosition().cpy());
            if (target != null) {
                System.out.println(target);
            }
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
            Gdx.app.log("UPDATE", "GAME OVER!!!");
        }
    }

    private boolean readyToPoll(GameObject hound) {
        return target == null || target.cpy().sub(hound.getPosition()).len() < 64f;
    }
}
