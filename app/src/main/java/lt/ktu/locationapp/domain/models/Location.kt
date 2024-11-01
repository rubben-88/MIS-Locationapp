package lt.ktu.locationapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val x: Int,
    val y: Int,
): Comparable<Location> {

    override fun equals(other: Any?): Boolean {
        if (other !is Location) return false
        return this.x == other.x && this.y == other.y
    }

    override fun compareTo(other: Location): Int {
        return when {
            this.x < other.x -> -1
            this.x > other.x -> 1
            else -> {
                when {
                    this.y < other.y -> -1
                    this.y > other.y -> 1
                    else -> 0
                }
            }
        }
    }

    override fun hashCode(): Int {
        // Szudzik’s Pairing Function
        return if (x >= y) {
            x * x + y
        } else {
            y * y + x
        }
    }

    override fun toString(): String = "($x,$y)"

}