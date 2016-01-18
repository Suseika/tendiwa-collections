package org.tendiwa.collections

import java.util.*
import java.util.function.Consumer

interface DoublyLinkedNode<T> : Iterable<T> {
    /**
     * Content of a node.
     */
    val payload: T
    /**
     * Next node. [next.previous] is this node.
     */
    val next: DoublyLinkedNode<T>?
    /**
     * Previous node. [previous.next] is this node.
     */
    val previous: DoublyLinkedNode<T>?

    val isDisconnected: Boolean
        get() = next == null && previous == null

    val isStartOfAChain: Boolean
        get() = hasNext() && !hasPrevious()

    // TODO: Find out if getNext is used instead of this method anywhere
    operator fun hasNext(): Boolean {
        return next != null
    }

    fun hasPrevious(): Boolean {
        return previous != null
    }

    fun hasBothNeighbors(): Boolean {
        // TODO: Maybe here should be XOR?
        return previous != null && next != null
    }

    // TODO: Split into chain iterator and cycle iterator
    override fun iterator(): Iterator<T> {
        return if (next == null) {
            BackwardIterator(this)
        } else{
            ForwardIterator(this)
        }
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

}
private class ForwardIterator<T>(
    start: DoublyLinkedNode<T>
) : LinkedListIterator<T>(start) {
    override fun chooseNext(
        current: DoublyLinkedNode<T>
    ): DoublyLinkedNode<T>? {
        return current.next
    }
}

private class BackwardIterator<T>(
    start: DoublyLinkedNode<T>
) : LinkedListIterator<T>(start) {
    override fun chooseNext(
        current: DoublyLinkedNode<T>
    ): DoublyLinkedNode<T>? {
        return current.previous
    }
}
private abstract class LinkedListIterator<T>(
    val start: DoublyLinkedNode<T>
) : Iterator<T> {

    protected var current: DoublyLinkedNode<T>? = start
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
