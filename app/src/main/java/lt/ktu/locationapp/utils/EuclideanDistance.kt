package lt.ktu.locationapp.utils

import lt.ktu.locationapp.domain.models.Location
import lt.ktu.locationapp.domain.models.Observation
import lt.ktu.locationapp.domain.models.SignalTriplet
import kotlin.math.pow
import kotlin.math.sqrt

fun bestFittingLocation(
    observationsList: List<Observation>,
    selfSignals: SignalTriplet
): Location {

    var bestLocation = Location(0,0)
    var bestEuclDist: Double = Double.MAX_VALUE

    for (obs in observationsList) {
        val euclDist: Double = euclideanDistance(obs.ss, selfSignals)
        if (euclDist < bestEuclDist) {
            bestLocation = obs.xy
            bestEuclDist = euclDist
        }
    }

    return bestLocation
}

fun euclideanDistance(ss1: SignalTriplet, ss2: SignalTriplet): Double {
    var sum = 0.0
    for (i in 0..2) {
        sum += (ss1.get(i) - ss2.get(i)).toDouble().pow(2)
    }
    return sqrt(sum)
}