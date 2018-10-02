package fr.printoid.phones;

import fr.printoid.phones.ServerModel;

/**
 * Interface to communicate with Printoid from another process.
 */
interface IPrintoidCommunicationInterface {

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

}