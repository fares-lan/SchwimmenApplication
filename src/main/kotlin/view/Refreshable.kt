package view

/**
 * This Interface provides a mechanism for the service layer to communicate
 * with the view layer, which update the UI according to the taken actions.
 */
interface Refreshable {


    /**
     * perform refreshes after a new gam started
     */
    fun refreshAfterStartGame() {}

    /**
     * refreshes the UI after all the cards are changed
     */
    fun refreshAfterCardSwaps() {}


    /**
     * perform refreshes after turn End
     */
    fun refreshAfterTurnEnd(hasGameEnded: Boolean) {}

    /**
     * perform refreshes after game End
     */
    fun refreshAfterGameEnds() {}
}