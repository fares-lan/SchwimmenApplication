package view

import service.SpielService
import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

/**
 * A class for the Scoreboard menu scene which extends [MenuScene]
 */
class EndScene(private val rootService: RootService) : MenuScene(1000, 1080), Refreshable {

    /**
     * [Label] that displays the title of the endScene
     */
    private val headlineLabel = Label(
        width = 300, height = 50, posX = 350, posY = 50, alignment = Alignment.CENTER,
        text = "Game over",
        font = Font(size = 40, Color(0, 0, 0))
    )

    /**
     * [Label] shows the score of the first player
     */
    private val score1 = Label(
        width = 600, height = 100,
        posX = 200, posY = 200,
        text = "score1",
        font = Font(size = 50, color = Color.BLACK)
    ).apply {
        isVisible = true
        scale = 0.8
    }

    /**
     * [Label] shows the score of the second player
     */
    private val score2 = Label(
        width = 600, height = 100,
        posX = 200, posY = 300,
        text = "score2",
        font = Font(size = 50, color = Color.BLACK)
    ).apply {
        isVisible = true
        scale = 0.8
    }

    /**
     * [Label] shows the score of the third player
     */
    private val score3 = Label(
        width = 600, height = 100,
        posX = 200, posY = 400,
        text = "score3",
        font = Font(size = 50, color = Color.BLACK)
    ).apply {
        isVisible = false
        scale = 0.8
    }

    /**
     * [Label] shows the score of the fourth player
     */
    private val score4 = Label(
        width = 600, height = 100,
        posX = 200, posY = 500,
        text = "score4",
        font = Font(size = 50, color = Color.BLACK)
    ).apply {
        isVisible = false
        scale = 0.8
    }


    /**
     * [Button] that the game with the same players but with a new stack of cards
     */
    val restartButton = Button(
        width = 250, height = 90,
        posX = 200, posY = 890,
        text = "Neustart",
        font = Font(size = 38, color = Color.BLACK),
        visual = ColorVisual(0, 200, 0)
    ).apply {
        isVisible = true
    }

    /**
     * [Button] that quits the game and takes us to the start menu scene
     */
    val quitButton = Button(
        width = 250, height = 90,
        posX = 550, posY = 890,
        text = "Beenden",
        font = Font(size = 38, color = Color.BLACK),
        visual = ColorVisual(200, 0, 0)
    ).apply {
        isVisible = true
    }


    /**
     * refreshes the scene when endTurn() is called
     */
    override fun refreshAfterTurnEnd(hasGameEnded: Boolean) {

        // defines the current game
        val game = rootService.currentGame!!

        // variables that will be used to check the number of players
        var exist3 = false
        var exist4 = false

        // in case there are 3 players
        if (game.spieler.size == 3) {
            exist3 = true
            score3.isVisible = true
        }

        // in case there are 4 players
        if (game.spieler.size == 4) {
            exist3 = true
            score3.isVisible = true

            exist4 = true
            score4.isVisible = true
        }

        // variables that store the score of each player
        val player1Score = SpielService(rootService).punkteBerechnen(game.spieler[0].handKarte)
        val player2Score = SpielService(rootService).punkteBerechnen(game.spieler[1].handKarte)
        val player3Score =
            if (exist3) SpielService(rootService).punkteBerechnen(game.spieler[2].handKarte) else 0.0
        val player4Score =
            if (exist4) SpielService(rootService).punkteBerechnen(game.spieler[3].handKarte) else 0.0


        // defines the best score
        val bestScore = when (game.spieler.size) {
            3 -> maxOf(player1Score, player2Score, player3Score)
            4 -> maxOf(player1Score, player2Score, player3Score, player4Score)
            else -> maxOf(player1Score, player2Score)
        }

        // makes the winner label bigger
        when (bestScore) {
            player1Score -> {
                score1.scale = 1.2
            }

            player2Score -> {
                score2.scale = 1.2
            }

            player3Score -> {
                score3.scale = 1.2
            }

            player4Score -> {
                score4.scale = 1.2
            }
        }

        if (hasGameEnded) {
            // printing the texts
            score1.text = "${game.spieler[0].name} : $player1Score points"
            score2.text = "${game.spieler[1].name} : $player2Score points"
            score3.text = if (exist3) "${game.spieler[2].name} : $player3Score points" else ""
            score4.text = if (exist4) "${game.spieler[3].name} : $player4Score points" else ""
        }

    }


    /**
     * Initialization of the ScoreBoardMenuScene
     */
    init {
        opacity = .4
        background = ColorVisual(235, 236, 237)
        addComponents(
            headlineLabel,
            score1, score2, score3, score4,
            restartButton, quitButton
        )
    }
}