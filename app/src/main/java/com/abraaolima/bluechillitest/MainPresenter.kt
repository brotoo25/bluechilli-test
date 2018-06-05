package com.abraaolima.bluechillitest

import android.graphics.Color
import kotlinx.coroutines.experimental.async
import java.io.ByteArrayInputStream
import java.net.URL
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory

class MainPresenter {

    private val random = Random()
    private val MIN_SIZE = 50
    private val MAX_SIZE = 400

    suspend fun generateNewShape(): Shape {
        val randomShape = ShapeType.values()[random.nextInt(ShapeType.values().size)]
        val randomSize = random.nextInt(MAX_SIZE + 1 - MIN_SIZE) + MIN_SIZE

        val randomColor =
                try {
                    fetchRemoteColor()
                } catch (ex: Exception) {
                    Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
                }

        return Shape(randomShape, randomSize, randomColor)
    }

    suspend fun updateColor(shape: Shape) : Shape {
        val randomColor =
                try {
                    fetchRemoteColor()
                } catch (ex: Exception) {
                    Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
                }

        return shape.copy(color = randomColor)
    }

    private suspend fun fetchRemoteColor(): Int {
        return async {
            val xml = URL("http://www.colourlovers.com/api/colors/random")

            val newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            val parse = newDocumentBuilder.parse(ByteArrayInputStream(xml.readBytes()))
            val hexCode = parse.getElementsByTagName("hex").item(0).textContent

            Color.parseColor("#$hexCode")
        }.await()
    }
}