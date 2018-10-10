package com.rsmbyk.course.mdp.data.api.volley.parambuilder

interface ParameterBuilder<T> {
    fun build (request: T): MutableMap<String, String>
}
