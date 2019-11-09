package com.punchbrain.awake.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.punchbrain.awake.AwakeGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.forceExit = false;
		config.width = 800;
		config.height = 600;
		config.resizable = false;
		config.title = "project: awake";
		new LwjglApplication(new AwakeGame(), config);
	}
}
