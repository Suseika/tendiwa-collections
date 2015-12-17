package org.tendiwa.collections

val Collection<*>.indicesButLast: IntRange
    get() = this.indices.run { IntRange(start, endInclusive) }
