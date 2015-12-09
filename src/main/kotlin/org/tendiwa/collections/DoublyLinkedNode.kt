package org.tendiwa.collections

import java.util.*

/**
 * Implementation of a doubly linked list's node.
 */
data class DoublyLinkedNode<T>(
    override var payload: T
) : ImmutableDoublyLinkedNode<T> {
    override var next: DoublyLinkedNode<T>? = null
        private set

    override var previous: DoublyLinkedNode<T>? = null
        private set

    fun connectWithNext(node: DoublyLinkedNode<T>) {
        Objects.requireNonNull(node)
        assert(settingNextPreservesConnectivity(node))
        next = node
        node.previous = this
    }

    fun connectWithPrevious(node: DoublyLinkedNode<T>) {
        Objects.requireNonNull(node)
        assert(settingPreviousPreservesConnectivity(node))
        previous = node
        node.next = this
    }

    /**
     * @param node A node to insert.
     * @return false if inserting `node` with [.connectWithNext] will introduce
     * a loop or isolate some part of the list.
     * @see [DoublyLinkedNode.settingPreviousPreservesConnectivity]
     */
    private fun settingNextPreservesConnectivity(node: DoublyLinkedNode<T>?): Boolean =
        node != null
            && (node.previous === this || node.previous == null)
            && node !== this
    //			&& next == null

    /**
     * @param node A node to insert.
     * @return false if inserting `node` with [.connectWithPrevious] will
     * introduce a loop or isolate some part of the list.
     */
    private fun settingPreviousPreservesConnectivity(node: DoublyLinkedNode<T>?): Boolean =
        node != null
            && (node.next === this || node.next == null)
            && node !== this
    //			&& previous == null

    /**
     * [Reverts a doubly linked list](http://www.geeksforgeeks.org/reverse-a-doubly-linked-list/)
     *
     * Swaps [DoublyLinkedNode.next] and [DoublyLinkedNode.previous] of each
     * payload in the chain of this node. This node must be either the first in
     * the chain or the last.
     */
    // TODO: Do we really need this method anywhere?
    fun revertChain() {
        assert(next == null || previous == null)
        var temp: DoublyLinkedNode<T>?
        var current: DoublyLinkedNode<T>? = this
        if (next == null) {
            // Starting from the last node
            while (current != null) {
                temp = current.next
                current.next = current.previous
                current.previous = temp
                current = current.next
            }
        } else {
            // Starting from the first node
            while (current != null) {
                temp = current.previous
                current.previous = current.next
                current.next = temp
                current = current.previous
            }
        }
    }

    /**
     * @param listEnd Head or tail of another list.
     */
    fun uniteWith(listEnd: DoublyLinkedNode<T>) {
        val thisNextNull = this.next == null
        val thisPreviousNull = this.previous == null
        val anotherNextNull = listEnd.next == null
        val anotherPreviousNull = listEnd.previous == null
        assert((thisNextNull || thisPreviousNull) && (anotherNextNull || anotherPreviousNull))
        if (thisNextNull && thisPreviousNull) {
            // Two free on this end, one free on listEnd.
            if (anotherNextNull) {
                listEnd.connectWithNext(this)
                this.connectWithPrevious(listEnd)
            } else {
                assert(anotherPreviousNull)
                listEnd.connectWithPrevious(this)
                this.connectWithNext(listEnd)
            }
        } else if (anotherNextNull && anotherPreviousNull) {
            // Two free on listEnd, one free on this end.
            if (thisNextNull) {
                this.connectWithNext(listEnd)
                listEnd.connectWithPrevious(this)
            } else {
                assert(thisPreviousNull)
                this.connectWithPrevious(listEnd)
                listEnd.connectWithNext(this)
            }
        } else {
            // One free on one end, one free on another end.
            assert(thisPreviousNull xor thisNextNull)
            assert(anotherPreviousNull xor anotherNextNull)
            if (previous == null && anotherNextNull) {
                listEnd.next = this
                previous = listEnd
            } else if (next == null && anotherPreviousNull) {
                listEnd.previous = this
                next = listEnd
            } else {
                assert(anotherNextNull && thisNextNull || anotherPreviousNull && thisPreviousNull)
                listEnd.revertChain()
                uniteWith(listEnd)
            }
        }
    }
}
