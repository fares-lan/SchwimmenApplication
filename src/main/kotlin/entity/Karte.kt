package entity

/**
 *[karteFarbe]: Farbe der Karte
 *[karteWert]: wert der Karte
 **/
class Karte(val karteFarbe: CardSuit, val karteWert: CardValue) {


    /**
     * a value getter
     */
    val getValue: CardValue
        get() = this.karteWert
}
