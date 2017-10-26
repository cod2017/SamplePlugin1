cordova.define("my-cordova-plugin.plugin", function(require, exports, module) {

var exec = require('cordova/exec');

var PLUGIN_NAME = 'MyCordovaPlugin';

var MyCordovaPlugin = {
  echo: function(phrase, cb) {
    exec(cb, null, PLUGIN_NAME, 'echo', [phrase]);
  },
  getDate: function(cb) {
    exec(cb, null, PLUGIN_NAME, 'getDate', []);
  },
     alert: function(title, message, buttonLabel, successCallback) {

    	 cordova.exec(successCallback,

                 null, // No failure callback

                 PLUGIN_NAME,

                 "alert",

                 [title, message, buttonLabel]);

  },

    checkAutoSync:function(successCallback) {

    	 cordova.exec(successCallback,

                 null, // No failure callback

                 PLUGIN_NAME,

                 "checkAutoSync",

                 []);

  },
  setAutoSync:function(successCallback) {

    	 cordova.exec(successCallback,

                 null, // No failure callback

                 PLUGIN_NAME,

                 "setAutoSync",

                 []);

  }
};

module.exports = MyCordovaPlugin;

});
