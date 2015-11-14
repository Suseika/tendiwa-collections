package org.tendiwa.collectioins

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

