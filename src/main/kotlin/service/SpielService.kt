package service

import entity.*

/**
 * A service layer class that provides the logic for actions
 * that are not directly related to a single player
 */

class SpielService(private val rootService: RootService) : AbstractRefreshableService() {

    /**
     * Creates a shuffled 32 cards list of all four suits and cards
     * from 7 to Ace
     */
    fun defaultRandomCardList() = MutableList(32) { index ->
        Karte(
            CardSuit.values()[index / 8], CardValue.values()[(index % 8) + 5]
        )
    }.shuffled()


    /**
     * * A function that creates all the players for our game (minimum 2 players and maximum 4 players).
     */
    fun createPlayers(spielerName: MutableList<String>, allCards: MutableList<Karte>): MutableList<Spieler> {
        // defines a list of players with the same size as the list of names
        val listOfPlayers = mutableListOf<Spieler>()

        if (spielerName.size !in 2..4) println("ERROR: Number of Players must be 2, 3 or 4 !!")
        else {
            for (name in spielerName) {
                listOfPlayers.add(Spieler(name, false, drawThree(allCards)))
            }

        }
        return listOfPlayers
    }

    /**
     * A function that return list of last 3 [cards]
     */
    fun drawThree(cards: MutableList<Karte>): MutableList<Karte> {

        val draw=mutableListOf<Karte>()
        for (i in 0..2) {
            draw.add(cards.removeFirst())
        }
        return draw

    }


    /**
     * A function that start the game
     */
    fun spielStarten(spielerName: MutableList<String>) {
        val allCards = defaultRandomCardList()
        val playerList = createPlayers(spielerName, allCards as MutableList<Karte>)
        val middleCards = drawThree(allCards)

        //initialize the game
        this.rootService.currentGame = Schwimmen(0, 0)
        this.rootService.currentGame!!.nachziehStapel = allCards
        this.rootService.currentGame!!.spieler = playerList
        this.rootService.currentGame!!.mitteKarten = middleCards

        onAllRefreshable { refreshAfterStartGame() }
    }

    /**
     * A function that calculates score of one card
     */
    private fun scoreOneCard(c: Karte): Double {
        return when (c.karteWert) {
            CardValue.SEVEN -> 7.0
            CardValue.EIGHT -> 8.0
            CardValue.NINE -> 9.0
            CardValue.TEN, CardValue.JACK, CardValue.QUEEN, CardValue.KING -> 10.0
            CardValue.ACE -> 11.0
            else -> 0.0
        }
    }


    /**
     * A function that return player score
     */
    fun punkteBerechnen(spielerHandDeck: MutableList<Karte>): Double {


        // a set of a possible three cards (king or queen or jack)
        val set = setOf(CardValue.KING, CardValue.QUEEN, CardValue.JACK)

        // c1, c2 and c3 define the three cards in the hand of the player
        val c1: Karte = spielerHandDeck[0]
        val c2: Karte = spielerHandDeck[1]
        val c3: Karte = spielerHandDeck[2]
        // case1: the same suit (the ACE card is equals to 11 points)
        return if (c1.karteFarbe == c2.karteFarbe && c1.karteFarbe == c3.karteFarbe) {
            scoreOneCard(c1) + scoreOneCard(c2) + scoreOneCard(c3)
        }
        // case2: three cards with th same number/value
        else if ((c1.karteWert == c2.karteWert) && (c1.karteWert == c3.karteWert)) {
            30.5
        }
        // case3: return 10 points
        else if (c1.karteWert in set && c2.karteWert in set && c3.karteWert in set) {
            10.0
        }

        // case4: the normal case
        else {
            scoreOneCard(c1) + scoreOneCard(c2) + scoreOneCard(c3) - 10.0
        }
    }


}



