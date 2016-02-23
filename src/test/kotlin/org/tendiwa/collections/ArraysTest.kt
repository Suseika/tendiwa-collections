package org.tendiwa.collections

import org.junit.Test
import kotlin.test.assertEquals

class ArraysTest {
    @Test fun loopPrevIndex() {
        assertEquals(
            3,
            loopedPrevIndex(current = 0, size = 4)
        )
        assertEquals(
            0,
            loopedPrevIndex(current = 1, size = 4)
        )
    }

    @Test fun loopNextIndex() {
        assertEquals(
            0,
            loopedNextIndex(current = 3, size = 4)
        )
        assertEquals(
            3,
            loopedNextIndex(current = 2, size = 4)
        )
    }

    @Test fun findEqualOrHigherInSortedArray() {
        val array = intArrayOf(1, 2, 3, 4, 5, 6, 6, 6, 7, 7, 8, 9, 10)
        assertEquals(5, array.findEqualOrHigherInSortedArray(6))
        assertEquals(0, array.findEqualOrHigherInSortedArray(1))
        assertEquals(array.lastIndex, array.findEqualOrHigherInSortedArray(10))
    }

    @Test fun findEqualOrHigherInSortedArray_returnsMinus1IfValueIsTooBig() {
        assertEquals(
            -1,
            intArrayOf(1, 2, 3).findEqualOrHigherInSortedArray(4)
        )
    }

    @Test fun findEqualOrHigherInSortedAray_findsHigherElement() {
        assertEquals(
            1,
            intArrayOf(1, 3, 4, 6, 10).findEqualOrHigherInSortedArray(2)
        )
    }

    @Test
    fun `square array`() {
        SquareArray(10, { x, y -> 5 })
            .apply {
                assertEquals(10, size)
                assertEquals(10, this[0].size)
                assertEquals(5, this[4][6])
            }
    }
}

