package com.example.stack_exchange_plugin

import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** StackExchangePlugin */
class StackExchangePlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "stack_exchange_plugin")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else {
      result.notImplemented()
    }
  }
private fun fetchQuestions(result: Result) {

  CoroutineScope(Dispatchers.IO).launch {

    val url = URL("https://api.stackexchange.com/2.3/questions?order=desc&sort=activity&site=stackoverflow")

    val connection = url.openConnection() as HttpURLConnection

    try {
    
      connection.requestMethod = "GET"

      val responseCode = connection.responseCode

      if(responseCode == HttpURLConnection.HTTP_OK) {

        withContext(Dispatchers.Main) {
          result.success("Successfully fetched questions")
        }

      } else {

        withContext(Dispatchers.Main) {
          result.error("HTTP Error", "Failed to fetch questions", null)
        }

      }

    } catch (e: Exception) {

      withContext(Dispatchers.Main) {
        result.error("Exception", "Failed to fetch questions", e.localizedMessage)  
      }

    } finally {
      connection.disconnect()
    }

  }

}
  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}