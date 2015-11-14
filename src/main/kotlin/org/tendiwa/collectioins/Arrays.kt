package org.tendiwa.collectioins

fun loopedPrevIndex(current: Int, size: Int): Int =
    if (current == 0) size - 1 else current - 1

fun loopedNextIndex(current: Int, size: Int): Int =
    if (current == size - 1) 0 else current + 1

fun BooleanArray2D(width: Int, height: Int): Array<BooleanArray> =
    Array(width, { BooleanArray(height) })

