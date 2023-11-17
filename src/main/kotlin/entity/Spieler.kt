package entity

/**
 *[name]: Name der Spieler
 *[klopfen]:return ob der Spieler geklopft hat oder nicht
 * [handKarte]:muss 3 karte sein
 **/

class Spieler(val name: String, var klopfen: Boolean, var handKarte: MutableList<Karte>)
