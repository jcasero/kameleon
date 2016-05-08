package com.tekihub.kameleon.renderers.effects.lines;

import android.animation.TimeInterpolator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;

/**
 * Created by Jose on 24/4/16.
 */
public class Line {
    private PathMeasure pathMeasure = new PathMeasure();
    private final PointF start;
    private final PointF end;
    private final Line neighbour;
    private final TimeInterpolator interpolator;
    private float drawnPath = 0;
    private float length = 0;
    private int iteration = 0;

    public Line(PointF start, PointF end, Line neighbour, TimeInterpolator interpolator) {
        this.start = start;
        this.end = end;
        this.neighbour = neighbour;
        this.interpolator = interpolator;
        setLength();
        setPath();
    }

    private void setPath() {
        Path path = new Path();
        path.reset();
        path.moveTo(start.x, start.y);
        path.lineTo(end.x, end.y);
        path.close();

        pathMeasure.setPath(path, false);
    }

    public PointF getStart() {
        return start;
    }

    public PointF getEnd() {
        return end;
    }

    public TimeInterpolator getInterpolator() {
        return interpolator;
    }

    public float getDrawnPath() {
        return drawnPath;
    }

    public void setDrawnPath(float length) {
        this.drawnPath = length;
    }

    public boolean isDrawn() {
        return drawnPath >= length;
    }

    public void reset() {
        drawnPath = 0;
        iteration = 0;
    }

    public float getLength() {
        return length;
    }

    private void setLength() {
        length = (float) Math.sqrt((start.x - end.x) * (start.x - end.x) + (start.y - end.y) * (start.y - end.y));
    }

    public int getIteration() {
        return iteration;
    }

    public void addIteration(int iteration) {
        this.iteration += iteration;
    }

    public Line getNeighbour() {
        return neighbour;
    }

    public PointF getDrawnPathCoordinate() {
        float[] pos = new float[2];
        pathMeasure.getPosTan(drawnPath, pos, null);

        return new PointF(pos[0], pos[1]);
    }
}
