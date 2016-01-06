package org.tendiwa.collections

import java.util.*

fun <T> List<T>.nextAfter(index: Int): T {
    return this[loopedNextIndex(index, size)]
}

fun <T> List<T>.prevBefore(index: Int): T {
    return this[loopedPrevIndex(index, size)]
}

/**
 * Returns a random element from this list. Mutates `random`.
 * @param random Source of randomness.
 */
fun <T> List<T>.randomElement(random: Random): T =
    this[Math.floor(this.size * random.nextDouble()).toInt()]

fun <T> List<T>.goForwardLooped(start: Int, steps: Int): T
    = this[(start + steps) % size]

/**
 * Returns `subList(index, size)`.
 * @param index First index
 * @see prefix
 */
fun <T> List<T>.postfix(index: Int): List<T> =
    subList(index, size)

/**
 * Returns `subList(0, index)`.
 * @param index Last index.
 * @see postfix
 */
fun <T> List<T>.prefix(index: Int): List<T> =
    subList(0, index)

/**
 * Returns this list without last element.
 */
fun <T> List<T>.withoutLast(): List<T> =
    subList(0, lastIndex)

/**
 * Returns this list without first element.
 */
fun <T> List<T>.withoutFirst(): List<T> =
    subList(1, size)

/**
 * Tells how many elements we have to pick from a list until they reduce to a
 * good enough value.
 * @param reduce Reducing function.
 * @param enough Predicate for a good enough running total.
 * @return How many items we are to pick from a list
 */
fun <T> List<T>.pickEnough(
    reduce: (T, T) -> T,
    enough: (T) -> Boolean
): Int? {
    var soFar = first()
    if (enough(soFar)) {
        return 1
    }
    for (i in 1..size - 1) {
        soFar = reduce(soFar, this[i])
        if (enough(soFar)) {
            return i + 1
        }
    }
    return null
}
