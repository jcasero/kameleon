package com.tekihub.kameleon.watchtheme.communication;

import android.support.annotation.NonNull;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.tekihub.kameleon.domain.ApplicationColorSet;
import com.tekihub.kameleon.domain.Constants;
import com.tekihub.kameleon.watchtheme.di.WatchThemeScope;

import javax.inject.Inject;

@WatchThemeScope
public class DataSender {
    private static final String TAG = "DataSender";
    private GoogleApiClient googleApiClient;

    @Inject
    public DataSender(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    public void send(ApplicationColorSet applicationColorSet) {
        PutDataMapRequest putDataMapReq = PutDataMapRequest.create(Constants.DATA_MAP_PATH);
        putDataMapReq.getDataMap().putIntegerArrayList(Constants.DATA_MAP_KEY_COLORS, applicationColorSet.getColorSet());
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(googleApiClient, putDataReq);
        pendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
            @Override public void onResult(@NonNull DataApi.DataItemResult dataItemResult) {
                // Ignore the result by now.
            }
        });
    }


}
