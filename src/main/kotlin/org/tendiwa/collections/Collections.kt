package org.tendiwa.collections

val Collection<*>.indicesButLast: IntRange
    get() = this.indices.run { IntRange(start, endInclusive) }

fun Collection<*>.hasNoDuplicates(): Boolean =
    this.toSet().size == this.size
