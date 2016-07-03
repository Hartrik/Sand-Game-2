
Turtle.create(ToolFactory.centeredSquare(1))
    .setX(canvas.getWidth() / 2 + 300)
    .setAngle(305)
    
    .repeat(50)
        .repeat(365)
            .draw(2)
            .left(0.99)
        .end()
        .move(10)
    .end()