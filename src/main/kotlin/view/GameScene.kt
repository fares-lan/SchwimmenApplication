package view

import entity.*
import service.CardImageLoader
import service.RootService
import tools.aqua.bgw.components.container.CardStack
import tools.aqua.bgw.components.container.LinearLayout
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.visual.ImageVisual
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color.BLACK


/**
 * This class defines the game scene that extends the [BoardGameScene]
 */
class GameScene(private val rootService: RootService) : BoardGameScene(1920, 1080), Refreshable {

    // defines the index of the active player´s clicked card
    private var clickedPlayerCardIndex: Int = -1

    // defines the index of the middle deck´s clicked card
    private var clickedMiddleCardIndex: Int = -1


    //----------------------------------- Infos -----------------------------------//


    /**
     * This [Label] shows the number of passed players
     */

    private val passedPlayers = Label(
        posX = 20,
        posY = 20,
        width = 500,
        height = 70,
        alignment = Alignment.CENTER_LEFT,
        font = Font(size = 26, BLACK),
    )

    /**
     * This [Label] shows if the active player already knocked or not
     */
    private val wurdeGeklopft = Label(
        posX = 20,
        posY = 80,
        width = 500,
        height = 70,
        alignment = Alignment.CENTER_LEFT,
        font = Font(size = 26, BLACK),
    )

    /**
     * This [Label] shows the number of remaing cards
     */

    private val numberOfRemaingCard = Label(
        posX = 1600,
        posY = 160,
        width = 130,
        height = 400,
        alignment = Alignment.CENTER,
        font = Font(size = 26, BLACK, fontWeight = Font.FontWeight.BOLD),

        ).apply {
        isVisible = true
        ColorVisual(157, 224, 132)
    }


    //-----------------------------------Active Player-----------------------------------//

    /**
     * This [Label] shows the active player name
     */
    private val activePlayerName = Label(
        posX = 700,
        posY = 650,
        width = 500,
        height = 70,
        alignment = Alignment.CENTER,
        font = Font(size = 26, BLACK, fontWeight = Font.FontWeight.BOLD),
        text = "Spielername"
    )

    /**
     * A list of [String]s that stores every cardView with its name.
     *
     * This helps to determine the index of the clicked card.
     */
    private val cardViewList = mutableListOf<CardView>()

    /**
     * represents the active player´s cards as [LinearLayout]
     */
    private val activePlayerCards = LinearLayout<CardView>(
        posX = 650, posY = 450, width = 600, height = 200, spacing = 20, alignment = Alignment.CENTER
    ).apply {
        isVisible = true
        //visual = ColorVisual(228, 242, 223)
        onMouseClicked = {

            // adds every cardView to the list and shows the front side
            forEach { cardView ->
                cardView.showFront()
                cardViewList.add(cardView)
            }

            // defines the index of the player´s clicked card
            defineIndex()
        }
    }

    /**
     * This function determines the index of the clicked card in the player´s hand deck
     */
    private fun defineIndex() {
        cardViewList.forEach { cardView ->
            cardView.apply {
                onMouseClicked = {
                    if (cardView == cardViewList[0]) {
                        clickedPlayerCardIndex = 0
                    }

                    if (cardView == cardViewList[1]) {
                        clickedPlayerCardIndex = 1
                    }

                    if (cardView == cardViewList[2]) {
                        clickedPlayerCardIndex = 2
                    }
                }
            }
        }

    }

    /**
     * A function that gives to every card in the playerHandDeck its front and back view
     * and determines the index of the clicked card
     */
    private fun activePlayerCardsInitialize(
        playerCards: MutableList<Karte>, cardImageLoader: CardImageLoader,
        playerLinearLayout: LinearLayout<CardView>,
    ) {
        playerLinearLayout.clear()  // clears the hand deck of the active player

        playerCards.forEach { card ->
            val cardView = CardView(
                height = 200,
                width = 130,
                front = ImageVisual(cardImageLoader.frontImageFor(card.karteFarbe, card.karteWert)),
                back = ImageVisual(cardImageLoader.backImage)
            ).apply {
                showFront()
            }

            playerLinearLayout.add(cardView)  // adds the current cardView to the linear layout of the player
        }
    }

    //-----------------------------------Other Players-----------------------------------//

    /**
     * represents the cards of the second player as a [LinearLayout]
     */
    private val secondPlayerCards = LinearLayout<CardView>(
        posX = 770, posY = 60,
        width = 400, height = 100,
        spacing = 3,
        alignment = Alignment.CENTER
    )

