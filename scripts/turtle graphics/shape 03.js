
Turtle.create(ToolFactory.centeredSquare(1))
    .repeat(36)
        .run(function(t) { regularPolygon(t, 5, 150) })  // pentagon
        .right(10)
    .end();

function regularPolygon(turtle, numberOfSides, sideLength) {
    turtle.repeat(numberOfSides)
            .draw(sideLength)
            .right(360 / numberOfSides)
        .end()
}
