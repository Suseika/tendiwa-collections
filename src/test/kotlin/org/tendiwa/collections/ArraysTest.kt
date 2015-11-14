package org.tendiwa.collections

import org.junit.Test
import org.tendiwa.collectioins.loopedNextIndex
import org.tendiwa.collectioins.loopedPrevIndex
import kotlin.test.assertEquals

class ArraysTest {
    @Test fun loopPrevIndex() {
        assertEquals(
            3,
            loopedPrevIndex(current = 0, size = 4)
        )
        assertEquals(
            0,
            loopedPrevIndex(current = 1, size = 4)
        )
    }

    @Test fun loopNextIndex() {
        assertEquals(
            0,
            loopedNextIndex(current = 3, size = 4)
        )
        assertEquals(
            3,
            loopedNextIndex(current = 2, size = 4)
        )
    }
}

