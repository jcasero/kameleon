package com.tekihub.kameleon.watchtheme.theme;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;

import com.tekihub.kameleon.domain.ApplicationColorSet;
import com.tekihub.kameleon.watchtheme.di.WatchThemeScope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Func1;

@WatchThemeScope
public class GetApplicationPaletteTask implements Func1<String, ApplicationColorSet> {
    protected Context context;

    @Inject
    public GetApplicationPaletteTask(Context context) {
        this.context = context;
    }

    @Override public ApplicationColorSet call(final String packageName) {

        PackageManager packageManager = context.getPackageManager();
        Drawable icon = null;
        try {
            icon = packageManager.getApplicationIcon(packageName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (icon == null) {
            return null;
        }

        Bitmap bitmap = drawableToBitmap(icon);
        Palette palette = Palette.from(bitmap).maximumColorCount(3).generate();

        List<Palette.Swatch> swatches = new ArrayList<>(palette.getSwatches());
        Collections.sort(swatches, new Comparator<Palette.Swatch>() {
            @Override public int compare(Palette.Swatch swatch, Palette.Swatch t1) {
                return t1.getPopulation() - swatch.getPopulation();
            }
        });

        ArrayList<Integer> applicationColorSets = new ArrayList<>();
        for (Palette.Swatch swatch : swatches) {
            if (swatch != null) {
                applicationColorSets.add(swatch.getRgb());
            }
        }
        return new ApplicationColorSet(applicationColorSets);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
