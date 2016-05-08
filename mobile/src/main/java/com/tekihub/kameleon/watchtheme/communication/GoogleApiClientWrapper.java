package com.tekihub.kameleon.watchtheme.communication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.tekihub.kameleon.domain.ApplicationColorSet;
import com.tekihub.kameleon.domain.Constants;

public class GoogleApiClientWrapper
    implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleApiClient;

    public GoogleApiClientWrapper(Context context) {
        googleApiClient = new GoogleApiClient.Builder(context).addConnectionCallbacks(this)
            .addApi(Wearable.API)
            .addOnConnectionFailedListener(this)
            .build();
    }

    @Override public void onConnected(Bundle bundle) {

    }

    @Override public void onConnectionSuspended(int i) {

    }

    @Override public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void connect() {
        googleApiClient.connect();
    }

    public void disconnect() {
        googleApiClient.disconnect();
    }

    public void send(ApplicationColorSet applicationColorSet) {
        PutDataMapRequest putDataMapReq = PutDataMapRequest.create(Constants.DATA_MAP_PATH);
        putDataMapReq.getDataMap()
            .putIntegerArrayList(Constants.DATA_MAP_KEY_COLORS, applicationColorSet.getColorSet());
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(googleApiClient, putDataReq);
        pendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
            @Override public void onResult(@NonNull DataApi.DataItemResult dataItemResult) {
                // Ignore the result by now.
            }
        });
    }
}
