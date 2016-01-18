package org.tendiwa.collections

// TODO: Rewrite connectWithNext and connectiWithPrevious so this method is
// not needed
infix fun <T> MutableDoublyLinkedNode<T>.mutuallyConnectWithNext(
    other: MutableDoublyLinkedNode<T>
) {
    this.connectWithNext(other)
    other.connectWithPrevious(this)
}
