package org.tendiwa.collections

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class IterablesKtTest {
    @Test
    fun detectsSortedList() {
        assert(listOf(1.0, 3.0, 3.1, 5.2).isSortedAscending())
    }

    @Test
    fun detectsUnsortedList() {
        assertFalse(listOf(1.0, 4.0, 6.0, 3.0).isSortedAscending())
    }

    @Test
    fun emptyListIsSorted() {
        assert(listOf<Double>().isSortedAscending())
    }

    @Test
    fun singleElementListIsSorted() {
        assert(listOf(1.0).isSortedAscending())
    }

    @Test
    fun detectsOffElementInTheBeginning() {
        assertFalse(listOf(10.0, 1.0, 2.0, 3.0).isSortedAscending())
    }

    @Test
    fun detectsOffElementInTheEnd() {
        assertFalse(listOf(1.0, 2.0, 1.0).isSortedAscending())
    }

    @Test
    fun allowsEqualConsecutiveElements() {
        assert(listOf(1.0, 2.0, 2.0, 2.0).isSortedAscending())
        assert(listOf(1.0, 1.0, 2.0, 3.0).isSortedAscending())
    }

    @Test
    fun `takeUntil can return the whole list if last element satisfies the predicate`() {
        assertEquals(
            listOf(1, 2, 3, 4, 5),
            listOf(1, 2, 3, 4, 5).takeUntil { it == 5 }
        )
    }

    @Test
    fun `takeUntil can return the whole list if none of the elements satisfy predicate`() {
        assertEquals(
            listOf(1, 2, 3, 4, 5),
            listOf(1, 2, 3, 4, 5).takeUntil { it == 6 }
        )
    }

    @Test
    fun `takeUntil takes elements from beginning until an element satisfies the predicate`() {
        assertEquals(
            listOf(1, 2, 3),
            listOf(1, 2, 3, 4, 5).takeUntil { it == 3 }
        )
    }
}
