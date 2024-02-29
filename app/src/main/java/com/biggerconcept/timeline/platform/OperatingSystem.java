package com.biggerconcept.timeline.platform;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Methods for gathering information about the OS and interacting with it.
 * 
 * @author Andrew Bigger
 */
public class OperatingSystem {
    /**
    * Returns the name of the operating system executing the application.
    * 
    * @return 
    */
    public static String getName() {
        return System.getProperty("os.name").toLowerCase();
    }

    /**
    * Returns true if the platform is mac os.
    * 
    * @return 
    */
    public static boolean isMac() {
        return getName().startsWith("mac os"); 
    }

    /**
    * Returns true if the platform is windows.
    * 
    * @return
    */
    public static boolean isWindows() {
        return getName().startsWith("windows");
    }
  
     /**
     * Opens a URL in the system default browser.
     * 
     * The browse command requires a URI, so the given url string is converted
     * into a URL and then a URI.
     * 
     * @param url
     * @throws URISyntaxException
     * @throws MalformedURLException
     * @throws IOException 
     */
    public static void goToUrl(String url)
        throws URISyntaxException, MalformedURLException, IOException {
        
        Desktop.getDesktop().browse(new URL(url).toURI());
    }
  
}
