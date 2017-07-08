
Turtle.create(ToolFactory.centeredSquare(1))
    .setX(canvas.getWidth() / 2 - 150)
    .setY(canvas.getHeight() / 2 + 75)
    .run(function(t) { regularPolygon(t, 3, 150) }) // triangle
    .run(function(t) { regularPolygon(t, 4, 150) }) // rectangle
    .run(function(t) { regularPolygon(t, 5, 150) }) // ...
    .run(function(t) { regularPolygon(t, 6, 150) })
    .run(function(t) { regularPolygon(t, 7, 150) })
    .run(function(t) { regularPolygon(t, 8, 150) })

function regularPolygon(turtle, numberOfSides, sideLength) {
    turtle
        .repeat(numberOfSides)
            .draw(sideLength)
            .right(360 / numberOfSides)
        .end();
}
