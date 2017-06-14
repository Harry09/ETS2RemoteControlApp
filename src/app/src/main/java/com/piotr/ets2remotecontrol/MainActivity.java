package com.piotr.ets2remotecontrol;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.ToggleButton;
import android.widget.VerticalSeekBar;

public class MainActivity extends AppCompatActivity
{
    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        instance = this;

        RemoteControlApp app = (RemoteControlApp)getApplication();

        ((SeekBar)findViewById(R.id.wheelSeekBar)).setOnSeekBarChangeListener(new OnSeekBarChangeListener(app.tcpClient, RemoteControlApp.PacketType.WHEEL));
        ((SeekBar)findViewById(R.id.cameraSeekBar)).setOnSeekBarChangeListener(new OnSeekBarChangeListener(app.tcpClient, RemoteControlApp.PacketType.CAMERA));
        ((VerticalSeekBar)findViewById(R.id.engineSeekBar)).setOnSeekBarChangeListener(new OnSeekBarChangeListener(app.tcpClient, RemoteControlApp.PacketType.ENGINE));
        ((VerticalSeekBar)findViewById(R.id.brakeSeekBar)).setOnSeekBarChangeListener(new OnSeekBarChangeListener(app.tcpClient, RemoteControlApp.PacketType.BRAKE));
        ((VerticalSeekBar)findViewById(R.id.gearSeekBar)).setOnSeekBarChangeListener(new OnSeekBarChangeListener(app.tcpClient, RemoteControlApp.PacketType.GEAR));
    }

    public void engineButton_onClick(View view)
    {
        final RemoteControlApp app = (RemoteControlApp)getApplication();

        boolean isChecked = ((ToggleButton)view).isChecked();
        app.mEngineEnabled = isChecked;

        int valueToSend = 0;

        if (isChecked)
            valueToSend = ((VerticalSeekBar)findViewById(R.id.engineSeekBar)).getProgress();

        final int finalValueToSend = valueToSend;

        new Thread(new Runnable() {
            public void run() {
                app.tcpClient.sendPos(RemoteControlApp.PacketType.ENGINE, finalValueToSend);
            }}).start();
    }

    public void brakeButton_onClick(View view)
    {
        final RemoteControlApp app = (RemoteControlApp)getApplication();

        boolean isChecked = ((ToggleButton)view).isChecked();
        app.mBrakeEnabled = isChecked;

        int valueToSend = 0;

        if (isChecked)
            valueToSend = ((VerticalSeekBar)findViewById(R.id.brakeSeekBar)).getProgress();

        final int finalValueToSend = valueToSend;

        new Thread(new Runnable() {
            public void run() {
                app.tcpClient.sendPos(RemoteControlApp.PacketType.BRAKE, finalValueToSend);
            }}).start();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        final RemoteControlApp app = (RemoteControlApp)getApplication();
        app.tcpClient.close();
    }

    public static MainActivity get()
    {
        return instance;
    }
}
