package org.tendiwa.collectioins

fun <T> List<T>.nextAfter(index: Int): T {
    return this[loopedNextIndex(index, size)]
}

fun <T> List<T>.prevBefore(index: Int): T {
    return this[loopedPrevIndex(index, size)]
}

