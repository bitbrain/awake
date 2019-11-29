package com.punchbrain.awake.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import de.bitbrain.braingdx.graphics.animation.AnimationConfig;
import de.bitbrain.braingdx.graphics.animation.AnimationFrames;
import de.bitbrain.braingdx.graphics.animation.AnimationRenderer;
import de.bitbrain.braingdx.ui.AnimationDrawable;

public interface AnimationConfigFactory {
    public static AnimationConfig playerAnimationConfig = AnimationConfig.builder()
            .registerFrames(PlayerDirection.RUNNING_RIGHT, AnimationFrames.builder()
                     .frames(4)
                     .origin(0, 1)
                     .duration(0.2f)
                     .direction(AnimationFrames.Direction.HORIZONTAL)
                     .playMode(Animation.PlayMode.LOOP)
                     .build())
            .registerFrames(PlayerDirection.RUNNING_LEFT, AnimationFrames.builder()
                    .frames(4)
                    .origin(0, 2)
                    .duration(0.2f)
                    .direction(AnimationFrames.Direction.HORIZONTAL)
                    .playMode(Animation.PlayMode.LOOP)
                    .build())
            .registerFrames(PlayerDirection.JUMPING_RIGHT, AnimationFrames.builder()
                    .frames(2)
                    .origin(1, 5)
                    .duration(0.2f)
                    .direction(AnimationFrames.Direction.HORIZONTAL)
                    .playMode(Animation.PlayMode.LOOP)
                    .build())
            .registerFrames(PlayerDirection.JUMPING_LEFT, AnimationFrames.builder()
                    .frames(2)
                    .origin(1, 6)
                    .duration(0.2f)
                    .direction(AnimationFrames.Direction.HORIZONTAL)
                    .playMode(Animation.PlayMode.LOOP)
                    .build())
            .registerFrames(PlayerDirection.FALLING_RIGHT, AnimationFrames.builder()
                    .frames(2)
                    .origin(0, 7)
                    .duration(0.2f)
                    .direction(AnimationFrames.Direction.HORIZONTAL)
                    .playMode(Animation.PlayMode.LOOP)
                    .build())
            .registerFrames(PlayerDirection.FALLING_LEFT, AnimationFrames.builder()
                    .frames(2)
                    .origin(0, 8)
                    .duration(0.2f)
                    .direction(AnimationFrames.Direction.HORIZONTAL)
                    .playMode(Animation.PlayMode.LOOP)
                    .build())
            .registerFrames(PlayerDirection.STANDING_LEFT, AnimationFrames.builder()
                    .frames(1)
                    .origin(0, 0)
                    .duration(0.2f)
                    .direction(AnimationFrames.Direction.HORIZONTAL)
                    .playMode(Animation.PlayMode.LOOP)
                    .build()).build();

}

