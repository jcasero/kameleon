package com.tekihub.kameleon.renderers.effects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;

import com.tekihub.kameleon.renderers.Renderer;

/**
 * Created by Jose on 10/4/16.
 */
public class LinesRenderer implements Renderer {
    private int         width       = 0;
    private int         height      = 0;
    private int         amplitude   = 0;
    private int         color       = 0;
    private int         velocity    = 0;
    private float       stroke      = 8f;
    private float       angle       = 0;
    private Paint       paint       = new Paint();
    private PointF      pointStart  = new PointF();
    private PointF      pointEnd    = new PointF();
    private Path        path        = new Path();
    private PathMeasure pathMeasure = new PathMeasure();


    private int     currentVelocity = 0;
    private boolean finished        = false;

    private LinesRenderer(Builder builder) {
        this.width = builder.width;
        this.height = builder.height;
        this.amplitude = builder.amplitude;
        this.stroke = builder.stroke;
        this.velocity = builder.velocity;
        this.color = builder.color;
        this.angle = builder.angle;
        init();
    }

    private void init() {
        double maxLength = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2)) / 2f;
        float centerX = width / 2f;
        float centerY = height / 2f;

        float endX = (float) (centerX + Math.cos(Math.toRadians(angle)) * maxLength);
        float endY = (float) (centerY + Math.sin(Math.toRadians(angle)) * maxLength);

        float startX = (float) (centerX + Math.cos(Math.toRadians(angle - 180f)) * maxLength);
        float startY = (float) (centerY + Math.sin(Math.toRadians(angle - 180f)) * maxLength);

        pointStart.set(startX, startY);
        pointEnd.set(endX, endY);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(stroke);
        paint.setAntiAlias(true);

    }


    @Override public void render(Canvas canvas) {
        finished = renderLine(new PointF(pointStart.x, pointStart.y), new PointF(pointEnd.x, pointEnd.y), 0, 1, canvas, false);
        finished &= renderLine(new PointF(pointStart.x, pointStart.y), new PointF(pointEnd.x, pointEnd.y), 0, -1, canvas, false);
        currentVelocity += velocity;
    }

    @Override public boolean isFinished() {
        return finished;
    }

    @Override public void clear() {
        currentVelocity = 0;
        finished = false;
    }

    private boolean renderLine(PointF pointStart, PointF pointEnd, int iteration, final int direction, Canvas canvas, boolean finished) {
        if (((pointStart.x < 0 && pointEnd.x < 0) || (pointStart.y < 0 && pointEnd.y < 0)) ||
                ((pointStart.x > width && pointEnd.x > width) || (pointStart.y > width && pointEnd.y > width))) {
            return finished;
        }

        float[] pos = new float[2];
        float[] tan = new float[2];

        path.reset();
        path.moveTo(pointStart.x, pointStart.y);
        path.lineTo(pointEnd.x, pointEnd.y);
        path.close();

        int velocityDistance = currentVelocity - (velocity * iteration);
        int rectDimension = (int) Math.sqrt(
                (pointStart.x - pointEnd.x) * (pointStart.x - pointEnd.x) +
                        (pointStart.y - pointEnd.y) * (pointStart.y - pointEnd.y)
        );

        if (velocityDistance < rectDimension) {
            pathMeasure.setPath(path, false);
            pathMeasure.getPosTan(velocityDistance, pos, tan);
            finished = false;
        } else {
            pos[0] = pointEnd.x;
            pos[1] = pointEnd.y;
            finished = true;
        }

        canvas.drawLine(pointStart.x, pointStart.y, pos[0], pos[1], paint);

        if (angle >= 45f && angle <= 135f) {
            pointEnd.offset(amplitude * direction, 0);
            pointStart.offset(amplitude * direction, 0);
        } else {
            pointEnd.offset(0, amplitude * direction);
            pointStart.offset(0, amplitude * direction);
        }

        finished &= renderLine(pointStart, pointEnd, iteration + 1, direction, canvas, finished);

        return finished;
    }

    public static class Builder {
        private int   width     = 0;
        private int   height    = 0;
        private int   amplitude = 0;
        private int   color     = 0;
        private int   velocity  = 0;
        private float angle     = 0;
        private float stroke    = 0;

        public Builder(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public Builder setAmplitude(int amplitude) {
            this.amplitude = amplitude;
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

        public Builder setStroke(float stroke) {
            this.stroke = stroke;
            return this;
        }

        public Builder setAngle(float angle) {
            this.angle = angle;
            return this;
        }

        public LinesRenderer build() {
            return new LinesRenderer(this);
        }
    }
}
