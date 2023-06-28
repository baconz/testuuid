package com.baconz.testuuid

import com.benasher44.uuid.uuid4

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}! (${uuid4()})"
    }
}