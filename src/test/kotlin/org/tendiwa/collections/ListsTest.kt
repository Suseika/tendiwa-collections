package org.tendiwa.collections

import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.util.*
import kotlin.test.assertEquals

class ListsTest {
    @JvmField @Rule val expectRule = ExpectedException.none()

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

    @Test fun goForwardLooped_loopsAround() {
        assertEquals(
            "a",
            listOf("a", "b", "c", "d").goForwardLooped(start = 2, steps = 2)
        )
    }

    @Test
    fun withoutLastRemovesLastElement() {
        assertEquals(
            listOf(1, 2, 3, 4),
            listOf(1, 2, 3, 4, 5).withoutLast()
        )
    }

    @Test
    fun withoutFirstRemovesFirstElement() {
        assertEquals(
            listOf(2, 3, 4),
            listOf(1, 2, 3, 4).withoutFirst()
        )
    }

    @Test
    fun prefixContainsElementsBeforeSpecifiedIndex() {
        assertEquals(
            listOf("hello", "goodbye"),
            listOf("hello", "goodbye", "dark").prefix(2)
        )
    }

    @Test
    fun prefixFailsWithIndexGTListSize() {
        expectRule.expect(IndexOutOfBoundsException::class.java)
        listOf(1, 2, 3, 4).prefix(5)
    }

    @Test
    fun prefixWorksWith0Index() {
        assertEquals(listOf(), listOf("a", "b", "c").prefix(0))
    }

    @Test
    fun prefixWorksWithIndexEqualToSize() {
        assertEquals(listOf(1, 2, 3), listOf(1, 2, 3).prefix(3))
    }

    @Test
    fun postfixContainsElementsSinceSpecifiedIndex() {
        assertEquals(
            listOf("a", "b"),
            listOf("1", "2", "a", "b").postfix(2)
        )
    }

    @Test
    fun postifixFailsWithNegativeNumber() {
        expectRule.expect(IndexOutOfBoundsException::class.java)
        listOf(1, 2, 3, 4).postfix(-1)
    }

    @Test
    fun postfixWorksWith0Index() {
        assertEquals(listOf(1, 2, 3), listOf(1, 2, 3).postfix(0))
    }

    @Test
    fun postfixWorksWithIndexEqualToSize() {
        assertEquals(listOf(), listOf(1, 2, 3).postfix(3))
    }

    @Test
    fun pickEnoughReducesHeadsUntilResultSatisfiesPredicate() {
        assertEquals(
            4,
            listOf(1, 2, 3, 4, 5, 6).pickEnough({ a, b -> a + b }, { it >= 10 })
        )
    }

    @Test
    fun pickEnoughReturnsNullIfCompleteReductionDoesNotSatisfyPredicate() {
        assertEquals(
            null,
            listOf(1, 2, 3, 4, 5, 6).pickEnough({ a, b -> a + b }, { it > 100 })
        )
    }

    @Test
    fun `circular sublist can go over 0th element`() {
        listOf(0, 1, 2, 3, 4, 5)
            .circularSubList(4, 2)
            .apply {
                assertEquals(
                    listOf(4, 5, 0, 1),
                    this
                )
            }
    }

    @Test
    fun `circuclar sublist can work as the usual sublist`() {
        listOf(0, 1, 2, 3, 4, 5)
            .circularSubList(2, 4)
            .apply {
                assertEquals(
                    listOf(2, 3),
                    this
                )
            }
    }
}
