package service

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * This class tests all the methods of the [SpielerService] class
 */

class TestSpielerService {

    private val root = RootService()
    private val names = mutableListOf<String>()


    /**
     * checks if the whole middle deck is replaced with the player´s hand deck
     */
    @Test
    fun testAlleKartenTauschen() {
        names.add("fares")
        names.add("daniel")

        //start the game
        root.spielService.spielStarten(names)
        val game = root.currentGame!!

        // save the existing cards to compare them after switching places/hands
        val middle = this.root.currentGame!!.mitteKarten


        // switch all the cards
        this.root.spielerService.alleKartenTauschen()

        // testing the pass counter
        assertEquals(0, game.passenAnzahl)

        // compare the two decks
        assertEquals(middle, game.spieler[0].handKarte)

    }


    /**
     * checks if the pass counter has been incremented by 1
     */
    @Test
    fun testPass() {

        names.add("fares")
        names.add("daniel")
        names.add("susan")
        names.add("susan")

        // starts the game
        root.spielService.spielStarten(names)

        // the beginning of the game
        assertEquals(0, root.currentGame!!.passenAnzahl)

        // after one pass
        root.spielerService.passen()
        assertEquals(1, root.currentGame!!.passenAnzahl)

        // after two passes
        root.spielerService.passen()
        assertEquals(2, root.currentGame!!.passenAnzahl)

        // after three passes
        root.spielerService.passen()
        assertEquals(3, root.currentGame!!.passenAnzahl)

        // after four passes -> must return to zero
        root.spielerService.passen()
        assertEquals(0, root.currentGame!!.passenAnzahl)
    }

    /**
     * checks the functionality of the knock function
     */
    @Test
    fun testKlopfen() {

        names.add("fares")
        names.add("daniel")


        // start the game
        root.spielService.spielStarten(names)
        val game = root.currentGame!!

        // knock
        root.spielerService.klopfen()
        assertEquals(game.spieler[game.spielerIndex - 1].klopfen, true)


    }


    /**
     * checks if the cards are correctly switched with the same positions
     */
    @Test
    fun testEinKarteTauschen() {
        names.add("fares")
        names.add("daniel")
        names.add("susan")

        // start the game
        root.spielService.spielStarten(names)


        // save existing cards to compare them after switching places/hands
        val secondCardMiddle = this.root.currentGame!!.mitteKarten[1]
        val thirdCardFirstPlayer = this.root.currentGame!!.spieler[0].handKarte[2]

        // switch one card
        root.spielerService.eineKarteTauschen(2, 1)

        // compare the cards
        assertEquals(secondCardMiddle, this.root.currentGame!!.spieler[0].handKarte[2])
        assertEquals(thirdCardFirstPlayer, this.root.currentGame!!.mitteKarten[1])
    }

    /**
     * checks if game =Null
     */
    @Test
    fun testEndgame() {
        root.spielerService.endgame()
        assertEquals(root.currentGame, null)
    }

    /**
     * will call when end turn
     */
    @Test
    fun testzugBeenden() {

        names.add("fares")
        names.add("fares")
        root.spielService.spielStarten(names)
        val game = root.currentGame!!

        //check if we pass two time
        root.spielerService.passen()
        assertEquals(game.passenAnzahl, 1)
        root.spielerService.passen()
        assertEquals(game.passenAnzahl, 0)


        game.spielerIndex = 0
        //check if we knock
        root.spielerService.klopfen()
        assertEquals(game.spielerIndex, 1)


    }
}

