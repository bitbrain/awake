package com.punchbrain.awake;

import com.punchbrain.awake.assets.Assets;
import com.punchbrain.awake.screens.IntroScreen;
import de.bitbrain.braingdx.BrainGdxGame;
import de.bitbrain.braingdx.assets.GameAssetLoader;
import de.bitbrain.braingdx.assets.SmartAssetLoader;
import de.bitbrain.braingdx.screens.AbstractScreen;

public class AwakeGame extends BrainGdxGame {

	@Override
	protected GameAssetLoader getAssetLoader() {
		return new SmartAssetLoader(Assets.class);
	}

	@Override
	protected AbstractScreen<?> getInitialScreen() {
		return new IntroScreen(this);
	}
}
