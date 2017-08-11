package com.s5.plugins;

import org.apache.cordova.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Handler;
import android.os.Message;
import co.kr.bluebird.ser.protocol.Reader;


public class rfr900 extends CordovaPlugin {
    private CallbackContext _eventCallback;
    private Reader mReader;

    public Handler mRFConfigHandler = new Handler() {
        public void handleMessage(Message m) {
                     
        }
    };

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        _eventCallback = callbackContext;

        if (actions.equals("connect")) {

            mReader = Reader.getReader(this, mRFConfigHandler);
            ret = mReader.SD_Connect();           
            PluginResult pluginResult = null;
            pluginResult = new PluginResult(PluginResult.Status.OK, "HELLO: " + ret);
            _eventCallback.sendPluginResult(pluginResult);
            return true;     
        } else {
            PluginResult pluginResult = null;
            pluginResult = new PluginResult(PluginResult.Status.OK, "HELLO WORLD");
            _eventCallback.sendPluginResult(pluginResult);
            return true;            
        }
    }
}

