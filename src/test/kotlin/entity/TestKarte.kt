package entity

import  org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * * This class tests all the methods of the[Karte]class
 */


class TestKarte {
    /**
     * [test] of Queen Spades
     */
    @Test
    fun test() {

        val test = Karte(CardSuit.SPADES, CardValue.QUEEN)
        assertEquals(test.karteFarbe, CardSuit.SPADES)
        assertEquals(test.karteWert, CardValue.QUEEN)

    }


}