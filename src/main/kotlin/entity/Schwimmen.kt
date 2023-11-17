package entity

/**
 *[spielerIndex]:index der spieler
 *[passenAnzahl]:Pass Anzahl
 * [spieler]: list der spieler (2..4)
 * [mitteKarten]:list drei karte
 * [nachziehStapel] list von Karte(0..32)
 **/
class Schwimmen(var spielerIndex: Int, var passenAnzahl: Int){
    var spieler = mutableListOf<Spieler>()
    var mitteKarten = mutableListOf<Karte>()
    var nachziehStapel = mutableListOf<Karte>()
}