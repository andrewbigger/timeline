---
title: "Uninstall"
weight: 1
draft: false
# search related keywords
keywords: ["timeline", "troubleshooting", "uninstall"]
accent: green
---

Uninstalling timeline can be achieved by removing the app and its library files. This is necessary if you wish to remove the app completely from your machine

Below are the instructions for each supported OS:

## MacOS

To remove the app first drag the timeline app icon into the trash like you would with any other application. This will not fully remove the application as you'll need to also remove the application binaries.

Please note that application binaries will remain on your machine, to remove these, in the Finder:

1. Select `Go > Go to Folder` or press `Cmd + Shift + G`
2. Enter `~/Library/Application Support` and press Enter
3. Locate the `timeline` folder and move that into the trash

And that's it, timeline is completely removed.

## Windows

The timeline application can be removed from the Add Programs or Features Control Panel. Locate the timeline Application in the list and select Uninstall to remove the application from your system.

Please note that application binaries will remain on your machine after the uninstall. To remove these:

1. Select `Start > Run` or press `Windows + Shift + R`.
2. Enter `%APPDATA%` into the Run prompt
3. Locate the `timeline` folder, and send the folder to the recycle bin.

## Recovery from bad update

Recovery from a bad update involves a similar process to uninstallation, if you need to do this see: [Recover From Dodgy Update](/timeline/troubleshooting/recover_from_dodgy_update).
