package org.tendiwa.collections

import org.junit.Test
import org.tendiwa.collectioins.nextAfter
import org.tendiwa.collectioins.prevBefore
import org.tendiwa.collectioins.randomElement
import java.util.*
import kotlin.test.assertEquals

class ListsTest {
    @Test fun nextAfter() {
        val list = listOf("a", "b", "c")
        assertEquals("a", list.nextAfter(2))
        assertEquals("b", list.nextAfter(0))
    }

    @Test fun prevBefore() {
        val list = listOf("a", "b", "c")
        assertEquals("c", list.prevBefore(0))
        assertEquals("a", list.prevBefore(1))
    }

    @Test fun randomElement() {
        val random: Random = Random(0)
        val list = listOf("a", "b", "c")
        assert(
            (0..100)
                .map { list.randomElement(random) }
                .toSet()
                .containsAll(list)
        )
    }
}
