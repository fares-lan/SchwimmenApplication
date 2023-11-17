package service

import entity.*


/**
 * This class defines all the actions/moves that each player
 * can do (knock, pass, change one card or change all the cards)
 */
class SpielerService(private val rootService: RootService) : AbstractRefreshableService() {

    /**
     * A function that swaps all three cards in the middle with all three cards from the player´s hand
     */
    fun alleKartenTauschen() {
        val game = rootService.currentGame!!
        game.passenAnzahl = 0

        // get the hand of the currentPlayer
        val index = game.spielerIndex
        val spielerHand = game.spieler[index].handKarte

        //get mitte card
        val miteKarte = game.mitteKarten

        // speichere die zu tauschenden Karten in die Variablen

        //swap
        game.mitteKarten = spielerHand
        game.spieler[index].handKarte = miteKarte

        onAllRefreshable { refreshAfterCardSwaps() }
        zugBeenden()

    }


    /**
     * A function that swaps only ONE card from the middle deck
     * and the hand of the active player
     *
     * @param karteSpieler defines the index of the card that will be changed
     * @param karteMitte defines the index of the card on the table
     */
    fun eineKarteTauschen(karteSpieler: Int, karteMitte: Int) {
        //get current player
        val game = rootService.currentGame!!
        val currentPlayer = game.spieler[game.spielerIndex]

        //get card that player have chose to swap

        val playerCard = currentPlayer.handKarte[karteSpieler]
        // speichere die zu tauschenden Karten in die Variablen[save]
        //get one mitte card that have index[KarteMitte]
        val mitteCard = game.mitteKarten[karteMitte]
        //swap
        currentPlayer.handKarte[karteSpieler] = mitteCard
        game.mitteKarten[karteMitte] = playerCard

        // refreshable
        onAllRefreshable { refreshAfterCardSwaps() }
        zugBeenden()


    }


    /**
     * increments the pass counter by 1 and can execute the function [zugBeenden]
     */
    fun passen() {
        val game = rootService.currentGame!!

        //Es wurde ein weiteres Mal gepasst
        game.passenAnzahl++

        //falls alle Spieler gepasst haben

        if ((game.passenAnzahl) == (game.spieler.size)) {
            game.passenAnzahl = 0

            //falls nicht genügend Spielkarten auf dem Nachziehstapel sind
            if (game.nachziehStapel.size < 3) {
                onAllRefreshable { refreshAfterGameEnds() }
                endgame()
            }

            //drawThree 3 new card in mitte
            else {
                game.mitteKarten = drawThree(game.nachziehStapel)
                onAllRefreshable { refreshAfterTurnEnd(false) }
            }
        }
        zugBeenden()

    }


    /**
     * This function checks if the active player has [klopfen] or not,and can call the function[endgame]
     */
    fun klopfen() {
        val game = rootService.currentGame!!

        //get currentPlayer
        val currentPlayer = game.spieler[game.spielerIndex]
        currentPlayer.klopfen = true

        onAllRefreshable { refreshAfterTurnEnd(false) }
        zugBeenden()

    }

    /**
     * A function that controls the logic of the entire game and the players´actions
     */

    private fun zugBeenden() {

        val game = rootService.currentGame!!

        game.spielerIndex++
        game.spielerIndex = game.spielerIndex % game.spieler.size

        if (game.spieler[rootService.currentGame!!.spielerIndex].klopfen) {
            endgame()
        } else if (game.nachziehStapel.size < 3) {
            endgame()
        } else {
            onAllRefreshable { refreshAfterTurnEnd(false) }
        }

    }

    /**
     * A function that return list of first 3 card
     */
    private fun drawThree(cards: MutableList<Karte>): MutableList<Karte> {

        val draw = mutableListOf<Karte>()
        for (i in 0..2) {
            draw.add(cards.removeFirst())
        }
        return draw

    }

    /**
     * A function that end the game
     */
    fun endgame() {
        this.onAllRefreshable { refreshAfterTurnEnd(true) }
        this.onAllRefreshable { refreshAfterGameEnds() }
    }
}



