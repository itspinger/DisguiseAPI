# DisguiseAPI

Disguise is an open-source library which solves manipulating with player skins in Minecraft using NMS.
<br>
The standard Bukkit/Spigot implementation does not provide support for changing skins, so using this API
<br>
you can easily control player skins.

The goal for this plugin is to handle all versions in range from: **1.7 - LATEST (1.19 now)**.
Currently, it has handlers for `1.8.8, 1.9.4, 1.10 releases, 1.11 releases, 1.12 series, 1.13 series, 1.14 series, 1.15 series, 1.16 series, 1.17 series and 1.18 series`

# Example Usage
```java
public class ExamplePlugin extends JavaPlugin {
	
	@Override
	public void onEnable() {
		// DisguiseAPI.registerProvider(Provider.class); // Register provider here if you have any special providers
        // After this you want to apply the provider
        PacketProvider provider = DisguiseAPI.applyProvider();
		
		// Check if the provider is null and if so, it is advised to disable the plugin
        if (provider == null) {
			this.getPluginLoader().disablePlugin(this);
			return;
		}
		
		// Do stuff here
	}
}
 ```

# License 
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
