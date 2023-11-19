package entity

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


/**
 * * This class tests all the methods of the[Schwimmen]class
 */

class TestSchwimmen {

    /**
     * [test]is function that test the game
     */
    @Test
    fun test() {

        /**
         * add zwei spieler
         */
        val cart1 = Karte(CardSuit.SPADES, CardValue.SEVEN)
        val cart2 = Karte(CardSuit.DIAMONDS, CardValue.JACK)
        val cart3 = Karte(CardSuit.DIAMONDS, CardValue.SEVEN)

        val test = Schwimmen(0, 0)
        test.spieler.add(Spieler("player1", false, mutableListOf(cart1, cart2, cart3)))
        test.spieler.add(Spieler("player2", false, mutableListOf(cart1, cart2, cart3)))


        /**
         * test size, SpielerIndex, PassenAnzahl der Spieler
         */
        assertEquals(test.spieler.size, 2)
        assertTrue { test.spieler.size in (2..4) }
        assertEquals(test.spielerIndex, 0)
        assertEquals(test.passenAnzahl, 0)


        /**
         * add 3 karte in der mitte
         */

        val mitte = mutableListOf(cart1, cart2, cart3)
        test.mitteKarten.add(cart1)
        test.mitteKarten.add(cart2)
        test.mitteKarten.add(cart3)


        /**
         * test mitteKarten muss drei karte sein
         */
        assertEquals(test.mitteKarten.size, 3)
        assertEquals(test.mitteKarten, mitte)


        /**
         * Stack add 2 karte
         */
        val stack = mutableListOf(cart1, cart2)
        test.nachziehStapel.add(cart1)
        test.nachziehStapel.add(cart2)
        /**
         * test stack muss in [0..32] sein
         */
        assertEquals(stack, test.nachziehStapel)
        assertTrue { test.nachziehStapel.size in (0..32) }

    }
}






