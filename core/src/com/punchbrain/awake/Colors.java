package com.punchbrain.awake;

import com.badlogic.gdx.graphics.Color;

import static com.badlogic.gdx.graphics.Color.valueOf;

public interface Colors {

   Color BACKGROUND = valueOf("191325");
   Color FOREGROUND = valueOf("df9f6d");
   Color LANTERN = valueOf("ff9922").sub(0f, 0f, 0f, 0.2f);
}
