package lt.ktu.locationapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class SignalTriplet(
    private val value: Array<Int>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SignalTriplet

        return value.contentEquals(other.value)
    }

    override fun hashCode(): Int {
        return value.contentHashCode()
    }

    fun get(index: Int): Int = value[index]
}