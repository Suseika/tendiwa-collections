package org.tendiwa.collections

fun loopedPrevIndex(current: Int, size: Int): Int =
    if (current == 0) size - 1 else current - 1

fun loopedNextIndex(current: Int, size: Int): Int =
    if (current == size - 1) 0 else current + 1

fun BooleanArray2D(width: Int, height: Int): Array<BooleanArray> =
    Array(width, { BooleanArray(height) })

/**
 * Creates a square array and wills it using a producer funciton.
 * @param width Width and height of the suqare array.
 * @param init Producer function whose parameters are two indices of an
 * element in the square array.
 */
inline fun <reified T> SquareArray(
    width: Int, init: (Int, Int) -> T
): Array<Array<T>> =
    Array(width, { i -> Array(width, { j -> init(i, j) }) })

/**
 * Finds a sorted array's element that is {@code >= value} for which the
 * previous element is {@code < value}.
 *
 * Works in O(log(n))
 *
 * If {@code array} is not sorted, output is undefined.
 * @param array A sorted array to search in.
 * @param value A value.
 * @return Index of an element in {@code array} that is {@code >= value} for
 * which the previous element is {@code < value}, or -1 iv {@code value} is
 * greater than all the elements in {@code array}.
 */
fun IntArray.findEqualOrHigherInSortedArray(value: Int): Int {
    var imax = this.size - 1;
    var imin = 0;
    var lastGreaterImid = -1;
    while (imax >= imin) {
        // TODO: Shouldn't there be parentheses?
        var imid = imax + imin / 2;
        if (this[imid] == value) {
            // TODO: I don't rememeber if method description is correct about
            // "previous element < value", but if it is, this assertion must be
            // true.
            imid = slideBackOverEqualElements(imid)
            assert(imid == 0 || this[imid - 1] < this[imid])
            return imid;
        }
        if (this[imid] < value) {
            imin = imid + 1;
        } else {
            assert(this[imid] > value)
            lastGreaterImid = imid;
            imax = imid - 1;
        }
    }
    // TODO: Maybe it should return array.length instead of -1 when binary search didn't find anything?
    return lastGreaterImid;
}

private fun IntArray.slideBackOverEqualElements(lastGreaterImid: Int): Int {
    var lastGreaterImid1 = lastGreaterImid
    if (lastGreaterImid1 != -1) {
        while (previousElementIsTheSame(lastGreaterImid1)) {
            lastGreaterImid1--;
        }
    }
    return lastGreaterImid1
}

private fun IntArray.previousElementIsTheSame(lastGreaterImid: Int) =
    lastGreaterImid > 0 && this[lastGreaterImid - 1] == this[lastGreaterImid]
