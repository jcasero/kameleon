package com.tekihub.kameleon;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.view.Gravity;
import android.view.SurfaceHolder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;
import com.tekihub.kameleon.domain.Constants;
import com.tekihub.kameleon.renderers.SuperKameleon;
import com.tekihub.kameleon.renderers.background.BackgroundRenderer;
import com.tekihub.kameleon.renderers.background.BackgroundRendererImpl;

import java.util.ArrayList;

public class KameleonWatchFaceService extends CanvasWatchFaceService {
    private static final String TAG = "WatchFaceService";

    @Override public Engine onCreateEngine() {
        return new KameleonWatchFaceEngine();
    }

    private class KameleonWatchFaceEngine extends CanvasWatchFaceService.Engine implements DataApi.DataListener,
            GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

        private BackgroundRenderer renderer;
        private SuperKameleon superKameleon = new SuperKameleon();

        private GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(KameleonWatchFaceService.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();

        private Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override public void run() {
                invalidate();
            }
        };

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            renderer = new BackgroundRendererImpl(getApplicationContext(), superKameleon);

            setWatchFaceStyle(new WatchFaceStyle.Builder(KameleonWatchFaceService.this)
                    .setCardPeekMode(WatchFaceStyle.PEEK_MODE_SHORT)
                    .setPeekOpacityMode(WatchFaceStyle.PEEK_OPACITY_MODE_TRANSLUCENT)
                    .setBackgroundVisibility(WatchFaceStyle.BACKGROUND_VISIBILITY_INTERRUPTIVE)
                    .setViewProtectionMode((WatchFaceStyle.PROTECT_STATUS_BAR | WatchFaceStyle.PROTECT_HOTWORD_INDICATOR))
                    .setShowSystemUiTime(false)
                    .setStatusBarGravity(Gravity.RIGHT | Gravity.TOP)
                    .setHotwordIndicatorGravity(Gravity.RIGHT | Gravity.BOTTOM)
                    .build());

            mGoogleApiClient.connect();
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);

            renderer.setDimension(width, height);
        }

        @Override
        public void onPropertiesChanged(Bundle properties) {
            super.onPropertiesChanged(properties);

        }

        @Override
        public void onTimeTick() {
            super.onTimeTick();
            invalidate();

        }

        @Override
        public void onAmbientModeChanged(boolean inAmbientMode) {
            super.onAmbientModeChanged(inAmbientMode);

        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            boolean finished = renderer.isFinished();

            if (!finished) {
                handler.postDelayed(runnable, 20);
            }

            renderer.render(canvas);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (visible) {
                mGoogleApiClient.connect();
            } else {
                if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                    Wearable.DataApi.removeListener(mGoogleApiClient, this);
                    mGoogleApiClient.disconnect();
                }
            }
        }

        @Override public void onConnected(@Nullable Bundle bundle) {
            Wearable.DataApi.addListener(mGoogleApiClient, KameleonWatchFaceEngine.this);
        }

        @Override public void onConnectionSuspended(int i) {

        }

        @Override public void onDataChanged(DataEventBuffer dataEventBuffer) {

            for (DataEvent event : dataEventBuffer) {
                if (event.getType() == DataEvent.TYPE_CHANGED) {
                    DataItem item = event.getDataItem();
                    if (item.getUri().getPath().compareTo(Constants.DATA_MAP_PATH) == 0) {
                        DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                        ArrayList<Integer> colors = dataMap.getIntegerArrayList(Constants.DATA_MAP_KEY_COLORS);
                        renderer.create(colors);
                        invalidate();
                    }
                }
            }
        }

        @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        }
    }
}
