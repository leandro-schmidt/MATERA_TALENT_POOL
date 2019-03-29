package com.matera.restserver.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;


public class Messages {

    private static ResourceBundle bundle;
    
    private static final Logger LOGGER = Logger.getLogger( Messages.class.getName() );

    static {
        try {
            bundle = ResourceBundle.getBundle("messages");
        } catch (Exception e) {
        	LOGGER.severe(e.getMessage());
        }
    }
    
    //SonarLint made me do it again...
    private Messages() {
    	
    }
    
    /**
     * Gets the message of the given key
     * @param key Key of the message.
     * @return
     */
    public static String getMessage(String key) {
        try {
            if (bundle != null) {
                return bundle.getString(key);
            }
        } catch (MissingResourceException e) {
        	LOGGER.severe(e.getMessage());
        }
        return null;
    }
    
    /**
     * Gets the message of the given key with parameter.
     * @param key Key of the message.
     * @param arg The parameter to be on the message.
     * @return
     */
    public static final String getMessage(String key, final Object arg) {
        try {
            if (bundle != null) {
                return MessageFormat.format(bundle.getString(key), arg);
            }
        } catch (MissingResourceException e) {
        	LOGGER.severe(e.getMessage());
        }
        return null;
    }

    /**
     * Gets the message of the given key with parameters.
     * @param key Key of the message.
     * @param args The parameters to be on the message.
     * @return
     */
    public static final String getMessage(String key, final Object[] args) {
        try {
            if (bundle != null) {
                return MessageFormat.format(bundle.getString(key), args);
            }
        } catch (MissingResourceException e) {
        	LOGGER.severe(e.getMessage());
        }
        return null;
    }

}