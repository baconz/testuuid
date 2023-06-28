package com.baconz.testuuid

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform