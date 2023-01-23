package com.matera.restserver.util

import org.slf4j.LoggerFactory
import java.text.MessageFormat
import java.util.MissingResourceException
import java.util.ResourceBundle

@Suppress
object Messages {
    private var bundle: ResourceBundle? = null
    private val logger = LoggerFactory.getLogger(Messages::class.java)

    init {
        try {
            bundle = ResourceBundle.getBundle("messages")
        } catch (e: java.lang.NullPointerException) {
            logger.error(e.message)
        } catch (e1: MissingResourceException) {
            logger.error(e1.message)
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
            logger.error(e.message)
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
            logger.error(e.message)
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
                return MessageFormat.format(bundle!!.getString(key), args.toString())
            }
        } catch (e: MissingResourceException) {
            logger.error(e.message)
        }
        return null
    }
}