    /**
     * represents the cards of the third player as a [LinearLayout], if the player exists
     */
    private val thirdPlayerCards = LinearLayout<CardView>(
        posX = -60, posY = 500,
        width = 400, height = 100,
        spacing = 3,
        alignment = Alignment.CENTER
    )

    /**
     * represents the cards of the fourth player as a [LinearLayout], if the player exists
     */
    private val fourthPlayerCards = LinearLayout<CardView>(
        posX = 1620, posY = 500,
        width = 400, height = 100,
        spacing = 3,
        alignment = Alignment.CENTER
    )


    //-----------------------------------Middle Deck-----------------------------------//

    /**
     * This [Label] shows the middle deck name/string
     */
    private val middleDeckName = Label(
        posX = 700,
        posY = 50,
        width = 500,
        height = 70,
        alignment = Alignment.CENTER,
        font = Font(size = 26, BLACK, fontWeight = Font.FontWeight.BOLD),
        text = "Middle Deck"
    )


    /**
     * represents the middle deck cards as a [LinearLayout]
     */
    private val middleDeckCards = LinearLayout<CardView>(
        posX = 650, posY = 120, width = 600, height = 200, spacing = 20, alignment = Alignment.CENTER
    ).apply {
        isVisible = true
    }


    /**
     * A function that gives to every card in the [middleDeck] its front and back view
     * and determines the index of the clicked card
     */
    private fun middleDeckCardsInitialize(
        middleDeck: MutableList<Karte>, cardImageLoader: CardImageLoader,
        middleLinearLayout: LinearLayout<CardView>,
    ) {

        middleLinearLayout.clear()  // clears the middle deck

        // this will assign for every card in the middle deck layout a front and back [cardImageLoader]
        middleDeck.forEach { card ->
            val cardView = CardView(
                height = 200,
                width = 130,
                front = ImageVisual(cardImageLoader.frontImageFor(card.karteFarbe, card.karteWert)),
                back = ImageVisual(cardImageLoader.backImage)
            ).apply {

                showFront()
                onMouseClicked = {

                    if (card == middleDeck[0]) {
                        clickedMiddleCardIndex = 0
                    }

                    if (card == middleDeck[1]) {
                        clickedMiddleCardIndex = 1
                    }

                    if (card == middleDeck[2]) {
                        clickedMiddleCardIndex = 2
                    }

                }

            }

            middleLinearLayout.add(cardView) // this adds the current cardView to the middle deck´s linear layout
        }

    }


    //-----------------------------------Draw Pile-----------------------------------//

    /**
     * represents the draw pile
     */
    private val drawPile = CardStack<CardView>(
        posX = 1600, posY = 120, width = 130, height = 200, alignment = Alignment.CENTER
    ).apply {
        isVisible = true
        visual = ColorVisual(232, 245, 137)
    }

    /**
     * A function that gives to every card in the [drawPile] its front and back view
     */
    private fun drawPileInitialize(
        drawPile: MutableList<Karte>, cardImageLoader: CardImageLoader,
        stack: CardStack<CardView>,
    ) {
        stack.clear()  // clears the draw pile

        drawPile.forEach { card ->
            val cardView = CardView(
                height = 200,
                width = 130,
                front = ImageVisual(cardImageLoader.frontImageFor(card.karteFarbe, card.karteWert)),
                back = ImageVisual(cardImageLoader.backImage)
            ).apply {
                showBack()
            }

            stack.add(cardView) // this adds the current cardView to the draw pile´s linear layout
        }

    }

    //-----------------------------------Action Buttons-----------------------------------//

    /**
     * represents the pass button
     */
    private val passButton = Button(
        posX = 1000,
        posY = 850,
        width = 250,
        height = 70,
        text = "Passen",
        font = Font(size = 26, fontWeight = Font.FontWeight.NORMAL, color = BLACK),
        visual = ColorVisual(228, 242, 223)
    ).apply {
        isVisible = true
        onMouseClicked = {
            rootService.spielerService.passen()
        }
    }

    /**
     * represents the knock button
     */
    private val knockButton = Button(
        posX = 1350,
        posY = 850,
        width = 250,
        height = 70,
        text = "Klopfen",
        font = Font(size = 26, fontWeight = Font.FontWeight.NORMAL, color = BLACK),
        visual = ColorVisual(228, 242, 223)
    ).apply {
        isVisible = true
        onMouseClicked = {
            rootService.spielerService.klopfen()
        }
    }

