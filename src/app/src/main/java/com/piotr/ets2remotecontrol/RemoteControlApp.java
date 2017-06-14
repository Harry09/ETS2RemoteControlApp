package com.piotr.ets2remotecontrol;

import android.app.Application;

public class RemoteControlApp extends Application
{
    TCPClient tcpClient;

    public enum PacketType
    {
        // to server
        WHEEL(0),
        ENGINE(1),
        BRAKE(2),
        CAMERA(3),
        GEAR(4);

        private int value;

        PacketType(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return value;
        }
    }

    public boolean mEngineEnabled = false;
    public boolean mBrakeEnabled = false;
}
