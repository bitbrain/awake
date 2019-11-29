package com.punchbrain.awake.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import de.bitbrain.braingdx.graphics.animation.AnimationConfig;
import de.bitbrain.braingdx.graphics.animation.AnimationFrames;
import de.bitbrain.braingdx.graphics.animation.AnimationRenderer;
import de.bitbrain.braingdx.ui.AnimationDrawable;

public interface AnimationConfigFactory {
    public static AnimationConfig playerAnimationConfig = AnimationConfig.builder()
            .registerFrames(PlayerDirection.LEFT, AnimationFrames.builder()
                // the number of frames
                     .frames(4)
                // 4th tile from the left (3=index)
                     .origin(0, 1)
                // change frame every 200m
                     .duration(0.2f)
                // Animate directional
                     .direction(AnimationFrames.Direction.HORIZONTAL)
                // Animate to the end and then backwards and repeat
                     .playMode(Animation.PlayMode.LOOP)
                     .build()).build();

}

