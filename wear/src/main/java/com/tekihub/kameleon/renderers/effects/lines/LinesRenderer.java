package com.tekihub.kameleon.renderers.effects.lines;

import android.animation.TimeInterpolator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.animation.LinearInterpolator;

import com.tekihub.kameleon.renderers.Renderer;
import com.tekihub.kameleon.utils.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Jose on 10/4/16.
 */
public class LinesRenderer implements Renderer {
    private int width = 0;
    private int height = 0;
    private int amplitude = 0;
    private int color = 0;
    private int velocity = 0;
    private float stroke = 8f;
    private float angle = 0;
    private Paint paint = new Paint();
    private TimeInterpolator interpolator;
    private ArrayList<Line> lines = new ArrayList<>();
    private List<RectF> rects = new ArrayList<>();


    private boolean finished = false;
    private Line baseline;

    private LinesRenderer(Builder builder) {
        this.width = builder.width;
        this.height = builder.height;
        this.amplitude = builder.amplitude;
        this.stroke = builder.stroke;
        this.velocity = builder.velocity;
        this.color = builder.color;
        this.angle = builder.angle;
        this.interpolator = builder.interpolator;
        init();
    }

    private void init() {
        angle = (float) (angle + Math.ceil(-angle / 360) * 360);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(stroke);
        paint.setAntiAlias(true);

        double maxLength = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2)) / 2f;
        float centerX = width / 2f;
        float centerY = height / 2f;

        float endX = Math.round((float) (centerX - Math.cos(Math.toRadians(angle)) * maxLength));
        float endY = Math.round((float) (centerY + Math.sin(Math.toRadians(angle)) * maxLength));

        float startX = Math.round((float) (centerX + Math.cos(Math.toRadians(angle)) * maxLength));
        float startY = Math.round((float) (centerY - Math.sin(Math.toRadians(angle)) * maxLength));

        // Rects that delimiter the canvas
        rects.add(new RectF(0, 0, width, 0));
        rects.add(new RectF(width, 0, width, height));
        rects.add(new RectF(0, height, width, height));
        rects.add(new RectF(0, 0, 0, height));

        PointF start = new PointF(startX, startY);
        PointF end = new PointF(endX, endY);

        normalize(start, end);

        baseline = new Line(start, end, null, interpolator);
        lines.add(baseline);

        addLine(baseline, 1);
        addLine(baseline, -1);
    }

    private void normalize(PointF start, PointF end) {
        ArrayList<PointF> pointsBackup = new ArrayList<>();

        for (int i = 0; i < rects.size() && pointsBackup.size() < 2; ++i) {
            RectF rectF = rects.get(i);
            PointF intersect = intersection(rectF.left, rectF.top, rectF.right, rectF.bottom,
                    start.x, start.y, end.x, end.y);

            if (intersect != null && intersect.x >= 0f && intersect.x <= width &&
                    intersect.y >= 0f && intersect.y <= height &&
                    !pointsBackup.contains(intersect)) {
                pointsBackup.add(intersect);
            }
        }

        Collections.sort(pointsBackup, new Comparator<PointF>() {
            @Override public int compare(PointF lhs, PointF rhs) {
                return (int) (angle > 180f ? rhs.y - lhs.y : lhs.y - rhs.y);
            }
        });

        for (int i = 0; i < pointsBackup.size(); ++i) {
            PointF intersect = pointsBackup.get(i);
            PointF normalize = i == 0 ? start : end;
            normalize.set(intersect.x, intersect.y);
        }
    }

    private void addLine(Line line, int direction) {
        PointF start = line.getStart();
        PointF end = line.getEnd();
        if (((start.x < 0 && end.x < 0) || (start.y < 0 && end.y < 0)) ||
                ((start.x > width && end.x > width) || (start.y > width && end.y > width))) {
            return;
        }

        PointF nextStart = new PointF(start.x, start.y);
        PointF nextEnd = new PointF(end.x, end.y);

        if (angle >= 45f && angle <= 135f) {
            nextStart.offset(amplitude * direction, 0);
            nextEnd.offset(amplitude * direction, 0);
        } else {
            nextStart.offset(0, amplitude * direction);
            nextEnd.offset(0, amplitude * direction);
        }

        normalize(nextStart, nextEnd);

        Line nextLine = new Line(nextStart, nextEnd, line, interpolator);
        addLine(nextLine, direction);
        lines.add(nextLine);
    }

    /**
     * Calculate the intersection between two lines given by two points
     *
     * @param x1 X coordinates of the start point of the first rect
     * @param y1 Y coordinates of the start point of the first rect
     * @param x2 X coordinates of the end point of the first rect
     * @param y2 Y coordinates of the start point of the first rect
     * @param x3 X coordinates of the start point of the second rect
     * @param y3 Y coordinates of the start point of the second rect
     * @param x4 X coordinates of the end point of the second rect
     * @param y4 Y coordinates of the end point of the second rect
     * @return the intersection point. Null if the rect not intercept
     */
    public PointF intersection(
            float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        float d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (d == 0) {
            return null;
        }

        float xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
        float yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;

        xi = xi == -0f ? 0f : xi;
        yi = yi == -0f ? 0f : yi;

        return new PointF(xi, yi);
    }

    @Override public void render(Canvas canvas) {
        boolean finish = true;

        for (int i = 0; i < lines.size(); ++i) {
            finish &= drawLine(canvas, lines.get(i));
        }

        finished = finish;
    }

    private boolean drawLine(Canvas canvas, Line line) {
        if (line.getNeighbour() != null &&
                !isPointInCircle(line.getNeighbour().getStart().x,
                        line.getNeighbour().getStart().y,
                        line.getNeighbour().getDrawnPath() - (amplitude / 2),
                        line.getStart().x,
                        line.getStart().y)) {
            return true;
        }

        TimeInterpolator interpolator = line.getInterpolator();
        float interpolation = interpolator.getInterpolation(
                (line.getIteration() / (velocity / (float) Config.FRAMES_PER_SECOND)) / 100f);
        line.addIteration(Config.FRAMES_PER_SECOND);
        float drawnPath = interpolation * line.getLength();
        drawnPath = drawnPath > line.getLength() ? line.getLength() : drawnPath;
        line.setDrawnPath(drawnPath);

        PointF end = line.getDrawnPathCoordinate();
        canvas.drawLine(line.getStart().x, line.getStart().y, end.x, end.y, paint);

        return line.isDrawn();
    }

    @Override public boolean isFinished() {
        return finished;
    }

    @Override public void clear() {
        finished = false;
    }

    boolean isInRectangle(double centerX, double centerY, double radius, double x, double y) {
        return x >= centerX - radius && x <= centerX + radius &&
                y >= centerY - radius && y <= centerY + radius;
    }


    boolean isPointInCircle(
            double centerX, double centerY, double radius, double x, double y) {
        if (isInRectangle(centerX, centerY, radius, x, y)) {
            double dx = centerX - x;
            double dy = centerY - y;
            dx *= dx;
            dy *= dy;
            double distanceSquared = dx + dy;
            double radiusSquared = radius * radius;
            return distanceSquared <= radiusSquared;
        }
        return false;
    }

    public static class Builder {
        private int width = 0;
        private int height = 0;
        private int amplitude = 0;
        private int color = 0;
        private int velocity = 0;
        private float angle = 0;
        private float stroke = 0;
        private TimeInterpolator interpolator = new LinearInterpolator();

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

        public Builder setInterpolator(TimeInterpolator interpolator) {
            this.interpolator = interpolator;
            return this;
        }

        public LinesRenderer build() {
            return new LinesRenderer(this);
        }
    }
}
