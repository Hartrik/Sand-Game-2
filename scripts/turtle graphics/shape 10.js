var cycles = 180;
            
Turtle.create(ToolFactory.centeredSquare(1))
    .repeat(cycles)
        .center()
        .repeat(100)
            .move(3)
            .draw(3)
        .end()
        .left(360/cycles)
    .end();
