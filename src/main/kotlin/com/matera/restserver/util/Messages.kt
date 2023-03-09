package com.matera.restserver.util

import ch.qos.logback.core.CoreConstants.EMPTY_STRING
import lombok.Generated
import org.slf4j.LoggerFactory
import java.util.MissingResourceException
import java.util.ResourceBundle

@Suppress
@Generated
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

    fun getMessage(key: String): String =
        try {
            if (bundle != null) bundle!!.getString(key) else EMPTY_STRING
        } catch (e: MissingResourceException) {
            logger.error(e.message)
            EMPTY_STRING
        }
}
