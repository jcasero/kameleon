package com.tekihub.kameleon.renderers.effects;

import android.animation.TimeInterpolator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.animation.LinearInterpolator;

import com.tekihub.kameleon.renderers.Renderer;

import java.util.Random;

/**
 * Created by Jose on 10/4/16.
 */
public class CirclesRenderer implements Renderer {
    private static final float DEFAULT_STROKE = 4f;
    private int width;
    private int height;
    private int radius;
    private int color;
    private int velocity;
    private int x;
    private int y;
    private Paint paint = new Paint();
    private TimeInterpolator interpolator;

    private Random random = new Random();
    private int currentRadius = 0;

    private CirclesRenderer(Builder builder) {
        this.width = builder.width;
        this.height = builder.height;
        this.radius = builder.radius;
        this.velocity = builder.velocity;
        this.color = builder.color;
        this.interpolator = builder.interpolator;
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
        float rad = interpolator.getInterpolation(currentRadius / (float) radius);
        canvas.drawCircle(x, y, rad * radius, paint);
        currentRadius += velocity;
    }

    @Override public boolean isFinished() {
        return currentRadius >= radius;
    }

    @Override public void clear() {
        currentRadius = 0;
    }

    public static class Builder {
        private int width;
        private int height;
        private int radius;
        private int color;
        private int velocity;
        private TimeInterpolator interpolator = new LinearInterpolator();

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

        public Builder setInterpolator(TimeInterpolator interpolator) {
            this.interpolator = interpolator;
            return this;
        }

        public CirclesRenderer build() {
            return new CirclesRenderer(this);
        }
    }
}
