Sample Template Ionic/Cordova

Android
-------


1. getDate

callbackSuccess=function(result){
alert(result);
}

MyCordovaPlugin.getDate(callbackSuccess);

2. checkAutoSync

callbackSuccess=function(result){
alert(result);
}

MyCordovaPlugin.checkAutoSync(callbackSuccess);

3. setAutoSync

callbackSuccess=function(result){
alert(result);
}

MyCordovaPlugin.setAutoSync(callbackSuccess);

Permisssion Required
--------------------------

<uses-permission android:name="android.permission.WRITE_SYNC_SETTINGS" />
<uses-permission android:name="android.permission.READ_SYNC_SETTINGS" />

iOS
--------

1. getDate

callbackSuccess=function(result){
alert(result);
}

MyCordovaPlugin.getDate(callbackSuccess);


