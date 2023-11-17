package service

import entity.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * This class tests all the methods of the [SpielService] class
 */

class TestSpielService {
    private val root = RootService()
    private val names = mutableListOf<String>()
    private val allCards = root.spielService.defaultRandomCardList() as MutableList<Karte>

    /**
     * checks if the function accepts only 2-4 players
     */
    @Test
    fun testCreatePlayers() {


        names.add("fares")
        root.spielService.createPlayers(names, allCards)
        assertEquals(names.size, 1)


        names.add("daniel")
        root.spielService.createPlayers(names, allCards)
        assertEquals(names.size, 2)




        names.add("susan")
        root.spielService.createPlayers(names, allCards)
        assertEquals(names.size, 3)


        names.add("theo")
        root.spielService.createPlayers(names, allCards)
        assertEquals(names.size, 4)

    }

    /**
     * checks if the startGame method works
     */
    @Test
    fun testStartGame() {

        names.add("fares")
        names.add("daniel")
        names.add("susan")
        names.add("theo")
        root.spielService.spielStarten(names)

        assertEquals(false, root.currentGame!!.spieler[0].klopfen)
        assertEquals(0, root.currentGame!!.passenAnzahl)
        assertEquals(0, root.currentGame!!.spielerIndex)
        assertEquals(root.currentGame!!.mitteKarten.size, 3)

        //all cards=32 ,4 player have 3 card each one,3 mitte karte ==32-(4*3)-3=17
        assertEquals(root.currentGame!!.nachziehStapel.size, 17)

        //test if all player have 3 handKarte
        assertEquals(3, root.currentGame!!.spieler[0].handKarte.size)
        assertEquals(3, root.currentGame!!.spieler[1].handKarte.size)
        assertEquals(3, root.currentGame!!.spieler[2].handKarte.size)
        assertEquals(3, root.currentGame!!.spieler[3].handKarte.size)

    }

    /**
     * checks if drawThree method works
     */
    @Test
    fun testDrawThree() {
        names.add("fares")
        names.add("fares")
        names.add("fares")
        names.add("fares")
        root.spielService.spielStarten(names)
        val game = root.currentGame!!
        val cards = game.nachziehStapel
        println(cards)
        val firstThreeCards = mutableListOf(game.nachziehStapel[0], game.nachziehStapel[1], game.nachziehStapel[2])
        val test = root.spielService.drawThree(cards)
        assertEquals(test, firstThreeCards)
    }


    /**
     * checks if the function returns the correct answer using the rules of the game
     */
    @Test
    fun testScore() {
        // first deck
        val l1 = mutableListOf<Karte>()
        l1.add(Karte(CardSuit.DIAMONDS, CardValue.NINE))
        l1.add(Karte(CardSuit.DIAMONDS, CardValue.JACK))
        l1.add(Karte(CardSuit.DIAMONDS, CardValue.ACE))

        // second hand deck
        val l2 = mutableListOf<Karte>()
        l2.add(Karte(CardSuit.HEARTS, CardValue.SEVEN))
        l2.add(Karte(CardSuit.HEARTS, CardValue.TEN))
        l2.add(Karte(CardSuit.HEARTS, CardValue.JACK))

        // third hand deck
        val l3 = mutableListOf<Karte>()
        l3.add(Karte(CardSuit.SPADES, CardValue.ACE))
        l3.add(Karte(CardSuit.SPADES, CardValue.SEVEN))
        l3.add(Karte(CardSuit.HEARTS, CardValue.QUEEN))

        // fourth hand deck
        val l4 = mutableListOf<Karte>()
        l4.add(Karte(CardSuit.HEARTS, CardValue.JACK))
        l4.add(Karte(CardSuit.CLUBS, CardValue.QUEEN))
        l4.add(Karte(CardSuit.DIAMONDS, CardValue.KING))

        // fifth hand deck
        val l5 = mutableListOf<Karte>()
        l5.add(Karte(CardSuit.SPADES, CardValue.EIGHT))
        l5.add(Karte(CardSuit.DIAMONDS, CardValue.EIGHT))
        l5.add(Karte(CardSuit.HEARTS, CardValue.EIGHT))

        // tests

        assertEquals(30.0, SpielService(root).punkteBerechnen(l1))
        assertEquals(27.0, SpielService(root).punkteBerechnen(l2))
        assertEquals(18.0, SpielService(root).punkteBerechnen(l3))
        assertEquals(10.0, SpielService(root).punkteBerechnen(l4))
        assertEquals(30.5, SpielService(root).punkteBerechnen(l5))

    }

}