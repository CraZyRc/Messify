# Messify <img width="500" height="500" alt="Premium (1)" src="https://github.com/user-attachments/assets/eaba93ca-8093-4201-8166-41b5fb5f944e" />

Messify is a brand new broadcasting plugin for minecraft
This plugin can be used to broadcast messages to each and every player on your server.
With personalized messages depending on group permissions
Soft-dependend on [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245) for infinite placeholders.

## Bugs or Feature requests?
Please visit the [Issues](https://github.com/CraZyRc/Messify/issues) tab to request new features or to report bugs.
Whenerver submitting a new request, please be as specific as possible. 
Have a bug? Then please submit your error log and describe how you got that bug/error to happen.
Want a new feature? Then please try and be specific in what you like, descrive how you see this feature happening.
We offer additional support on our [Discord server](https://discord.gg/RNhtzvsBGP), go to: WAM Help and asdk your questions :) 


## Features
* Automated Broadcasting service with highly customizable messages
* Per-group permissions
* Compatible with PlaceholderAPI for more customization
* Create your own placeholders in the config
* Randomized messages
* In-game editing for groups and messages
* Broadcast command allowing you to instantly broadcast to the whole server
* Messages in String and Json format, allowing for basic and advanced messages.


## Installation
Drop the latest plugin compatible with your minecraft version into the plugins folder of your server and that's it. as simple as that.
To include the placeholders from PlaceholderAPI, download and install the PlaceholderAPI plugin.

## Commands
* `/messify raw`
  Sends a message in either Json format or String format to the whole server.
* `/messify start`
  Starts the broadcasting service.
* `/messify stop`
  Stops the broadcasting service.
* `/messify reload`
  Reloads the plugin
* `/messify group create <groupName>`
  Creates a new group
* `/messify group add <groupName> <message>`
  Add a new message to a group
* `/messify group list {groupName}`
  Lists all of the groups, or lists all the messages of a group when the groupname is added as argument
* `/messify group remove <groupName> {message}`
  Remove a message or entire group, depending if you add the message as argument

Please enjoy this light plugin and please let me know if you have any ideas to add or if you find any bugs.
