package com.tekihub.kameleon.renderers;

import android.graphics.Canvas;

public interface Renderer {
    void render(Canvas canvas);
    boolean isFinished();
    void clear();
}
