import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'stack_exchange_plugin_method_channel.dart';

abstract class StackExchangePluginPlatform extends PlatformInterface {
  /// Constructs a StackExchangePluginPlatform.
  StackExchangePluginPlatform() : super(token: _token);

  static final Object _token = Object();

  static StackExchangePluginPlatform _instance = MethodChannelStackExchangePlugin();

  /// The default instance of [StackExchangePluginPlatform] to use.
  ///
  /// Defaults to [MethodChannelStackExchangePlugin].
  static StackExchangePluginPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [StackExchangePluginPlatform] when
  /// they register themselves.
  static set instance(StackExchangePluginPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
