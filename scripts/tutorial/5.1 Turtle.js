
// VYTVOŘENÍ INSTANCE

// nástroj kterým se bude kreslit
var tool = ToolFactory.centeredSquare(5)
var brush = brushManager.getBrush("železo")

var turtle_1 = Turtle.create(tool)         // použije primární štětec
var turtle_2 = Turtle.create(tool, brush)  // použije jiný štětec


// KRESLENÍ

// nakreslí uprostřed obrazovky čtverec o stranách 100 bodů

var turtle = Turtle.create(tool, brush)
turtle.draw(100)
turtle.left()
turtle.draw(100)
turtle.left()
turtle.draw(100)
turtle.left()
turtle.draw(100)

// rozhraní ale umí být velice plynulé:

Turtle.create(tool, brush)
    .draw(100)
    .left()
    .draw(100)
    .left()
    .draw(100)
    .left()
    .draw(100)

// ... a podporuje cykly

Turtle.create(tool, brush)
    .repeat(4)
        .draw(100)
        .left()
    .end()