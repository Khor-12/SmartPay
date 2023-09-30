package com.khor.smartpay.core.util

class CardHash(private val details: String) {
    override fun hashCode(): Int {
        return details.hashCode() * 19
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }
}