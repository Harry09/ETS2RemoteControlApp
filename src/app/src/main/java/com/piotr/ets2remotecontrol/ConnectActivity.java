package com.piotr.ets2remotecontrol;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ConnectActivity extends AppCompatActivity
{
    ProgressDialog connectingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
    }

    public void connectButton_onClick(View view)
    {
        connectingDialog = ProgressDialog.show(this, "", "Connecting...", true);
    }
}
