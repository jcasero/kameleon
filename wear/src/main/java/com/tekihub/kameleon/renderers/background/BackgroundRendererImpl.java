package com.tekihub.kameleon.renderers.background;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import com.tekihub.kameleon.R;
import com.tekihub.kameleon.renderers.ClockRenderer;
import com.tekihub.kameleon.renderers.DarkLayerRenderer;
import com.tekihub.kameleon.renderers.Renderer;
import com.tekihub.kameleon.renderers.SuperKameleon;
import com.tekihub.kameleon.renderers.effects.PlainRenderer;
import com.tekihub.kameleon.utils.Config;
import java.util.ArrayList;
import java.util.Random;

public class BackgroundRendererImpl implements BackgroundRenderer {
    private Context context;
    private ArrayList<Integer> colors = new ArrayList<>();
    private ArrayList<Renderer> renderers = new ArrayList<>();
    private Random random = new Random(System.currentTimeMillis());
    private SuperKameleon superKameleon;

    private int width = 0;
    private int height = 0;

    public BackgroundRendererImpl(Context context, SuperKameleon superKameleon) {
        this.context = context;
        this.superKameleon = superKameleon;
    }

    @Override public void create(@Nullable ArrayList<Integer> colors) {
        this.colors.clear();

        if (colors != null) {
            this.colors.addAll(colors);
        }

        build();
    }

    @Override public void setDimension(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private void build() {
        if (!checkIfValidColors()) {
            sanitizeColors();
        }

        renderers.add(new PlainRenderer(this.colors.get(0)));

        superKameleon.kameleonize(width, height, colors, renderers);

        renderers.add(new DarkLayerRenderer());
        renderers.add(new ClockRenderer(context));
    }

    private void sanitizeColors() {
        for (int i = colors.size(); i < Config.MAX_COLORS; ++i) {
            this.colors.add(randomColor());
        }
    }

    private int randomColor() {
        int[] colors = context.getResources().getIntArray(R.array.colors);
        return colors[random.nextInt(colors.length)];
    }

    private boolean checkIfValidColors() {
        return colors != null && colors.size() > Config.MAX_COLORS - 1;
    }

    @Override public void render(Canvas canvas) {
        if (colors.size() == 0) {
            create(null);
        }
        for (Renderer renderer : renderers) {
            renderer.render(canvas);
        }
    }

    @Override public boolean isFinished() {
        boolean result = true;
        for (Renderer renderer : renderers) {
            result &= renderer.isFinished();
        }
        return result;
    }
}
