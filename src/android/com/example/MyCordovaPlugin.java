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

import android.R.string;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class MyCordovaPlugin extends CordovaPlugin {
  private static final String TAG = "MyCordovaPlugin";
  	AccountManager manager=null;
	String authtoken=null;
	// Naive int to account mapping so our JS can simply reference native objects
	Integer accumulator=0;
	Account[] availableAccounts;
	HashMap<Integer,
	Account>accounts=new HashMap<Integer,
	Account>();
	Account tempAccount;
	public static final String AUTH_TOKEN_TYPE="Bearer";
	CallbackContext callbackContext;


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
	private Integer indexForAccount(Account account)
	{
		for(Entry<Integer, Account> e: accounts.entrySet())
		{
			if(e.getValue().equals(account))
			{
				return e.getKey();
			}
		}

		accounts.put(accumulator, account);
		return accumulator++;
	}
  private String getExistingAccountAuthToken(Account account,  final String authTokenType,final String name,final CallbackContext callbackContext) {
      alert("title", "getExistingAccountAuthToken", "OK", callbackContext);
    //   	if(manager == null)
		// {
		// 	manager = AccountManager.get(cordova.getActivity());
    // }     
     alert("title", account.toString(), "OK", callbackContext);
    alert("title", authTokenType.toString(), "OK", callbackContext);
     alert("title", name.toString(), "OK", callbackContext);

    final AccountManagerFuture<Bundle> future = manager.getAuthToken(account, authTokenType, false, null, null);
  alert("title", future.toString(), "OK", callbackContext);
     new Thread(new Runnable() {
      @Override
      public void run() {
        try {
                alert("title", "message", "OK", callbackContext);

            Bundle bnd = future.getResult();
            authtoken = bnd.getString(AccountManager.KEY_AUTHTOKEN);

                            alert("title", authtoken, "OK", callbackContext);

          JSONArray result = new JSONArray();
          JSONObject account_object = new JSONObject();
          account_object.put("token", authtoken);
          account_object.put("name", name);
					account_object.put("type", authTokenType);
          result.put(account_object);



        callbackContext.success(result);
        } catch (Exception e) {
          e.printStackTrace();
		  
          // showMessage(e.getMessage());
        }
      }
    }).start();
return authtoken;


  }


  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);

    Log.d(TAG, "Initializing MyCordovaPlugin");
  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
      this.callbackContext=callbackContext;
		if(manager == null)
		{
			manager = AccountManager.get(cordova.getActivity());
		}
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
    	else if("getAccountsByType".equals(action))
			{
				 availableAccounts = manager.getAccountsByType(args.isNull(0)? null : args.getString(0));

				JSONArray result = new JSONArray();
				if (availableAccounts.length==0) {
					  callbackContext.success("result");
				
				} else {
				   for (Account account: availableAccounts) {
				    Integer index = indexForAccount(account);
				    String accessToken = getExistingAccountAuthToken(account, AUTH_TOKEN_TYPE, account.name,callbackContext);

				    
				//              JSONObject account_object = new JSONObject();
				//     					account_object.put("_index", (int)index);
				//     					account_object.put("name", account.name);
				//     					account_object.put("type", account.type);
				//              account_object.put("token", "test");
        //               result.put(account_object);



        // callbackContext.success(result);
				    //
				    //          result.put(account_object);
				    //					this.tempAccount=account;
				  }

				}


		

				//callbackContext.success(result);
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
