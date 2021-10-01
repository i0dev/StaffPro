# StaffPro Plugin!

This plugin allows you to take control of your server staff wise, and give your staff team tools to use to moderate your server!

This is a PLUGIN, and it goes into your `/plugins/` folder on your server. It will not work if It's elsewhere!

### How it works:

The main feature of StaffPro is ModMode, which staff can access through `/modmode`, which puts you into limited creative mode that has different
options in your inventory to do certain things. Some of these things include: Vanish, Freeze, Examine, Random TP. While in mod-mode you can get
some custom gear that is fully customizable! Everything in this plugin is configurable.

### Dependencies

- Vault
- NBTAPI

### Works With

- PlaceholderAPI
- MassiveCraft Factions

### Commands:

*Values in **<>** are required, values in **[]** are optional*

- ``/staffpro reload`` - ``Reloads the plugin.``
- ``/staffpro help`` - ``Sends a list of commands.``
- `/vanish [player]` - `Vanishes youreself or the target player.`
- `/unvanish [player] ` - `Un-Vanishes yourself or the target player.`
- `/rtp` - `Randomly teleport to a real player.`
- `/strip <player>` - `Strips the gear off their body.`
- `/freeze <player>` - `Freezes the player.`
- `/unfreeze <player>` - `Un-Freezes the player.`


### Permissions:

###### General
- ```staffpro.reload.cmd```   - ``Gives permissions to use /staffpro reload.``
- ```staffpro.help.cmd```   - ``Gives permissions to use /staffpro help.``
###### Vanish
- `staffpro.vanish.cmd` - `Gives permission to use /vanish.`
- `staffpro.vanish.modmode` - `Gives permission the vanish item in modmode.`
- `staffpro.unvanish.cmd` - `Gives permission to use /unvanish.`
- `staffpro.unvanish.modmode` - `Gives permission the unvanish item in modmode.`
- `staffpro.vanish.login` - `If you have this permission, you will be able to log online and be automatically vanished.`
###### RTP
- `staffpro.rtp.bypass` - `If you have this permission, you will bypass being randomly teleported to.`
- `staffpro.rtp.cmd` - `Gives permission to use /rtp.`
- `staffpro.rtp.modmode` - `Gives permission the rtp item in modmode.`
###### Strip
- `staffpro.strip.cmd` - `Gives permission to use /strip.`
###### Freeze
- `staffpro.freeze.cmd` - `Gives permission to use /freeze.`
- `staffpro.unfreeze.cmd` - `Gives permission to use /unfreeze.`
- `staffpro.freeze.bypass` - `Bypasses being able to be frozen.`
- `staffpro.freeze.modmode` - `Gives permission the freeze item in modmode.`
- `staffpro.unfreeze.modmode` - `Gives permission the unfreeze item in modmode.`
###### Examine
- `staffpro.examine.cmd` - `Gives permission to use /examine.`
- `staffpro.examine.bypass` - `Bypasses being able to be examined.`
- `staffpro.examine.takeitem` - `Able to take items from the examine inventory.`
- `staffpro.examine.modmode` - `Gives permission the examine item in modmode.`
###### Combat List
- `staffpro.combatlist.cmd` - `Gives permission to use /combatList.`
- `staffpro.combatlist.modmode` - `Gives permission the combat list item in modmode.`
###### ModMode
- `staffpro.modmode.cmd` - `Gives permission to use /modmode.`
- `staffpro.modmode.login` - `If you have this permission you will be able to login and automatically be put in modmode.`

### Need help?

Join the [Support Server](https://discord.i0dev.com/) and feel free to ask any questions. You can talk to me directly
too, My discord is i0#0001
