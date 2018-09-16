package com.rsmbyk.course.mdp.data.model

enum class OperatorData (val func: (Int, Int) -> Int) {

    ADD ({ x, y -> x + y }),
    SUBTRACT ({ x, y -> x - y }),
    MULTIPLY ({ x, y -> x * y }),
    DIVIDE ({ x, y -> x / y })
}
