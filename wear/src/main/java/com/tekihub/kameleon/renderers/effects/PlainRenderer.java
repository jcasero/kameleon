package com.tekihub.kameleon.renderers.effects;

import android.graphics.Canvas;
import android.graphics.Color;
import com.tekihub.kameleon.renderers.Renderer;

/**
 * Created by Jose on 12/4/16.
 */
public class PlainRenderer implements Renderer {
    private static final String TAG = "PlainRenderer";
    private int color = Color.WHITE;
    private boolean finished = false;

    public PlainRenderer(int color) {
        this.color = color;
    }

    @Override public void render(Canvas canvas) {
        canvas.drawColor(color);
        finished = true;
    }

    @Override public boolean isFinished() {
        return finished;
    }

    @Override public void clear() {
        color = Color.BLACK;
    }
}
