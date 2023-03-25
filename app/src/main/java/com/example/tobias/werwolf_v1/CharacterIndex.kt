package com.example.tobias.werwolf_v1

enum class CharacterIndex(val value: Int) {
    WOLF(2),
    CITIZEN(1),
    AMOR(3),
    WITCH(4),
    GUARDIAN(5),
    GIRL(6),
    THIEF(7),
    HUNTER(8),
    KNIGHT(9),
    FLUTE(10),
    FRIENDS(11),
    WHITE_WOLF(12),
    W_CHILD(13),
    URWOLF(14);

    companion object {
        fun fromInt(value: Int) = CharacterIndex.values().first { it.value == value }
    }
}
