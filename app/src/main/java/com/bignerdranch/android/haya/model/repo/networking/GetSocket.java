package com.bignerdranch.android.haya.model.repo.networking;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class GetSocket {
    private static final String TAG = "GetSocket";
    private static GetSocket ourInstance;
    private static Socket sSocket;
    private static String sToken;
    public static GetSocket getInstance(String token) {
        try {
            sSocket =  IO.socket("http://104.248.25.114:4000/chat");
            sToken = token;
            sSocket.on(Socket.EVENT_CONNECT,auth);
            sSocket.on("authenticated", onAuth);
            sSocket.connect();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return ourInstance;
    }

    private GetSocket() {
    }

    public static Socket getSocket() {
        return sSocket;
    }

    private static Emitter.Listener auth = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            /*JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("access_token", sToken);*/

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("access_token", sToken);
                sSocket.emit(SocketActions.EMIT_AUTH, jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
    private static Emitter.Listener onAuth = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
          System.out.println(args.toString());
            Log.d(TAG, "call: "+args.toString());
        }
    };
}
