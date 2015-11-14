package org.tendiwa.collections

import org.junit.Test
import org.tendiwa.collectioins.loopedTriLinks
import java.util.*
import kotlin.test.assertEquals

class TrilinksTest {
    @Test fun iteratesOverTriLinks() {
        assertEquals(
            listOf(
                Triple(1, 2, 3), Triple(2, 3, 4), Triple(3, 4, 5),
                Triple(4, 5, 1), Triple(5, 1, 2)
            ),
            listOf(1, 2, 3, 4, 5).loopedTriLinks
        )
    }

    @Test fun iteratesOverTrilinksOverThreeElements() {
        assertEquals(
            listOf(Triple(1, 2, 3), Triple(2, 3, 1), Triple(3, 1, 2)),
            listOf(1, 2, 3).loopedTriLinks
        )
    }

    @Test(expected = NoSuchElementException::class)
    fun failsConstructingTrilinksWith2Elements() {
        listOf(1, 2).loopedTriLinks
    }

    @Test(expected = NoSuchElementException::class)
    fun failsConstructingTrilinksWith1Element() {
        listOf(1).loopedTriLinks
    }

    @Test(expected = NoSuchElementException::class)
    fun failsConstructingTrilinksWith0Elements() {
        emptyList<Any>().loopedTriLinks
    }
}
