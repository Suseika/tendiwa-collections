package org.tendiwa.collections

val <T> ImmutableDoublyLinkedNode<T>.isInCycle: Boolean
    get() {
        var current: ImmutableDoublyLinkedNode<T> = this
        do {
            val following = current.next ?: return false
            current = following
        } while (current != this)
        return true
    }

