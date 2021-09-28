package com.example.mob3000_frisbeegolf.models

class Round (
    val ArenaName: String,
    val HoleList: ArrayList<HoleList>,
    val Username: String,
        )

class HoleList(
    val HoleNr: Int,
    val Throws: Int,
)