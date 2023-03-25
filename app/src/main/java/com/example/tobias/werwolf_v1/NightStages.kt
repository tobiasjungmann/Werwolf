package com.example.tobias.werwolf_v1

enum class NightStages(val value: Int) {
    AMOR(0),
    FRIENDS(1),
    THIEF(2),
    GUARDIAN(3),
    W_CHILD(4),
    SEHER(5),
    FLUTE(6),
    KNIGHT(7),
    WOLF(8),
    WHITE_WOLF(9),
    URWOLF(10),
    WITCH(11),
    EVALUATE_NIGHT(12),
    ELECTION_DAY(13),
    KILL_DAY(14),
    GIRL(15),
    HUNTER(16);

    companion object {
        fun fromInt(value: Int) = NightStages.values().first { it.value == value }
    }
}
