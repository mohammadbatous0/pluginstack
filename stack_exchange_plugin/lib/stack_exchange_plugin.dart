
import 'stack_exchange_plugin_platform_interface.dart';

class StackExchangePlugin {
  Future<String?> getPlatformVersion() {
    return StackExchangePluginPlatform.instance.getPlatformVersion();
  }
}
