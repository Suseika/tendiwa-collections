package org.tendiwa.collections

import org.junit.Test
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
}
