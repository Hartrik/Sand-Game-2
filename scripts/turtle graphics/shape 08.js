
Turtle.create(ToolFactory.centeredSquare(1))
    .repeat(36)
        .repeat(100)
            .draw(canvas.getHeight() / 60)
            .right(4)
        .end()
        .right(10)
        .center()
    .end();