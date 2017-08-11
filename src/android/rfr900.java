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


public class rfr900 extends CordovaPlugin {
    private CallbackContext _eventCallback;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        PluginResult pluginResult = null;
        pluginResult = new PluginResult(PluginResult.Status.OK, "HELLO WORLD");
        callbackContext.sendPluginResult(pluginResult);
        return true;
    }
}