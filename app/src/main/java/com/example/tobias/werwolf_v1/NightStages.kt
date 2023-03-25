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
    WHITE_WOLF(8),
    URWOLF(9),
    WITCH(10),
    EVALUATE_NIGHT(11),
    ELECTION_DAY(12),
    KILL_DAY(13),
    GIRL(14),
    HUNTER(15);

    companion object {
        fun fromInt(value: Int) = NightStages.values().first { it.value == value }
    }
}
