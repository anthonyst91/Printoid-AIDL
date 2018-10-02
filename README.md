# Printoid Interface

This project is made as an example of interface with the Android app "Printoid for OctoPrint":

[https://printoid.net](https://printoid.net)

With the interface (as an AIDL file), you will be able to communicate with Printoid from your own application.

## Pre-requisited

Printoid is compatible with **API level 14 and +** (Ice Cream Sandwish)

## How does it work

Include the .AIDL and .kt files from the **Interface** folder in your project. Do not change the hierarchy or the package names.

## How to connect to the service

Please see example in the **DemoApp**. 

Full guidelines and good practices are available here:
[https://developer.android.com/guide/components/aidl](https://developer.android.com/guide/components/aidl)

## Who can get connected

For the moment, only the MyMiniFactory application can connect to Printoid using these interfaces (package name: MyMiniFactory)

I will probably open these interfaces to everyone in the future if needed.

## What are the available interfaces 

For the moment, only the following interfaces are available. Please note that these interfaces will evolve in the future (but backward compatibiltiy will be kept, of course.) 


    /**
     * Returns the list of configured OctoPrint servers
     * in Printoid.
     */
    List<ServerModel> getConfiguredServers();

    /**
     * Send the passed GCODE file to the app for upload.
     * This method will start Printoid with the profile selector screen.
     *
     * @param pathToTheFile The path to the file from the storage
     */
    void sendGcodeFromFile(String pathToTheFile);

    /**
     * Send the passed GCODE file to the app for upload to the
     * passed server, using its id.
     *
     * @param pathToTheFile The path to the file from the storage
     * @param serverId The server ID targeted by the upload
     */
    void sendGcodeFromFileToServer(String pathToTheFile, String serverId);

    /**
     * Send the passed GCODE to the app for upload.
     * This method will start Printoid with the profile selector screen.
     *
     * @param fileUrl The URL to the file
     */
    void sendGcodeFromUrl(String fileUrl);

    /**
     * Send the passed GCODE to the app for upload to the
     * passed server, using its id.
     *
     * @param fileUrl The URL to the file
     * @param serverId The server ID targeted by the upload
     */
    void sendGcodeFromUrlToServer(String fileUrl, String serverId);



