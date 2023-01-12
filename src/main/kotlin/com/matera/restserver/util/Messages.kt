package com.matera.restserver.util

import java.text.MessageFormat
import java.util.*
import java.util.logging.Logger

object Messages {
    private var bundle: ResourceBundle? = null
    private val LOGGER = Logger.getLogger(Messages::class.java.name)

    init {
        try {
            bundle = ResourceBundle.getBundle("messages")
        } catch (e: Exception) {
            LOGGER.severe(e.message)
        }
    }

    /**
     * Gets the message of the given key
     * @param key Key of the message.
     * @return
     */
    fun getMessage(key: String?): String? {
        try {
            if (bundle != null) {
                return bundle!!.getString(key)
            }
        } catch (e: MissingResourceException) {
            LOGGER.severe(e.message)
        }
        return null
    }

    /**
     * Gets the message of the given key with parameter.
     * @param key Key of the message.
     * @param arg The parameter to be on the message.
     * @return
     */
    fun getMessage(key: String?, arg: Any?): String? {
        try {
            if (bundle != null) {
                return MessageFormat.format(bundle!!.getString(key), arg)
            }
        } catch (e: MissingResourceException) {
            LOGGER.severe(e.message)
        }
        return null
    }

    /**
     * Gets the message of the given key with parameters.
     * @param key Key of the message.
     * @param args The parameters to be on the message.
     * @return
     */
    fun getMessage(key: String?, args: Array<Any?>): String? {
        try {
            if (bundle != null) {
                return MessageFormat.format(bundle!!.getString(key), *args)
            }
        } catch (e: MissingResourceException) {
            LOGGER.severe(e.message)
        }
        return null
    }
}