    /**
     * represents the change-one-card button that changes only
     * one card from the hand of the active player with a card
     * from the middle deck.
     */
    private val changeOneCardButton = Button(
        posX = 650,
        posY = 850,
        width = 250,
        height = 70,
        text = "Eine tauschen",
        font = Font(size = 26, fontWeight = Font.FontWeight.NORMAL, color = BLACK),
        visual = ColorVisual(228, 242, 223)
    ).apply {
        isVisible = true
        onMouseClicked = {
            rootService.spielerService.eineKarteTauschen(clickedPlayerCardIndex, clickedMiddleCardIndex)
        }
    }

    /**
     * represents the change-all-cards button that changes
     * all cards from the hand of the active player with all
     * the cards from the middle deck.
     */
    private val changeAllCardsButton = Button(
        posX = 310,
        posY = 850,
        width = 250,
        height = 70,
        text = "Alle tauschen",
        font = Font(size = 26, fontWeight = Font.FontWeight.NORMAL, color = BLACK),
        visual = ColorVisual(228, 242, 223)
    ).apply {
        isVisible = true
        onMouseClicked = {
            rootService.spielerService.alleKartenTauschen()


        }
    }


    //------------------------Refreshable--------------------//


    /**
     * This function refreshes the middleDeck and the playerHandDeck when it is called
     */
    override fun refreshAfterCardSwaps() {

        val game = rootService.currentGame!!


        val activePlayerIndex = game.spielerIndex
        val cardImageLoader = CardImageLoader()



        passedPlayers.text = "Spieler gepasst: " + game.passenAnzahl.toString()
        wurdeGeklopft.text =
            "Wurde geklopft? " + game.spieler[game.spielerIndex].klopfen

        numberOfRemaingCard.text = game.nachziehStapel.size.toString()
        middleDeckCardsInitialize(game.mitteKarten, cardImageLoader, middleDeckCards)
        activePlayerCardsInitialize(game.spieler[activePlayerIndex].handKarte, cardImageLoader, activePlayerCards)

    }


    /**
     * This function makes the necessary changes and refreshes to start the game
     */
    override fun refreshAfterStartGame() {

        // clears every existing layout before starting the game and assigning new cards
        drawPile.clear()
        activePlayerCards.clear()
        secondPlayerCards.clear()
        middleDeckCards.clear()


        // knock button
        knockButton.apply { isDisabled = false }


        // initialize the current game
        val game = rootService.currentGame
        checkNotNull(game)

        val activePlayerIndex = game.spielerIndex
        val cardImageLoader = CardImageLoader()

        // shows the active player name
        activePlayerName.apply {
            isVisible = true
            text = game.spieler[activePlayerIndex].name
        }

        // in this part we call and initialize every layout that we need to start the game
        activePlayerCardsInitialize(game.spieler[activePlayerIndex].handKarte, cardImageLoader, activePlayerCards)
        middleDeckCardsInitialize(game.mitteKarten, cardImageLoader, middleDeckCards)
        drawPileInitialize(game.nachziehStapel, cardImageLoader, drawPile)


        passedPlayers.text = "Spieler gepasst: " + game.passenAnzahl.toString()
        wurdeGeklopft.text =
            "Wurde geklopft? " + game.spieler[game.spielerIndex].klopfen

        numberOfRemaingCard.text = game.nachziehStapel.size.toString()
    }

    /**
     * This function refreshes the game when it is finished
     */
    override fun refreshAfterTurnEnd(hasGameEnded: Boolean) {

        // this holds the current game
        val game = rootService.currentGame!!

        passedPlayers.text = "Spieler gepasst: " + game.passenAnzahl.toString()
        wurdeGeklopft.text = "Wurde geklopft? " + game.spieler[game.spielerIndex].klopfen
        numberOfRemaingCard.text = game.nachziehStapel.size.toString()

        val activePlayerIndex = game.spielerIndex
        val cardImageLoader = CardImageLoader()

        if (!hasGameEnded) {
            activePlayerName.apply {
                text = game.spieler[activePlayerIndex].name
            }
            clickedMiddleCardIndex = -1
            activePlayerCardsInitialize(game.spieler[activePlayerIndex].handKarte, cardImageLoader, activePlayerCards)
            middleDeckCardsInitialize(game.mitteKarten, cardImageLoader, middleDeckCards)

        }
    }


    /**
     * Initialization of the swimming game scene
     */
    init {
        opacity = 1.0
        background = ColorVisual(157, 224, 132)
        addComponents(
            activePlayerName,
            passedPlayers,
            wurdeGeklopft,
            middleDeckCards,
            drawPile,
            passButton,
            knockButton,
            middleDeckName,
            changeOneCardButton,
            changeAllCardsButton,
            activePlayerCards,
            secondPlayerCards,
            thirdPlayerCards,
            fourthPlayerCards,
            numberOfRemaingCard
        )
    }

}