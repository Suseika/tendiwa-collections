package org.tendiwa.collections

import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class LinksTest {
    @Test fun iteratesOverLinks() {
        assertEquals(
            listOf(
                Pair(1, 2), Pair(2, 3), Pair(3, 4), Pair(4, 1)
            ),
            listOf(1, 2, 3, 4).loopedLinks
        )
    }

    @Test fun iteratesOverLinksBetweenTwoElements() {
        assertEquals(
            listOf(Pair(1, 2), Pair(2, 1)),
            listOf(1, 2).loopedLinks
        )
    }

    @Test(expected = NoSuchElementException::class)
    fun failsConstructingLinksWith1Element() {
        listOf(1).loopedLinks
    }

    @Test(expected = NoSuchElementException::class)
    fun failsConstructingLinksWith0Elements() {
        emptyList<Any>().loopedLinks
    }
}

