package org.tendiwa.collections

import java.util.*
import java.util.function.Consumer

/**
 * Implementation of a doubly linked list's node.
 * @param Type of payload.
 */
class DoublyLinkedNode<T>(val payload: T) : Iterable<T> {
    var next: DoublyLinkedNode<T>? = null
        private set

    var previous: DoublyLinkedNode<T>? = null
        private set

    val isDisconnected: Boolean
        get() = next == null && previous == null

    // TODO: Make connectWithNext and connectWithPrevious set previous and next for another node as well
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
     *
     * Swaps [DoublyLinkedNode.next] and [DoublyLinkedNode.previous] of each
     * payload in the chain of this node. This node must be either the first in
     * the chain or the last.
     */
    // TODO: Do we really need this method anywhere?
    fun revertChain() {
        assert(next == null || previous == null)
        var temp: DoublyLinkedNode<T>
        var current: DoublyLinkedNode<T> = this
        if (next == null) {
            // Starting from the last node
            while (current != null) {
                temp = current.next!!
                current.next = current.previous
                current.previous = temp
                current = current.next!!
            }
        } else {
            // Starting from the first node
            while (current != null) {
                temp = current.previous!!
                current.previous = current.next
                current.next = temp
                current = current.previous!!
            }
        }
    }

    /**
     * @param listEnd
     * * 	Head or tail of another list.
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

    // TODO: Split into chain iterator and cycle iterator
    override fun iterator(): Iterator<T> {
        return if (next == null) BackwardIterator() else ForwardIterator()
    }

    private fun iterateCircularList(action: Consumer<in T>) {
        assert(next != null && previous != null)
        var current: DoublyLinkedNode<T>? = this
        do {
            action.accept(current!!.payload)
            current = current.next
            if (current == null) {
                throw IllegalArgumentException(
                    "You can only iterate over a chain of nodes beginning from a " + "node that is either end or start of a chain, or is in the middle of a circular chain")
            }
        } while (current !== this)
    }

    private fun iterateChainFromStart(action: Consumer<in T>) {
        assert(previous == null && next != null)
        var current: DoublyLinkedNode<T>? = this
        while (current != null) {
            action.accept(current.payload)
            current = current.next
        }
    }

    private fun iterateChainFromEnd(action: Consumer<in T>) {
        assert(next == null && previous != null)
        var current: DoublyLinkedNode<T>? = this
        while (current != null) {
            action.accept(current.payload)
            current = current.previous
        }
    }

    fun hasBothNeighbors(): Boolean {
        // TODO: Maybe here should be XOR?
        return previous != null && next != null
    }

    // TODO: Find out if getNext is used instead of this method anywhere
    operator fun hasNext(): Boolean {
        return next != null
    }

    fun hasPrevious(): Boolean {
        return previous != null
    }

    val isStartOfAChain: Boolean
        get() = hasNext() && !hasPrevious()

    private abstract inner class LinkedListIterator : Iterator<T> {

        protected var current: DoublyLinkedNode<T>? = this@DoublyLinkedNode
        protected val start = this@DoublyLinkedNode
        protected var hasNext = true

        override fun hasNext(): Boolean {
            return hasNext
        }

        protected fun recomputeHasNext() {
            hasNext = current != null && current !== start
        }

        override fun next(): T {
            if (!hasNext) {
                throw NoSuchElementException()
            }
            val answer = current!!.payload
            current = chooseNext(current!!)
            recomputeHasNext()
            return answer
        }

        protected abstract fun chooseNext(
            current: DoublyLinkedNode<T>
        ): DoublyLinkedNode<T>?
    }

    private inner class ForwardIterator : LinkedListIterator() {
        override fun chooseNext(
            current: DoublyLinkedNode<T>
        ): DoublyLinkedNode<T>? {
            return current.next
        }
    }

    private inner class BackwardIterator : LinkedListIterator() {
        override fun chooseNext(
            current: DoublyLinkedNode<T>
        ): DoublyLinkedNode<T>? {
            return current.previous
        }
    }

    override fun toString(): String {
        return "DoublyLinkedNode{$payload}"
    }
}
