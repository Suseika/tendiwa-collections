package org.tendiwa.collections

// TODO: Rewrite connectWithNext and connectiWithPrevious so this method is
// not needed
infix fun <T> DoublyLinkedNode<T>.mutuallyConnectWithNext(
    other: DoublyLinkedNode<T>
) {
    this.connectWithNext(other)
    other.connectWithPrevious(this)
}
