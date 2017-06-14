package com.piotr.ets2remotecontrol;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiCheck = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!wifiCheck.isConnected())
        {
            Toast.makeText(getApplicationContext(), "Turn on wifi!", Toast.LENGTH_SHORT).show();
            return;
        }

        connectingDialog = ProgressDialog.show(this, "", "Connecting...", true);

        EditText editText = (EditText)findViewById(R.id.ipEditText);

        TCPClient tcpClient = new TCPClient(this, editText.getText().toString());
        tcpClient.execute();

        ((RemoteControlApp)getApplication()).tcpClient = tcpClient;
    }

    public void onFinish()
    {
        connectingDialog.dismiss();
        Intent intent = new Intent(ConnectActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
