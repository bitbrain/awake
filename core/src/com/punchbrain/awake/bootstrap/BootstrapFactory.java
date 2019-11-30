package com.punchbrain.awake.bootstrap;

public class BootstrapFactory {

    public static LevelBootstrap[] getBoostraps() {
        return new LevelBootstrap[] {new LevelStageBootstrap()};
    }
}
