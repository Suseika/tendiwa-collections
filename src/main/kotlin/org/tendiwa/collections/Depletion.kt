package org.tendiwa.collections

import java.util.*

fun <T> Iterable<T>.untilDepleted(operation: Depletion<T>.(item: T) -> Unit) {
    val depletion = Depletion<T>()
    for (item in this) {
        if (!depletion.isUsed(item)) {
            depletion.markUsed(item)
            depletion.operation(item)
        }
    }
}

class Depletion<T> internal constructor() {
    private val used = HashSet<T>()

    fun markUsed(item: T) {
        used.add(item)
    }

    internal fun isUsed(item: T): Boolean =
        used.contains(item)
}
