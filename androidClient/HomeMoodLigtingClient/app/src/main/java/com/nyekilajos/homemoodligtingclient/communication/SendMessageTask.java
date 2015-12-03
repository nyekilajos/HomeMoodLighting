package com.nyekilajos.homemoodligtingclient.communication;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import roboguice.util.RoboAsyncTask;

import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;

/**
 * Created by Lajos_Nyeki on 11/13/2015.
 */
public class SendMessageTask extends RoboAsyncTask<Void> {

    private static final int CONNECTION_TIMEOUT = 5000;

    private String ipAddress;
    private int port;
    private String message;

    @Inject
    public SendMessageTask(Context context) {
        super(context);
    }

    public void send(String ipAddress, int port, String message) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.message = message;
        execute();
    }

    @Override
    public Void call() throws Exception {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(ipAddress, port), CONNECTION_TIMEOUT);
            DataOutputStream DataOut = new DataOutputStream(socket.getOutputStream());
            DataOut.writeBytes(message);
            DataOut.flush();
        } catch (IOException e) {
            Log.e(SendMessageTask.class.getName(), e.toString());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                Log.e(SendMessageTask.class.getName(), e.toString());
            }
        }
        return null;
    }
}
