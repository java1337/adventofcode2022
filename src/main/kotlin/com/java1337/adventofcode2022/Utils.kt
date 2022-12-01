package com.java1337.adventofcode2022

import java.lang.Integer.max
import java.lang.Integer.min
import java.math.BigInteger
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.ceil

class Utils {
    companion object {
        fun readFileAsListOfString(file: String): List<String> {
            @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
            val inputRaw = Utils::class.java.classLoader.getResource(file).readText()
            return inputRaw.split("\n")
        }
    }
}

/**
 * This file contains useful Kotlin extensions to deal with
 * various problems that we need to solve during Advent Of Code
 *
 * Including String, Ints, Longs, Iterable & Collections, Ranges, Pairs, Complex, ...
 *
 * These extensions are used in all the different years solutions.
 * package me.grison.aoc
 * source: https://github.com/agrison/advent-of-code/blob/master/src/main/kotlin/me/grison/aoc/Extensions.kt
 */


/*
 219
 398
 ->
 {(0,0)=2, (0,1)=1, (0,2)=9
  (1,0)=3, (1,1)=9, (1,2)=8}
 */
typealias Grid<T> = MutableMap<Position, T>

fun List<String>.intGrid(default: Int): Grid<Int> {
    val grid = mutableMapOf<Position, Int>().withDefault { default }
    var height = 0
    forEach { line ->
        var width = 0
        line.split("").map { println(it); it.toInt() }.forEach { n ->
            grid[p(height, width++)] = n
        }
        height++
    }
    return grid
}

fun List<String>.stringGrid(default: String): Grid<String> {
    val grid = mutableMapOf<Position, String>().withDefault { default }
    var height = 0
    forEach { line ->
        var width = 0
        line.split("").forEach { n ->
            grid[p(height, width++)] = n
        }
        height++
    }
    return grid
}

fun <T> MutableList<T>.append(t: T): MutableList<T> {
    this.add(t)
    return this
}

// Integers & Long
/** Returns whether this is divisible by `other`. */
fun Int.divisible(other: Int) = this % other == 0

/** Returns whether this is in the given range. */
fun Int.`in`(i: IntRange) = (i.first..(i.last + 1)).contains(this)

/** Returns whether this is divisible by `other`. */
fun Long.divisible(other: Long) = this % other == 0L

fun Int.odd() = this % 2 == 1
fun Int.even() = this % 2 == 0
fun Int.zero() = this == 0
fun Int.pos() = this >= 0
fun Int.neg() = this < 0

// Booleans
/** Returns `1` if `true`, `0` otherwise. */
fun Boolean.toInt() = if (this) 1 else 0

// Characters
/** `+` operator for characters. */
operator fun Char.plus(c: Char) = toString() + c

/** Returns whether this equals `c`. */
fun Char.eq(c: Char) = this == c

//Ranges
/** Returns whether this range includes all the given `ints`. */
fun IntRange.includes(vararg ints: Int) = ints.all(this::contains)


// Sequence
fun <T : Any> cycle(vararg xs: T): Sequence<T> {
    var i = 0
    return generateSequence { xs[i++ % xs.size] }
}

// Pairs
/** Difference between an ordered Pair. */
fun Pair<Int, Int>.difference() = second - first

/** Whether the difference between an ordered Pair is `i`. */
fun Pair<Int, Int>.differenceIs(i: Int) = difference() == i

/** Difference between an ordered Pair. */
fun Pair<Long, Long>.difference() = (second - first).absoluteValue

/** Whether the difference between an ordered Pair is `i`. */
fun Pair<Long, Long>.differenceIs(i: Long) = difference() == i

/** Returns the sum of the elements in this Pair. */
fun Pair<Int, Int>.sum() = first + second

/** Returns the product of the elements in this Pair. */
fun Pair<Int, Int>.multiply() = first * second

/** Returns the product of the elements in this Pair. */
fun Pair<Int, Int>.product() = first * second

/** `+` operator for Pairs. */
operator fun Pair<Int, Int>.plus(p: Pair<Int, Int>): Pair<Int, Int> = Pair(first + p.first, second + p.second)
fun Pair<Int, Int>.plus(a: Int, b: Int): Pair<Int, Int> = Pair(first + a, second + b)

/** `-` operator for Pairs. */
operator fun Pair<Int, Int>.minus(p: Pair<Int, Int>): Pair<Int, Int> = Pair(first - p.first, second - p.second)

/** `*` operator for Pairs. */
operator fun Pair<Int, Int>.times(p: Pair<Int, Int>): Pair<Int, Int> = Pair(first * p.first, second * p.second)

fun Pair<Int, Int>.above() = this.plus(p(0, -1))
fun Pair<Int, Int>.below() = this.plus(p(0, 1))
fun Pair<Int, Int>.left() = this.plus(p(-1, 0))
fun Pair<Int, Int>.right() = this.plus(p(1, 0))
fun Pair<Int, Int>.up(i: Int) = this.plus(p(0, -i))
fun Pair<Int, Int>.down(i: Int) = this.plus(p(0, i))
fun Pair<Int, Int>.forward(i: Int) = this.plus(p(i, 0))
fun Pair<Int, Int>.forward(i: Int, aim: Int) = this.plus(p(i, aim))
fun Pair<Int, Int>.backward(i: Int) = this.plus(p(-i, 0))
fun Pair<Int, Int>.within(minx: Int, miny: Int, maxx: Int, maxy: Int) = first in minx..maxx && second in miny..maxy
fun Pair<Int, Int>.directions() = listOf(above(), below(), left(), right())
fun Pair<Int, Int>.neighbors() = listOf(
    plus(-1, -1), plus(-1, 0), plus(-1, 1),
    plus(0, -1), /*  self  */ plus(0, 1),
    plus(1, -1), plus(1, 0), plus(1, 1)
)

