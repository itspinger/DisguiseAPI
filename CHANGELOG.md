# Changelog
All notable releases after **1.2.0** will be mentioned here, along with the information
regarding the given release.

## 1.2.0 - (Date: 2023-04-10)
This is a large update which fortunately doesn't break backwards compatibility.
You should still follow this changelog to see how you should adjust to it.

### Additions
- Added registry system to support running DisguiseAPI with multiple Disguise plugins on the same server.
Plugins can either use the default registration `RegistrySystem.DEFAULT_REGISTRATION` or create their own registration by extending the `DisguiseRegistration` class.
- Added DisguiseProvider class which is used for changing skins / names. 
To obtain an instance of the class you can either call `DisguiseAPI.createProvider(DisguiseRegistration)` which uses the registration mentioned above.
Or you can use `DisguiseAPI.getDefaultProvider()` to get the provider with the default registration.
- Added and changed packets in all modules which weren't updating health scales.

### Changes
- Deprecated the `NameFactory` interface, which now uses the `DisguiseProvider` by default. 
It is scheduled for removal in one of the future updates.
- Deprecated the `DisguiseAPI.getProvider()` method, as users can easily manipulate skins
with the new `DisguiseProvider` interface. It is scheduled for removal in one of the future updates.
- Removed any custom event classes that were registered within this plugin