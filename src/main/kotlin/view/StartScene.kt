package view


import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color


/**
 * [MenuScene] that is used for starting a new game. It is displayed directly at program start.
 *  After providing the names of minimum two players,
 * [startButton] can be pressed. There is also a [quitButton] to end the program.
 */
class StartScene(private val rootService: RootService) : MenuScene(720, 400), Refreshable {


    private val headlineLabel = Label(
        width = 300, height = 50, posX = 200, posY = 50,
        text = "Schwimmen",
        font = Font(size = 30, Color(0,0,0))
    )

    /**
     *this show the [TextField]for "spieler 1"
     */
    val p1Input: TextField = TextField(
        width = 250, height = 35,
        posX = 70, posY = 140,
        prompt = "Spieler 1"
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = this.text.isBlank() || p2Input.text.isBlank()
        }
    }

    /**
     *this show the [TextField]for "spieler 2"
     */
    val p2Input: TextField = TextField(
        width = 250, height = 35,
        posX = 400, posY = 140,
        prompt = "Spieler 2"
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = this.text.isBlank() || p1Input.text.isBlank()
        }
    }

    /**
     *this show the [TextField]for "spieler 3"
     */
    val p3Input: TextField = TextField(
        width = 250, height = 35,
        posX = 70, posY = 215,
        prompt = "Spieler 3"
    )

    /**
     *this show the [TextField]for "spieler 4"
     */
    val p4Input: TextField = TextField(
        width = 250, height = 35,
        posX = 400, posY = 215,
        prompt = "Spieler 4"
    )

    //List l contains the names of players
    private val namenList = mutableListOf<String>()


    /**
     * This is a [Button] that allows us to start the game
     */
    val startButton = Button(
        width = 150, height = 30,
        posX = 285, posY = 290,
        text = "Spiel starten",
        font = Font(size = 15, color = Color.BLACK),
        visual = ColorVisual(0, 200, 0)
    ).apply {
        isDisabled = true
        onMouseClicked = {
            namenList.add(p1Input.text)
            namenList.add(p2Input.text)
            if (p3Input.text.isNotBlank()) {
                namenList.add(p3Input.text)
            }
            if (p4Input.text.isNotBlank()) {
                namenList.add(p4Input.text)
            }
            rootService.spielService.spielStarten(namenList)
        }
    }

    /**
     * This [Button] defines the exit-button that quits the game
     */
    val quitButton = Button(
        width = 100, height = 30,
        posX = 310, posY = 330,
        text = "Beenden",
        font = Font(size = 15, color = Color.BLACK),
        visual = ColorVisual(200, 0, 0)
    )

    /**
     * Initialization of the StartMenuScene
     */
    init {

        opacity = .5
        background = ColorVisual(235, 236, 237)
        addComponents(
            headlineLabel,
            p1Input,
            p2Input,
            p3Input,
            p4Input,
            startButton,
            quitButton
        )
    }
}




