package org.tendiwa.collections

fun <T> DoublyLinkedCycle(payloads: List<T>): List<MutableDoublyLinkedNode<T>> {
    return payloads
        .apply {
            if (size < 2) {
                throw  IllegalArgumentException(
                    "Cycle must contain at least 2 elements, but it contains $size"
                )
            }
        }
        .map { MutableDoublyLinkedNode(it) }
        .apply {
            loopedLinks.forEach {
                pair ->
                pair.first.connectWithNext(pair.second)
            }
        }
}
