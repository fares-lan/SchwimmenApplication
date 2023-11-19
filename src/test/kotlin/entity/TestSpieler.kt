package entity

import org.junit.jupiter.api.Test
import kotlin.test.*

/**
 * * This class tests all the methods of the[Spieler]class
 */

class TestSpieler {
    /**
     *[test] is A function that test player
     */
    @Test
    fun test() {
        val cart1 = Karte(CardSuit.SPADES, CardValue.SEVEN)
        val cart2 = Karte(CardSuit.DIAMONDS, CardValue.JACK)
        val cart3 = Karte(CardSuit.DIAMONDS, CardValue.SEVEN)
        val test = Spieler(name = "player", klopfen = false, handKarte = mutableListOf(cart1, cart2, cart3))

        /**
         * test of name,klopfen,handKarte
         */

        assertEquals(test.name, "player")
        assertTrue { test.name != "" }

        assertEquals(test.klopfen, false)

        assertEquals(test.handKarte, mutableListOf(cart1, cart2, cart3))

    }
}
