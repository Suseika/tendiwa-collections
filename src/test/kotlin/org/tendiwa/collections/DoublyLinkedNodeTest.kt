package org.tendiwa.collections

import com.google.common.collect.Iterables
import org.junit.Assert.*
import org.junit.Test
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class DoublyLinkedNodeTest {
    private fun ThreeNodeCycle(): MutableDoublyLinkedNode<String> {
        val one = MutableDoublyLinkedNode("a")
        val two = MutableDoublyLinkedNode("b")
        val three = MutableDoublyLinkedNode("c")
        one.connectWithNext(two)
        two.connectWithNext(three)
        three.connectWithNext(one)
        return one
    }

    private fun ThreeNodeChain(): MutableDoublyLinkedNode<String> {
        val one = MutableDoublyLinkedNode("A")
        val two = MutableDoublyLinkedNode("B")
        val three = MutableDoublyLinkedNode("C")
        one.connectWithNext(two)
        two.connectWithNext(three)
        return one
    }

    @Test
    fun circular_list_for_each_touches_each_node_once() {
        val cycle = ThreeNodeCycle()
        val i = AtomicInteger(0)
        cycle.forEach { p -> i.incrementAndGet() }
        assertEquals(
            3,
            i.get().toLong())
    }

    @Test
    fun circular_list_iterates() {
        val cycle = ThreeNodeCycle()
        val iterator = cycle.iterator()
        for (i in 0..2) {
            iterator.next()
        }
    }

    @Test(expected = NoSuchElementException::class)
    fun circular_list_iterates_finitely() {
        val cycle = ThreeNodeCycle()
        val iterator = cycle.iterator()
        for (i in 0..3) {
            iterator.next()
        }
    }

    @Test
    fun forward_iteration_traverses_whole_chain() {
        val chain = ThreeNodeChain()
        assertNull(chain.previous)
        assertNotNull(chain.next)
        assertEquals(3, Iterables.size(chain))
    }

    @Test
    fun backward_iteration_traverses_whole_chain() {
        val backwardChain = ThreeNodeChain().next!!.next!!
        assertNull(backwardChain.next)
        assertNotNull(backwardChain.previous)
        assertEquals(3, Iterables.size(backwardChain))
    }

    @Test fun circularlyConnectedNodesAreInCycle() {
        val a = MutableDoublyLinkedNode("dude")
        val b = MutableDoublyLinkedNode("man")
        val c = MutableDoublyLinkedNode("amigo")
        a.connectWithNext(b)
        b.connectWithNext(c)
        c.connectWithNext(a)
        assert(a.isInCycle)
        assert(b.isInCycle)
        assert(c.isInCycle)
    }

    @Test fun disconnectedNodeIsNotInCycle() {
        assertFalse(MutableDoublyLinkedNode("dude").isInCycle)
    }

    @Test fun nodesConnectedInAPolylineAreNotInCycle() {
        val a = MutableDoublyLinkedNode("dude")
        val b = MutableDoublyLinkedNode("man")
        val c = MutableDoublyLinkedNode("amigo")
        a.connectWithNext(b)
        b.connectWithNext(c)
        assertFalse(a.isInCycle)
        assertFalse(b.isInCycle)
        assertFalse(c.isInCycle)
    }
}
