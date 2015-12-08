package org.tendiwa.collections

import org.junit.Test

class DoublyLinkedListTest {
    @Test fun isCycle() {
        DoublyLinkedCycle(
            listOf("a", "b", "c")
        ).forEach { assert(it.isInCycle) }
    }

    @Test(expected = IllegalArgumentException::class)
    fun cycleFailsWithSingleElement() {
        DoublyLinkedCycle(listOf("a"))
    }
}

