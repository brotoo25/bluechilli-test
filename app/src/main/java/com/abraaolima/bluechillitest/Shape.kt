package com.abraaolima.bluechillitest

data class Shape(val shapeType: ShapeType,
                 val size: Int,
                 val color: Int)

enum class ShapeType {
    CIRCLE,
    SQUARE
}