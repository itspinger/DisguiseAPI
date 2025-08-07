<p align="center">
	<img src = "https://img.shields.io/badge/Supports-1.8.8%20--%201.21.4-%3Cbrightgreen%3E">
	<img src = "https://img.shields.io/badge/Added%20Up%20To-1.21.7-yellow">
	<img src = "https://img.shields.io/badge/-Library-blue">
	<img src = "https://img.shields.io/badge/-Easy%20to%20use-orange">
</p>

<img src="https://imgur.com/xCRKtdh.png" width="120px" align="right"></img>
# DisguiseAPI
Disguise is an open-source library which manipulates with player skins in Minecraft using NMS.
<br>
The standard Spigot implementation does not provide support for changing skins, so using this API
you can easily control player skins.

### Update
This part is mainly about what has been updated for the support up to 1.21.7
Following things that are updated or edited out:

Added 1.21.4-1.21.7 with almost identical files, just changing a few stuff like pom.xml, imports and texts containing "1.21.#"

Plugin/pom.xml has been updated to include Providers for 1.21.4-1.21.7
Plugin/src/main/java/net/pinger/disguise/packet/PacketManagerImpl.java registeredProviders has been updated to contain 1.21.4-1.21.7

API/pom.xml has been updated with an updated spigot version, Java version stays the same.
API/src/main/java/net/pinger/disguise/item/XMaterial.java Potion part has been updated to support 1.8 and 1.9+ potion types. If the code broke stuff, you can edit it out. Main things were to make a safer way of fallbacking to 1.8 potions from 1.9+ potions when the server is in that specified version.

DisguiseAPI/pom.xml has been updated to include the needed modules.

I'm sorry if this might be a bad explanation of what I updated as this is my first ever pull request and successful github repo. I hope that this would help getting support up to 1.21.7

### Dependency 

To install this repository, you should follow the next steps:

1. Clone this repository: ``git clone https://github.com/ITSPINGER/DisguiseAPI.git``
2. Enter into the directory folder: ``cd DisguiseAPI``
3. Build the project using Maven: ``mvn clean install``

After the project has finished building, you may now use the project in your projects.

#### Maven
```xml
<dependency>
  <groupId>net.pinger.disguise</groupId>
  <artifactId>API</artifactId>
  <version>1.4.0</version> <!-- At time of writing, 1.4.0 is the latest version. See the pom.xml for the latest version -->
  <scope>provided</scope> <!-- No need for compiling it within the jar since it is already included within the plugin -->
</dependency>
```

#### Gradle
```gradle
dependencies {
    // No need for compiling it within the jar since it is already included within the plugin
    compileOnly 'net.pinger.disguise:API:1.4.0'
}
```

### Documentation

- <a href = "https://itspinger.github.io/DisguiseAPI/">Javadoc:</a> Documentation for the current release 
- <a href = "https://github.com/itspinger/DisguiseAPI/blob/master/CHANGELOG.md">Changelog:</a> Information about the latest updates
- <a href = "https://github.com/itspinger/DisguiseAPI/wiki">User guide:</a> A well written user guide on how to use this library

### License 

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

### Plugins

Down below plugins implementing this library will be mentioned, along with the download link. If you want your plugin to be
included in this list, write me a message on Pinger#5246 (Discord)

- <a href = "https://www.spigotmc.org/resources/disguise.84079/">DisguisePlus</a>
- <a href = "">Hynick</a>
