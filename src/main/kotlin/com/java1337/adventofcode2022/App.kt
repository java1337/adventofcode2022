package com.java1337.adventofcode2022

//
// Santa's reindeer typically eat regular reindeer food, but they need a lot of magical energy to deliver presents
// on Christmas. For that, their favorite snack is a special type of star fruit that only grows deep in the jungle.
// The Elves have brought you on their annual expedition to the grove where the fruit grows.
//
// To supply enough magical energy, the expedition needs to retrieve a minimum of fifty stars by December 25th.
// Although the Elves assure you that the grove has plenty of fruit, you decide to grab any fruit you see along the
// way, just in case.
//
// Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent calendar;
// the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!
//
class App {

    companion object {

        const val RESET = "\u001B[0m"
        const val RED   = "\u001B[31m"
        const val CYAN  = "\u001B[36m"

        @JvmStatic
        fun main(args: Array<String>) {
            val challenges = listOf(
                Day20221201.create()
            )
            for (challenge in challenges) {
                println()
                println("Running challenge $CYAN${challenge.getLabel()}$RESET")
                println("The first answer is $RED${challenge.first()}$RESET")
                println("The second answer is $RED${challenge.second()}$RESET")
            }
        }
    }
}
