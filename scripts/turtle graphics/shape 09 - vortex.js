
Turtle.create(ToolFactory.centeredSquare(1))
    .repeat(56)
        .center()
        .repeat(400)
            .draw(1)
            .left(.5)
        .end()
        .repeat(5)
            .draw(1)
            .right(.7)
        .end()
    .end();
