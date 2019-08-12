package com.roaim.pindownloader.mock

import com.google.gson.Gson
import com.google.gson.JsonElement

object MockJson {
    const val string = "{\"id\":\"123\",\"nama\":\"Juliar Nasution\",\"age\" : 24}"

    val jsonElement: JsonElement = Gson().fromJson(string, JsonElement::class.java)

    val poJo = Gson().fromJson(jsonElement, MockPoJo::class.java)!!
}

data class MockPoJo(val id: Int, val name: String, val age: Int)
