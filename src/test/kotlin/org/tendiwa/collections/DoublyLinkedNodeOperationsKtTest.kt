package org.tendiwa.collections

import org.junit.Test

class DoublyLinkedNodeOperationsKtTest {
    @Test
    fun connectsNodes() {
        val a = MutableDoublyLinkedNode(1)
        val b = MutableDoublyLinkedNode(2)
        a mutuallyConnectWithNext b
        assert(a.next!! == b && b.previous!! == a)
    }
}
