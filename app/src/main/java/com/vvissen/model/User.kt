package com.vvissen.model

data class User(
        val name: String = "",
        val photo: String = "",
        val interest: Interest = Interest.Women) {
}