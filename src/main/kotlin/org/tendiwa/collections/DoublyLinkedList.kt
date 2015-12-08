package org.tendiwa.collections

fun <T> DoublyLinkedCycle(payloads: List<T>): List<DoublyLinkedNode<T>> {
    return payloads
        .apply {
            if (size < 2) {
                throw  IllegalArgumentException(
                    "Cycle must contain at least 2 elements, but it contains $size"
                )
            }
        }
        .map { DoublyLinkedNode(it) }
        .apply {
            loopedLinks.forEach {
                pair ->
                pair.first.connectWithNext(pair.second)
            }
        }
}
