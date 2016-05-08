package com.tekihub.kameleon.renderers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import com.tekihub.kameleon.utils.FontManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ClockRenderer implements Renderer {
    private static final float DEFAULT_TEXT_SIZE = 65f;
    private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#E9FFFFFF");
    private static final float DEFAULT_SHADOW_SIZE = 2f;
    private static final int DEFAULT_SHADOW_COLOR = Color.parseColor("#757575");

    private FontManager fontManager;
    private Paint paintHour = new Paint();
    private Paint paintMinutes;
    private SimpleDateFormat simpleDateFormatHour;
    private SimpleDateFormat simpleDateFormatMinutes;
    private Rect bounds = new Rect();

    public ClockRenderer(Context context) {
        simpleDateFormatHour = new SimpleDateFormat("hh", Locale.getDefault());
        simpleDateFormatMinutes = new SimpleDateFormat("mm", Locale.getDefault());
        fontManager = new FontManager(context);
        paintHour.setColor(DEFAULT_TEXT_COLOR);
        paintHour.setStyle(Paint.Style.FILL);
        paintHour.setTextSize(DEFAULT_TEXT_SIZE);
        paintHour.setAntiAlias(true);
        paintHour.setShadowLayer(DEFAULT_SHADOW_SIZE, 0f, 0f, DEFAULT_SHADOW_COLOR);
        paintHour.setTypeface(fontManager.getTypeface(FontManager.ROBOTO_BOLD));

        paintMinutes = new Paint(paintHour);
        paintMinutes.setTypeface(fontManager.getTypeface(FontManager.ROBOTO_THIN));
        paintMinutes.setTextSize(DEFAULT_TEXT_SIZE);
    }

    public void render(Canvas canvas) {
        Date date = new Date();
        String hour = simpleDateFormatHour.format(date);
        String minutes = simpleDateFormatMinutes.format(date);

        paintHour.getTextBounds(hour, 0, hour.length(), bounds);
        canvas.drawText(hour, 20, 60, paintHour);

        int x = bounds.right + 2;
        paintMinutes.getTextBounds(":", 0, 1, bounds);
        canvas.drawText(":", 20 + x, 59, paintMinutes);
        x += bounds.right - 2;
        canvas.drawText(minutes, 22 + x, 60, paintMinutes);
    }

    @Override public boolean isFinished() {
        return true;
    }

    @Override public void clear() {

    }
}
