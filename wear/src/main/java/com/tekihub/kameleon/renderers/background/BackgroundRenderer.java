package com.tekihub.kameleon.renderers.background;

import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by Jose on 10/4/16.
 */
public interface BackgroundRenderer {
    void create(ArrayList<Integer> colors);

    void setDimension(int width, int height);

    void render(Canvas canvas);

    boolean isFinished();
}
