package com.tekihub.kameleon.renderers;

import com.tekihub.kameleon.renderers.effects.CirclesRenderer;
import com.tekihub.kameleon.renderers.effects.lines.LinesRenderer;
import com.tekihub.kameleon.utils.Config;
import java.util.ArrayList;
import java.util.Random;

public class SuperKameleon {
    private static final int TYPES = 3;

    private static final int LINES = 0;
    private static final int CIRCLES = 1;
    private static final int CIRCLES_RANDOM_RADIUS = 2;

    private Random random = new Random(System.currentTimeMillis());

    public void kameleonize(int width, int height, ArrayList<Integer> colors, ArrayList<Renderer> renderers) {

        int pattern = random.nextInt(TYPES);
        switch (pattern) {
            case LINES:
                createLines(width, height, colors, renderers);
                break;
            case CIRCLES:
                createCircles(width, height, colors, renderers);
                break;
            case CIRCLES_RANDOM_RADIUS:
                createCirclesRadius(width, height, colors, renderers);
                break;
        }
    }

    private void createLines(int width, int height, ArrayList<Integer> colors, ArrayList<Renderer> renderers) {
        int amplitude = random.nextInt(Config.LINES_RENDERER_MAX_AMPLITUDE - Config.LINES_RENDERER_MIN_AMPLITUDE + 1)
            + Config.LINES_RENDERER_MIN_AMPLITUDE;
        int velocity = random.nextInt(Config.LINES_RENDERER_MAX_VELOCITY - Config.LINES_RENDERER_MIN_VELOCITY + 1)
            + Config.LINES_RENDERER_MIN_VELOCITY;
        float angle = random.nextFloat() * (Config.LINES_RENDERER_MAX_ANGLE - Config.LINES_RENDERER_MIN_ANGLE)
            + Config.LINES_RENDERER_MIN_ANGLE;
        float stroke = random.nextFloat() * (Config.LINES_RENDERER_MAX_STROKE - Config.LINES_RENDERER_MIN_STROKE)
            + Config.LINES_RENDERER_MIN_STROKE;

        createLinesColor(width, height, colors.get(1), amplitude, angle, stroke, velocity, renderers);
        createLinesColor(width, height, colors.get(2), amplitude, 180 - angle, stroke, velocity, renderers);
    }

    private void createLinesAxis(int width, int height, ArrayList<Integer> colors, ArrayList<Renderer> renderers) {
        int amplitude = random.nextInt(Config.LINES_RENDERER_MAX_AMPLITUDE - Config.LINES_RENDERER_MIN_AMPLITUDE + 1)
            + Config.LINES_RENDERER_MIN_AMPLITUDE;
        int velocity = random.nextInt(Config.LINES_RENDERER_MAX_VELOCITY - Config.LINES_RENDERER_MIN_VELOCITY + 1)
            + Config.LINES_RENDERER_MIN_VELOCITY;
        float angle = random.nextFloat() * (Config.LINES_RENDERER_MAX_ANGLE - Config.LINES_RENDERER_MIN_ANGLE)
            + Config.LINES_RENDERER_MIN_ANGLE;
        float stroke = random.nextFloat() * (Config.LINES_RENDERER_MAX_STROKE - Config.LINES_RENDERER_MIN_STROKE)
            + Config.LINES_RENDERER_MIN_STROKE;
        createLinesColor(width, height, colors.get(1), amplitude, angle, stroke, velocity, renderers);

        amplitude = random.nextInt(Config.LINES_RENDERER_MAX_AMPLITUDE - Config.LINES_RENDERER_MIN_AMPLITUDE + 1)
            + Config.LINES_RENDERER_MIN_AMPLITUDE;
        velocity = random.nextInt(Config.LINES_RENDERER_MAX_VELOCITY - Config.LINES_RENDERER_MIN_VELOCITY + 1)
            + Config.LINES_RENDERER_MIN_VELOCITY;
        angle = random.nextFloat() * (Config.LINES_RENDERER_MAX_ANGLE - Config.LINES_RENDERER_MIN_ANGLE)
            + Config.LINES_RENDERER_MIN_ANGLE;
        stroke = random.nextFloat() * (Config.LINES_RENDERER_MAX_STROKE - Config.LINES_RENDERER_MIN_STROKE)
            + Config.LINES_RENDERER_MIN_STROKE;
        createLinesColor(width, height, colors.get(2), amplitude, angle, stroke, velocity, renderers);
    }

    private void createLinesColor(int width, int height, int color, int amplitude, float angle, float stroke,
        int velocity, ArrayList<Renderer> renderers) {
        renderers.add(new LinesRenderer.Builder(width, height).setAmplitude(amplitude)
            .setAngle(angle)
            .setColor(color)
            .setStroke(stroke)
            .setVelocity(velocity)
            .build());
    }

    private void createCircles(int width, int height, ArrayList<Integer> colors, ArrayList<Renderer> renderers) {
        int radius = random.nextInt(Config.CIRCLES_RENDERER_MAX_RADIUS - Config.CIRCLES_RENDERER_MIN_RADIUS + 1)
            + Config.CIRCLES_RENDERER_MIN_RADIUS;
        int velocity = random.nextInt(Config.CIRCLES_RENDERER_MAX_VELOCITY - Config.CIRCLES_RENDERER_MIN_VELOCITY + 1)
            + Config.CIRCLES_RENDERER_MIN_VELOCITY;

        createCirclesColor(width, height, colors.get(1), radius, velocity, renderers);
        createCirclesColor(width, height, colors.get(2), radius, velocity, renderers);
    }

    private void createCirclesRadius(int width, int height, ArrayList<Integer> colors, ArrayList<Renderer> renderers) {
        int radius = random.nextInt(Config.CIRCLES_RENDERER_MAX_RADIUS - Config.CIRCLES_RENDERER_MIN_RADIUS + 1)
            + Config.CIRCLES_RENDERER_MIN_RADIUS;
        int velocity = random.nextInt(Config.CIRCLES_RENDERER_MAX_VELOCITY - Config.CIRCLES_RENDERER_MIN_VELOCITY + 1)
            + Config.CIRCLES_RENDERER_MIN_VELOCITY;

        createCirclesColor(width, height, colors.get(1), radius, velocity, renderers);

        radius = random.nextInt(Config.CIRCLES_RENDERER_MAX_RADIUS - Config.CIRCLES_RENDERER_MIN_RADIUS + 1)
            + Config.CIRCLES_RENDERER_MIN_RADIUS;
        velocity = random.nextInt(Config.CIRCLES_RENDERER_MAX_VELOCITY - Config.CIRCLES_RENDERER_MIN_VELOCITY + 1)
            + Config.CIRCLES_RENDERER_MIN_VELOCITY;
        createCirclesColor(width, height, colors.get(2), radius, velocity, renderers);
    }

    private void createCirclesColor(int width, int height, int color, int radius, int velocity,
        ArrayList<Renderer> renderers) {
        renderers.add(
            new CirclesRenderer.Builder(width, height).setVelocity(velocity).setColor(color).setRadius(radius).build());
    }
}
