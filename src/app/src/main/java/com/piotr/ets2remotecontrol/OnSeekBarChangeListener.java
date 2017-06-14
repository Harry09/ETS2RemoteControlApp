package com.piotr.ets2remotecontrol;


import android.widget.SeekBar;
import android.widget.ToggleButton;

public class OnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener
{
    private TCPClient mTcpClient;
    private RemoteControlApp.PacketType mType;


    OnSeekBarChangeListener(TCPClient tcpClient, RemoteControlApp.PacketType type)
    {
        mTcpClient = tcpClient;
        mType = type;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        final int prog = progress;

        boolean send = true;

        // refresh button text
        if (mType == RemoteControlApp.PacketType.ENGINE)
        {
            ToggleButton engineButton = (ToggleButton)MainActivity.get().findViewById(R.id.engineButton);

            engineButton.setTextOn(String.valueOf(prog));
            engineButton.setChecked(engineButton.isChecked());

            if (!engineButton.isChecked())
                send = false;
        }
        else if (mType == RemoteControlApp.PacketType.BRAKE)
        {
            ToggleButton brakeButton = (ToggleButton)MainActivity.get().findViewById(R.id.brakeButton);

            brakeButton.setTextOn(String.valueOf(prog));
            brakeButton.setChecked(brakeButton.isChecked());

            if (!brakeButton.isChecked())
                send = false;
        }

        if (send)
        {
            new Thread(new Runnable() {
            public void run() {
                mTcpClient.sendPos(mType, prog);
            }}).start();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {
        if (mType == RemoteControlApp.PacketType.WHEEL)
            seekBar.setProgress(250);
        else if (mType == RemoteControlApp.PacketType.CAMERA)
            seekBar.setProgress(250);
    }
}
