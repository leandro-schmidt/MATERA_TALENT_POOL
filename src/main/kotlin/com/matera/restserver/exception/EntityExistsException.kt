package com.matera.restserver.exception

import com.matera.restserver.util.Messages.getMessage

class EntityExistsException : Exception {
    constructor(msg: String?) : super(msg) {}
    constructor() : super(getMessage(DEFAULT_MESSAGE)) {}

    companion object {
        private const val DEFAULT_MESSAGE = "exceptions.default.entityexistsexception"
        private const val serialVersionUID = -4802286605958048986L
    }

}