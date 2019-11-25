package com.punchbrain.awake.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.punchbrain.awake.AwakeGame;

public class DesktopLauncher {
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.forceExit = false;
		config.resizable = false;
		config.title = "project: awake";
		new LwjglApplication(new AwakeGame(args), config);
	}
}
