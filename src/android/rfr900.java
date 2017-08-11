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
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import co.kr.bluebird.ser.protocol.Reader;
import co.kr.bluebird.ser.protocol.SDConsts;
import co.kr.bluebird.ser.protocol.*;

public class rfr900 extends CordovaPlugin {
    private CallbackContext _readCallback;
    private CallbackContext _eventCallback;

    private Context mContext;
    private Reader mReader;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        mContext = this.cordova.getActivity().getApplicationContext();
        _eventCallback = callbackContext;

        if (action.equals("connect")) {
            mReader = Reader.getReader(mContext, mRFConnectHandler);

            boolean isOpen = mReader.RF_Open();
            if (isOpen) {
                int ret = mReader.SD_Wakeup();
                return true;
            } else {
                PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, "Error on RF_Open()");
                _eventCallback.sendPluginResult(pluginResult);
                return false;
            }
        } else if (action.equals("readCallback")) {
            _readCallback = callbackContext;
            return true;
        } else if (action.equals("read")){
            mReader = Reader.getReader(this.cordova.getActivity().getApplicationContext(), mRFReadHandler);
            int result = mReader.RF_READ(SDConsts.RFMemType.EPC, 1, 7, "00000000", false);
            if (result != SDConsts.RFResult.SUCCESS) {
                PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, "Error reading");
                _eventCallback.sendPluginResult(pluginResult);
                return false;
            }
            return true;
        } else {
            PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, "Action doesn't exist");
            _eventCallback.sendPluginResult(pluginResult);
            return false;
        }
    }

    public Handler mRFConnectHandler = new Handler() {
        public void handleMessage(Message m) {
            switch (m.what) {
                case SDConsts.Msg.SDMsg:
                    if (m.arg1 == SDConsts.SDCmdMsg.SLED_WAKEUP) {
                        if (m.arg2 == SDConsts.SDResult.SUCCESS) {
                            int ret = mReader.SD_Connect();
                            if (ret == SDConsts.SDResult.SUCCESS || ret == SDConsts.SDResult.ALREADY_CONNECTED) {
                                mReader = Reader.getReader(mContext, mRFReadHandler);
                                PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, "Return value(We are connected): " + ret);
                                _eventCallback.sendPluginResult(pluginResult);
                            }
                        } else {
                            PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, "ERROR ON WAKEUP");
                            _eventCallback.sendPluginResult(pluginResult);
                        }
                    }
                    else if (m.arg1 == SDConsts.SDCmdMsg.SLED_UNKNOWN_DISCONNECTED) {
                        PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, "ERROR ON WAKEUP");
                        _eventCallback.sendPluginResult(pluginResult);
                    }
                    break;
            }
        }
    };

    public Handler mRFReadHandler = new Handler() {
        public void handleMessage(Message m) {
            // Trigger pressed
            if (m.arg1 == 41) {
                int result = mReader.RF_READ(SDConsts.RFMemType.EPC, 1, 7, "00000000", false);
                if (result != SDConsts.RFResult.SUCCESS) {
                    PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, "Error reading");
                    pluginResult.setKeepCallback(true);
                    _readCallback.sendPluginResult(pluginResult);
                }
            } else {
                int result = m.arg2;
                String data = "";
                if ((String)m.obj != null)
                    data = (String)m.obj;
                String messageStr = null;
                if (m.what == SDConsts.Msg.RFMsg && m.arg1 == SDConsts.RFCmdMsg.READ) {
                    PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, data);
                    pluginResult.setKeepCallback(true);
                    _readCallback.sendPluginResult(pluginResult);
                }
            }
        }
    };
}

