package com.piotr.ets2remotecontrol;


import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCPClient extends AsyncTask<String, String, String>
{
    private Socket clientSocket;
    private String ip;
    private ConnectActivity connectActivity;
    private boolean working;

    public TCPClient(ConnectActivity connectActivity_, String ip_)
    {
        ip = ip_;
        connectActivity = connectActivity_;

        working = false;
    }

    @Override
    protected String doInBackground(String... params)
    {
        try
        {
            Log.d("TCPClient", "Connecting...");
            clientSocket = new Socket(ip, 12345);
            Log.d("TCPClient", "Connected!");

            connectActivity.runOnUiThread(new Runnable(){
                @Override
                public void run()
                {
                    connectActivity.onFinish();
                }
            });

            working = true;

            /*while (working)
            {
                InputStreamReader reader = new InputStreamReader(clientSocket.getInputStream());

                int type = reader.read();

                RemoteControlApp.PacketType packetType = RemoteControlApp.PacketType.values()[type];
            }*/
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public void sendPos(RemoteControlApp.PacketType type, int x)
    {
        Log.d("TCPClient", "sendPos: id: " + String.valueOf(type.getValue()) +  " X: " + String.valueOf(x));

        try
        {
            Integer integer = x;

            OutputStreamWriter writer = new OutputStreamWriter(clientSocket.getOutputStream());
            writer.write(type.getValue());
            writer.write(integer.toString() + "\0");
            writer.flush();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void close()
    {
        try
        {
            working = false;
            clientSocket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
