package org.tendiwa.collections

val <T> DoublyLinkedNode<T>.isInCycle: Boolean
    get() {
        var current: DoublyLinkedNode<T> = this
        do {
            val following = current.next ?: return false
            current = following
        } while (current != this)
        return true
    }

