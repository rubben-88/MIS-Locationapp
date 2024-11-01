package lt.ktu.locationapp.domain.models


data class Observation(
    val xy: Location,
    val ss: SignalTriplet
) {
    override fun toString(): String {
        return "${xy.x} ${xy.y} ${ss.get(0)} ${ss.get(1)} ${ss.get(2)}"
    }

}

fun observationFromString(s: String): Observation {
    val arr = s.split(" ")
    return Observation(
        xy = Location(arr[0].toInt(), arr[1].toInt()),
        ss = SignalTriplet(arrayOf(
            arr[2].toInt(),
            arr[3].toInt(),
            arr[4].toInt()
        ))
    )
}