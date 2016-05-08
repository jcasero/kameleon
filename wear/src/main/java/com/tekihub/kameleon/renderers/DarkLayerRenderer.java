package com.tekihub.kameleon.renderers;

import android.graphics.Canvas;
import android.graphics.Color;

public class DarkLayerRenderer implements Renderer {
    private static final int DEFAULT_SHADOW_COLOR = Color.parseColor("#20000000");

    public DarkLayerRenderer() {
    }

    public void render(Canvas canvas) {
        canvas.drawColor(DEFAULT_SHADOW_COLOR);
    }

    @Override public boolean isFinished() {
        return true;
    }

    @Override public void clear() {

    }
}
