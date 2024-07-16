package com.example.stack_exchange_plugin

import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class StackExchangePlugin : FlutterPlugin, MethodCallHandler {
    private lateinit var channel: MethodChannel

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "stack_exchange_plugin")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if (call.method == "fetchQuestions") {
            fetchQuestions(result)
        } else {
            result.notImplemented()
        }
    }

    private fun fetchQuestions(result: Result) {
        CoroutineScope(Dispatchers.IO).launch {
            val url = URL("https://api.stackexchange.com/2.3/questions?order=desc&sort=activity&site=stackoverflow")
            val connection = url.openConnection() as? HttpURLConnection
            if (connection != null) {
                try {
                    connection.requestMethod = "GET"
                    val responseCode = connection.responseCode
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        withContext(Dispatchers.Main) {
                            result.success("Successfully fetched questions")
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            result.error("HTTP_ERROR", "Failed to fetch questions", null)
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        result.error("EXCEPTION", "Failed to fetch questions", e.localizedMessage)
                    }
                } finally {
                    connection.disconnect()
                }
            } else {
                withContext(Dispatchers.Main) {
                    result.error("INVALID_CONNECTION", "Failed to create a valid HttpURLConnection", null)
                }
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
