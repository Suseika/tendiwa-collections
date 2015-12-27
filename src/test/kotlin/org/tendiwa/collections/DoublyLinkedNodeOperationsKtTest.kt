package org.tendiwa.collections

import org.junit.Test

class DoublyLinkedNodeOperationsKtTest {
    @Test
    fun connectsNodes() {
        val a = DoublyLinkedNode(1)
        val b = DoublyLinkedNode(2)
        a mutuallyConnectWithNext b
        assert(a.next!! == b && b.previous!! == a)
    }
}
