package lt.ktu.locationapp.utils

import android.util.Log
import kotlinx.serialization.json.Json
import lt.ktu.locationapp.domain.models.Location

fun toInteger(s: String): Int? {
    return try {
        s.toInt()
    } catch (e: NumberFormatException) {
        null
    }
}

fun toMAC(s: String): String? {
    Log.d("test", "toMac(${s})")
    val macRegex = Regex("^([0-9A-Fa-f]{2}:){5}([0-9A-Fa-f]{2})$")
    return if (macRegex.matches(s)) {
        Log.d("test", "mac ok")
        s
    } else {
        Log.d("test", "mac not ok")
        null
    }
}

var integerCounter = 0
fun newInteger(): Int {
    integerCounter++
    return integerCounter
}

fun observationEditRouteToTitle(route: String): String {
    val components = route.split("/")
    val id = Json.decodeFromString<Location>(components[1])
    return "Edit $id"
}