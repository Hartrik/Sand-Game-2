
var tool = ToolFactory.centeredSquare(5);
var brush = brush("wall");


// Draws a square (100x100) in the center of the canvas

var turtle = Turtle.create(tool, brush);
turtle.draw(100);
turtle.left();
turtle.draw(100);
turtle.left();
turtle.draw(100);
turtle.left();
turtle.draw(100);


// ... or the same fluently

Turtle.create(tool, brush)
    .draw(100)
    .left()
    .draw(100)
    .left()
    .draw(100)
    .left()
    .draw(100);


// ... or the same using cycles

Turtle.create(tool, brush)
    .repeat(4)
        .draw(100)
        .left()
    .end();
