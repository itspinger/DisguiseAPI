<p align="center">
	<img src = "https://img.shields.io/badge/Supports-1.8.8%20--%201.19.2-%3Cbrightgreen%3E">
	<img src = "https://img.shields.io/badge/-Library-blue">
	<img src = "https://img.shields.io/badge/-Easy%20to%20use-orange">
</p>

# DisguiseAPI

Disguise is an open-source library which solves manipulating with player skins in Minecraft using NMS.
<br>
The standard Spigot implementation does not provide support for changing skins, so using this API
<br>
you can easily control player skins.

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
  <version>1.1.2</version> <!-- At time of writing, 1.1.0 is the latest version. See the pom.xml for the latest version -->
  <scope>provided</scope> <!-- No need for compiling it within the jar since it is already included within the plugin -->
</dependency>
```

#### Gradle
```gradle
dependencies {
    // No need for compiling it within the jar since it is already included within the plugin
    compileOnly 'net.pinger.disguise:API:1.1.2'
}
```

### Documentation

- <a href = "https://itspinger.github.io/DisguiseAPI/">Javadoc:</a> Documentation for the current release 
- <a href = "https://github.com/itspinger/DisguiseAPI/wiki">User guide:</a> A well written user guide on how to use this library

### License 

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

### Plugins

Down below plugins implementing this library will be mentioned, along with the download link. If you want your plugin to be
included in this list, write me a message on Pinger#5246 (Discord)

- <a href = "https://www.spigotmc.org/resources/disguise.84079/">DisguisePlus</a>
