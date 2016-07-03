
turtle = Turtle.create(ToolFactory.centeredSquare(1))
spiral(turtle, 8, 0.04, 0, 30)

function spiral(turtle, angle, add, side, maxSide) {
    if (side <= maxSide) {
        turtle.draw(side)
              .right(angle)

        spiral(turtle, angle, add, side + add, maxSide)
    }
}