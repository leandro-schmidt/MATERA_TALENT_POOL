package com.matera.restserver.exception

import com.matera.restserver.util.Messages.getMessage
import lombok.Generated

@Generated
class EntityNotFoundException : Exception {
    constructor(msg: String?) : super(msg)
    constructor() : super(getMessage(DEFAULT_MESSAGE))

    companion object {
        private const val DEFAULT_MESSAGE = "exceptions.default.entitynotfoundexception"
        private const val serialVersionUID = 8032193805676955555L
    }
}
