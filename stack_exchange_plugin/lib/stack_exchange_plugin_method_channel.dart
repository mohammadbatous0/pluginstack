import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'stack_exchange_plugin_platform_interface.dart';

/// An implementation of [StackExchangePluginPlatform] that uses method channels.
class MethodChannelStackExchangePlugin extends StackExchangePluginPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('stack_exchange_plugin');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
