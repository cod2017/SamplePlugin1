/**
 */
package com.example;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import java.util.Date;

import android.app.AlertDialog;

import android.app.AlertDialog.Builder;

import android.content.DialogInterface;

import org.json.JSONArray;

import org.json.JSONException;

import org.json.JSONObject;
import android.content.ContentResolver;

public class MyCordovaPlugin extends CordovaPlugin {
  private static final String TAG = "MyCordovaPlugin";
  private synchronized void alert(final String title,

  final String message,

  final String buttonLabel,

  final CallbackContext callbackContext) {

  new AlertDialog.Builder(cordova.getActivity())

  .setTitle(title)

  .setMessage(message)

  .setCancelable(false)

  .setNeutralButton(buttonLabel, new AlertDialog.OnClickListener() {

    public void onClick(DialogInterface dialogInterface, int which) {

      dialogInterface.dismiss();

      callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, 0));

    }

  })

  .create()

  .show();

}


  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);

    Log.d(TAG, "Initializing MyCordovaPlugin");
  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    if(action.equals("echo")) {
      String phrase = args.getString(0);
      // Echo back the first argument
      Log.d(TAG, phrase);
    } else if(action.equals("getDate")) {
      // An example of returning data back to the web layer
      final PluginResult result = new PluginResult(PluginResult.Status.OK, (new Date()).toString());
      callbackContext.sendPluginResult(result);
    }
      else if (action.equals("alert")) {

      alert(args.getString(0), args.getString(1), args.getString(2), callbackContext);

      return true;

    }

      else if (action.equals("checkAutoSync")) {

      // alert(args.getString(0), args.getString(1), args.getString(2), callbackContext);
      boolean temp=isAutoSync();
      final PluginResult result = new PluginResult(PluginResult.Status.OK, temp);
      callbackContext.sendPluginResult(result);

      return true;

    }
      else if (action.equals("setAutoSync")) {

      // alert(args.getString(0), args.getString(1), args.getString(2), callbackContext);
     synAccount();
      final PluginResult result = new PluginResult(PluginResult.Status.OK, "Success");
      callbackContext.sendPluginResult(result);

      return true;

    }
    return true;
  }
    private boolean isAutoSync() {

return ContentResolver.getMasterSyncAutomatically();

//ContentResolver.setSyncAutomatically(account, authority, true/false);

}
private void synAccount(){
ContentResolver.setMasterSyncAutomatically(true);

}
 
}