// return an increasing range
fun Pair<Int, Int>.range() = min(first, second)..max(first, second)

// compute the slope of a pair representing two X or two Y (derivative)
fun Pair<Int, Int>.slope() = when {
    first == second -> 0
    first > second -> -1
    else -> 1
}

fun Pair<Int, Int>.pivotSecond(loc: Int) = if (second < loc) this else p(first, loc - (second - loc))
fun Pair<Int, Int>.pivotFirst(loc: Int) = if (first < loc) this else p(loc - (first - loc), second)

fun Pair<Long, Long>.pivotSecond(loc: Long) = if (second < loc) this else Pair(first, loc - (second - loc))
fun Pair<Long, Long>.pivotFirst(loc: Long) = if (first < loc) this else Pair(loc - (first - loc), second)

// works with MutableMap.withDefault()
fun <T> MutableMap<T, Int>.increase(key: T, amount: Int = 1): MutableMap<T, Int> {
    this[key] = this.getOrDefault(key, 0) + amount
    return this
}

fun <T> MutableMap<T, Long>.increase(key: T, amount: Long = 1L): MutableMap<T, Long> {
    this[key] = this.getOrDefault(key, 0L) + amount
    return this
}

fun <T> MutableMap<T, BigInteger>.increase(key: T, amount: BigInteger = BigInteger.ONE): MutableMap<T, BigInteger> {
    this[key] = this.getOrDefault(key, BigInteger.ZERO).add(amount)
    return this
}

/** Returns the sum of the elements in this Pair. */
fun Pair<Long, Long>.sum() = first + second

/** Returns the product of the elements in this Pair. */
fun Pair<Long, Long>.multiply() = first * second

/** Shortcut for creating a Pair. */
fun <T, U> p(t: T, u: U): Pair<T, U> = Pair(t, u)
fun pd(t: Double, u: Double): Pair<Double, Double> = Pair(t, u)

/** Returns the manhattan distance for this Pair. */
fun Pair<Int, Int>.manhattan() = first.absoluteValue + second.absoluteValue

/** Swap a Pair. */
fun <T, U> Pair<T, U>.swap() = p(second, first)

fun Pair<Double, Double>.distance() = ceil(abs(first) + kotlin.math.max(0.0, abs(second) - abs(first) / 2))


class Memoize1<in T, out R>(val f: (T) -> R) : (T) -> R {
    private val values = mutableMapOf<T, R>()
    override fun invoke(x: T): R {
        return values.getOrPut(x, { f(x) })
    }
}

fun <T, R> ((T) -> R).memoize(): (T) -> R = Memoize1(this)


fun <T> List<List<T>>.swapRowCols() = flatMap { it.withIndex() }
    .groupBy({ (i, _) -> i }, { (_, v) -> v })
    .map { (_, v) -> v.reversed() }

fun List<List<List<Boolean>>>.flattenGrid() = flatMap { it.mapIndexed { i, list -> IndexedValue(i, list) } }
    .groupBy({ (i, _) -> i }, { (_, v) -> v })
    .map { (_, v) -> v.reduce { acc, list -> acc + list } }

fun intSeq() = generateSequence(1) { it + 1 }
fun intSeq(x: Int) = generateSequence(x) { it + 1 }

typealias HashBag<T> = MutableMap<T, Int>

fun <T> hashBag() = mutableMapOf<T, Int>().withDefault { 0 }
typealias LongHashBag<T> = MutableMap<T, Int>

fun <T> longHashBag() = mutableMapOf<T, Long>().withDefault { 0L }

fun <T> bigIntHashBag() = mutableMapOf<T, BigInteger>().withDefault { BigInteger.ZERO }

typealias Position = Pair<Int, Int>

fun Position.manhattan(other: Position) = abs(first - other.first) + abs(second - other.second)

fun <T> makeList(size: Int, t: T): MutableList<T> {
    val list = mutableListOf<T>()
    for (i in 0.until(size)) {
        list.add(t)
    }
    return list
}

fun Iterable<Int>.sumLong(): Long = this.fold(0L, Long::plus)

fun List<Long>.median(): Long {
    return this.sorted()[this.size/2]
}

fun Array<Long>.increase(i: Int, amount: Long = 1): Array<Long> {
    this[i] += amount
    return this
}

fun gridPositions(dimensions: Pair<Int, Int>) = gridPositions(dimensions.first, dimensions.second)
fun gridPositions(height: Int, width: Int) = (0.until(height)).flatMap { y -> (0.until(width)).map { x -> p(y, x) } }


fun Iterable<Pair<Long, Long>>.pointsDisplay(empty: String = " "): String {
    val (maxX, maxY) = p(maxOf { it.first }, maxOf { it.second })
    val display = arrayListOf<List<String>>()
    for (y in 0..maxY) {
        display.add((0..maxX).map { x -> if (p(x, y) in this) "${App.CYAN}#${App.RESET}" else empty })
    }
    return display.joinToString("\n") { it.joinToString("") }
}

//fun Collection<Int>.range() = max()!! - min()!!
//fun Collection<Long>.range() = max()!! - min()!!
//fun Collection<BigInteger>.range() = max()!!.minus(min()!!)

fun <T> Map<T, Long>.frequency() = longHashBag<T>().let { hash ->
    this.forEach { (k, v) -> hash.increase(k, v)}
    hash
}
fun <T, U> Map<T, Long>.frequency(selector: (T) -> U) = longHashBag<U>().let { hash ->
    this.forEach { (k, v) -> hash.increase(selector(k), v)}
    hash
}
fun <T> Collection<T>.frequency() =  bigIntHashBag<T>().let { hash ->
    this.forEach { c -> hash.increase(c) }
    hash
}