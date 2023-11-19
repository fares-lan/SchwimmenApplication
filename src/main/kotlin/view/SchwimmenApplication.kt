package view


import service.RootService
import tools.aqua.bgw.core.BoardGameApplication

/**
 * This is a class of the Swimming Application that extends the [BoardGameApplication]
 * and allows accessing other MenuScenes
 */
class SchwimmenApplication : BoardGameApplication("Swimming_fares"), Refreshable {


    // Central service from which all others are created/accessed
    // also holds the currently active game
    private val rootService = RootService()

    //---------------------------SCENES----------------------------------//

    /**
     * this represents the [startMenuScene]
     */
    private val startMenuScene: StartScene = StartScene(rootService).apply {
        startButton.onMouseClicked = {
            val game = rootService.spielService  // this holds the game service

            // starts the game with just two players
            if (p3Input.text.isBlank()) {
                game.spielStarten(
                    listOf(p1Input.text.trim(), p2Input.text.trim()).toMutableList()
                )
            }

            // starts the game with just three players
            if (p3Input.text.isNotBlank() && p4Input.text.isBlank()) {
                game.spielStarten(
                    listOf(p1Input.text.trim(), p2Input.text.trim(), p3Input.text.trim()).toMutableList()
                )
            }

            // starts the game with four players as expected
            if (p3Input.text.isNotBlank() && p4Input.text.isNotBlank()) {
                game.spielStarten(
                    listOf(
                        p1Input.text.trim(), p2Input.text.trim(), p3Input.text.trim(),
                        p4Input.text.trim()
                    ).toMutableList()
                )
            }

            this@SchwimmenApplication.hideMenuScene()
            this@SchwimmenApplication.showGameScene(gameScene)
        }

        quitButton.onMouseClicked = {
            exit() // this exits the current game
        }

    }

    /**
     * This represents the [GameScene]
     */
    private val gameScene = GameScene(rootService)


    /**
     * This represents the [EndScene] that indicates the end of
     * the game and shows the scores of the players
     */
    private val endScene = EndScene(rootService).apply {
        restartButton.onMouseClicked = {
            this@SchwimmenApplication.hideMenuScene()
            this@SchwimmenApplication.showMenuScene(startMenuScene)
        }
        quitButton.onMouseClicked = {
            exit()
        }
    }
    //---------------------------Refreshable----------------------------------//
    /**
     * This function shows the start menu scene
     */
    override fun refreshAfterStartGame() {
        this@SchwimmenApplication.hideMenuScene()
        this@SchwimmenApplication.showGameScene(gameScene)
    }

    /**
     * This functions shows the score menu scene after the game is finished
     */

    override fun refreshAfterGameEnds() {
        this@SchwimmenApplication.hideMenuScene()
        this@SchwimmenApplication.showMenuScene(endScene)
    }

    /**
     * Initialization of the SwimmingApplication
     */
    init {
        rootService.addRefreshables(
            startMenuScene,
            this,
            gameScene,
            endScene
        )
        this.showMenuScene(startMenuScene)

    }


}
