package com.rsmbyk.course.mdp.data.api.volley.parser

interface Parser<T> {
    fun parse (response: String): T
}
