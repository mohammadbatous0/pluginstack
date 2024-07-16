import 'package:flutter_test/flutter_test.dart';
import 'package:stack_exchange_plugin/stack_exchange_plugin.dart';
import 'package:stack_exchange_plugin/stack_exchange_plugin_platform_interface.dart';
import 'package:stack_exchange_plugin/stack_exchange_plugin_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockStackExchangePluginPlatform
    with MockPlatformInterfaceMixin
    implements StackExchangePluginPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final StackExchangePluginPlatform initialPlatform = StackExchangePluginPlatform.instance;

  test('$MethodChannelStackExchangePlugin is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelStackExchangePlugin>());
  });

  test('getPlatformVersion', () async {
    StackExchangePlugin stackExchangePlugin = StackExchangePlugin();
    MockStackExchangePluginPlatform fakePlatform = MockStackExchangePluginPlatform();
    StackExchangePluginPlatform.instance = fakePlatform;

    expect(await stackExchangePlugin.getPlatformVersion(), '42');
  });
}
