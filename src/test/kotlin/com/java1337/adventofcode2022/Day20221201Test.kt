package com.java1337.adventofcode2022

import org.junit.Test

import org.junit.Assert.*

class Day20221201Test {

    private val sample = listOf(
        "1000",
        "2000",
        "3000",
        "",
        "4000",
        "",
        "5000",
        "6000",
        "",
        "7000",
        "8000",
        "9000",
        "",
        "10000"
    )

    @Test
    fun shouldReturn24000ForSampleInputForFirstQuestion() {
        // given
        val runner = Day20221201(sample)

        // when
        val actual = runner.first()

        // then
        assertEquals("24000", actual)
    }

    @Test
    fun shouldReturn45000ForSampleInputForSecondQuestion() {
        // given
        val runner = Day20221201(sample)

        // when
        val actual = runner.second()

        // then
        assertEquals("45000", actual)
    }
}