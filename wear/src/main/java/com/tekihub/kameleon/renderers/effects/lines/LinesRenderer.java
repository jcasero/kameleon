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

public class LinesRenderer implements Renderer {
    private int width = 0;
    private int height = 0;
    private int amplitude = 0;
    private int color = 0;
    private int duration = 0;
    private float stroke = 8f;
    private float angle = 0;
    private Paint paint = new Paint();
    private TimeInterpolator interpolator;
    private ArrayList<Line> lines = new ArrayList<>();
    private List<RectF> rects = new ArrayList<>();
    private boolean finished = false;

    private LinesRenderer(Builder builder) {
        this.width = builder.width;
        this.height = builder.height;
        this.amplitude = builder.amplitude;
        this.stroke = builder.stroke;
        this.duration = builder.duration;
        this.color = builder.color;
        this.angle = getNormalizedAngle(builder.angle);
        this.interpolator = builder.interpolator;
        init();
    }

    private void init() {
        initializePaint();

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

        Line baseline = new Line(start, end, null, interpolator);
        lines.add(baseline);

        addLine(baseline, 1);
        addLine(baseline, -1);
    }

    private float getNormalizedAngle(float angle) {
        return (float) (angle + Math.ceil(-angle / 360) * 360);
    }

    private void initializePaint() {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(stroke);
        paint.setAntiAlias(true);
    }

    private void normalize(PointF start, PointF end) {
        ArrayList<PointF> pointsBackup = new ArrayList<>();

        for (int i = 0; i < rects.size() && pointsBackup.size() < 2; ++i) {
            RectF rectF = rects.get(i);
            PointF intersect =
                intersection(rectF.left, rectF.top, rectF.right, rectF.bottom, start.x, start.y, end.x, end.y);

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
        if (((start.x < 0 && end.x < 0) || (start.y < 0 && end.y < 0)) || ((start.x > width && end.x > width)
            || (start.y > width && end.y > width))) {
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

    @Override public boolean isFinished() {
        return finished;
    }

    @Override public void clear() {
        finished = false;
        resetLines();
    }

    @Override public void render(Canvas canvas) {
        boolean finish = true;

        for (int i = 0; i < lines.size(); ++i) {
            finish &= drawLine(canvas, lines.get(i));
        }

        finished = finish;
    }

    private void resetLines() {
        for (int i = 0; i < lines.size(); ++i) {
            lines.get(i).reset();
        }
    }

    private boolean drawLine(Canvas canvas, Line line) {
        if (!checkIfLineShouldBeDrawn(line)) {
            return true;
        }

        TimeInterpolator interpolator = line.getInterpolator();
        float interpolation =
            interpolator.getInterpolation((line.getIteration() / (duration / (float) Config.FRAMES_PER_SECOND)) / 100f);
        line.addIteration(Config.FRAMES_PER_SECOND);
        float drawnPath = interpolation * line.getLength();
        drawnPath = drawnPath > line.getLength() ? line.getLength() : drawnPath;
        line.setDrawnPath(drawnPath);

        PointF end = line.getDrawnPathCoordinate();
        canvas.drawLine(line.getStart().x, line.getStart().y, end.x, end.y, paint);

        return line.isDrawn();
    }

    private boolean checkIfLineShouldBeDrawn(Line line) {
        Line neighbour = line.getNeighbour();

        // neighbour equal than null means that is the baseline and the line must be drawn
        if (neighbour == null) {
            return true;
        }

        // If the start of the line given by parameters is inside the circle drawn by the end of the neighbour line
        // then the line must be drawn
        float radius = neighbour.getDrawnPath() - (amplitude / 2);
        return isPointInCircle(neighbour.getStart(), radius, line.getStart());
    }

    private PointF intersection(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
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

    private boolean isPointInRectangle(PointF center, double radius, PointF point) {
        return point.x >= center.x - radius && point.x <= center.x + radius &&
            point.y >= center.y - radius && point.y <= center.y + radius;
    }

    private boolean isPointInCircle(PointF center, double radius, PointF point) {
        if (isPointInRectangle(center, radius, point)) {
            double dx = center.x - point.x;
            double dy = center.y - point.y;
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
        private int duration = 0;
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

        public Builder setDuration(int duration) {
            this.duration = duration;
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
