package com.tekihub.kameleon.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.util.ArrayMap;

public class FontManager {
    private static final String BASE_PATH   = "fonts/";
    public static final  String ROBOTO_THIN = "Roboto-Thin.ttf";
    public static final  String ROBOTO_BOLD = "Roboto-Bold.ttf";

    private Context context;
    private ArrayMap<String, Typeface> fonts = new ArrayMap<>();


    public FontManager(Context context) {
        this.context = context;
    }

    public Typeface getTypeface(String font) {
        if (fonts.containsKey(font)) {
            return fonts.get(font);
        }

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), BASE_PATH + font);
        fonts.put(font, typeface);
        return typeface;
    }


}
