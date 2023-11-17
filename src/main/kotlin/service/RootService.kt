package service

import entity.Schwimmen
import view.Refreshable


/**
 * This class defines the main class of the whole service layer for the Swimming game.
 * It provides access to all other service classes and holds the state of the [currentGame].
 */
class RootService {

    /**
     * holds the player action service
     */
    val spielerService = SpielerService(this)

    /**
     * holds the game service
     */
    val spielService = SpielService(this)

    /**
     * The current game can be `null´ if it is not started yet
     */
    var currentGame: Schwimmen? = null


    /**
     * Thi function adds the provided [newRefreshable] to all services connected
     * to the root service class
     */
    private fun addRefreshable(newRefreshable: Refreshable) {
        spielService.addRefreshable(newRefreshable)
        spielerService.addRefreshable(newRefreshable)
    }

    /**
     * This function adds for each of the provided new Refreshable to all
     * services connected to the root service class
     */
    fun addRefreshables(vararg newRefreshables: Refreshable) {
        newRefreshables.forEach { addRefreshable(it) }
    }
}