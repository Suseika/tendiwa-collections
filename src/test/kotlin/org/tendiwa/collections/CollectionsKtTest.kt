package org.tendiwa.collections

import org.junit.Test
import kotlin.test.assertFalse

class CollectionsKtTest {
    @Test
    fun hasNoDuplicates() {
        assert(
            listOf(1, 2, 3, 4).hasNoDuplicates()
        )
    }

    @Test
    fun `can have duplicates`() {
        assertFalse(
            listOf(1, 2, 3, 1).hasNoDuplicates()
        )
    }
}
