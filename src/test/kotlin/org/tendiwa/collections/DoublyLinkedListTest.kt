package org.tendiwa.collections

import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import kotlin.test.assertFalse

class DoublyLinkedListTest {
    @JvmField @Rule val expectRule = ExpectedException.none()

    @Test fun isCycle() {
        DoublyLinkedCycle(
            listOf("a", "b", "c")
        ).forEach { assert(it.isInCycle) }
    }

    @Test
    fun cycleFailsWithSingleElement() {
        expectRule.expectMessage(
            "Cycle must contain at least 2 elements"
        )
        expectRule.expect(IllegalArgumentException::class.java)
        DoublyLinkedCycle(listOf("a"))
    }

    @Test
    fun unlink() {
        DoublyLinkedCycle(
            listOf("a", "b", "c")
        ).apply {
            this[0].unlink(this[1])
            assertFalse(this[0].hasNext())
            assertFalse(this[1].hasPrevious())
        }
    }

    @Test
    fun unlinkFailsIfParemeterIsNotANeighbor() {
        expectRule.expectMessage(
            "Node to unlink must be a neighbor of this node"
        )
        expectRule.expect(IllegalArgumentException::class.java)
        DoublyLinkedCycle(
            listOf("a", "b", "c", "d")
        )
            .apply { this[0].unlink(this[2]) }
    }

    @Test
    fun unlinkFailsWithACycleOf2Nodes() {
        expectRule.expectMessage(
            "Can't unlink a cycle of 2 nodes"
        )
        expectRule.expect(IllegalArgumentException::class.java)
        DoublyLinkedCycle(
            listOf("a", "b")
        )
            .apply { this[0].unlink(this[1]) }
    }
}

