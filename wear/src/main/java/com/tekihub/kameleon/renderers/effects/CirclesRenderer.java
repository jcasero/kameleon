package com.tekihub.kameleon.renderers.effects;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.tekihub.kameleon.renderers.Renderer;

import java.util.Random;

/**
 * Created by Jose on 10/4/16.
 */
public class CirclesRenderer implements Renderer {
    private static final float  DEFAULT_STROKE = 4f;
    private              int    width          = 0;
    private              int    height         = 0;
    private              int    radius         = 0;
    private              int    color          = 0;
    private              int    velocity       = 0;
    private              int    x              = 0;
    private              int    y              = 0;
    private              Paint  paint          = new Paint();

    private Random random        = new Random();
    private int    currentRadius = 0;

    private CirclesRenderer(Builder builder) {
        this.width = builder.width;
        this.height = builder.height;
        this.radius = builder.radius;
        this.velocity = builder.velocity;
        this.color = builder.color;
        init();
    }

    private void init() {
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(DEFAULT_STROKE);
        paint.setAntiAlias(true);

        x = random.nextInt(width);
        y = random.nextInt(height);
    }


    @Override public void render(Canvas canvas) {
        canvas.drawCircle(x, y, currentRadius, paint);
        if (!isFinished()) {
            currentRadius += velocity;
        }
    }

    @Override public boolean isFinished() {
        return currentRadius >= radius;
    }

    @Override public void clear() {
        currentRadius = 0;
    }

    public static class Builder {
        private int width    = 0;
        private int height   = 0;
        private int radius   = 0;
        private int color    = 0;
        private int velocity = 0;

        public Builder(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public Builder setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder setVelocity(int velocity) {
            this.velocity = velocity;
            return this;
        }

        public Builder setColor(int color) {
            this.color = color;
            return this;
        }

        public CirclesRenderer build() {
            return new CirclesRenderer(this);
        }
    }
}
