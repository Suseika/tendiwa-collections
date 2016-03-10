package org.tendiwa.collections

import java.util.*

val <T> Iterable<T>.loopedLinks: Iterable<Pair<T, T>> get() {
    val iterator = iterator()
    var previous: T
    try {
        previous = iterator.next();
    } catch (e: NoSuchElementException) {
        throw NoSuchElementException(
            "Iterable should have at least 2 elements; it has 0"
        );
    }
    val first: T = previous
    if (!iterator.hasNext()) {
        throw NoSuchElementException(
            "Iterable should have at least 2 elements; it has 1"
        );
    }
    val links: MutableList<Pair<T, T>> = LinkedList()
    while (iterator.hasNext()) {
        val current: T = iterator.next()
        links.add(Pair(previous, current))
        previous = current
    }
    links.add(Pair(previous, first))
    return links
}

val <T> Iterable<T>.loopedTriLinks: Iterable<Triple<T, T, T>> get() {
    val iterator = iterator()
    var prePrevious: T
    try {
        prePrevious = iterator.next()
    } catch (e: NoSuchElementException) {
        throw NoSuchElementException(
            "Iterable should have at least 3 elements; it has 0"
        );
    }
    val first: T = prePrevious
    var previous: T
    try {
        previous = iterator.next()
    } catch (e: NoSuchElementException) {
        throw NoSuchElementException(
            "Iterable should have at least 3 elements; it has 1"
        );
    }
    val second: T = previous;
    if (!iterator.hasNext()) {
        throw NoSuchElementException(
            "Iterable should have at least 3 elements; it has 2"
        );
    }
    val links: MutableList<Triple<T, T, T>> = LinkedList()
    while (iterator.hasNext()) {
        val current: T = iterator.next();
        links.add(Triple(prePrevious, previous, current))
        prePrevious = previous;
        previous = current;
    }
    links.add(Triple(prePrevious, previous, first))
    links.add(Triple(previous, first, second))
    return links
}

fun <T: Comparable<T>> Iterable<T>.isSortedAscending(): Boolean {
    val iterator = iterator()
    if (!iterator.hasNext()) {
        return true
    }
    var previous = iterator.next()
    if (!iterator.hasNext()) {
        return true
    }
    do {
        var current = iterator.next()
        if (previous > current) {
            return false
        }
        previous = current
    } while (iterator.hasNext())
    return true
}

/**
 * Returns a list containing first elements satisfying the given [predicate].
 */
inline fun <T> Iterable<T>.takeUntil(predicate: (T) -> Boolean): List<T> {
    val list = ArrayList<T>()
    for (item in this) {
        list.add(item)
        if (predicate(item))
            break;
    }
    return list
}
