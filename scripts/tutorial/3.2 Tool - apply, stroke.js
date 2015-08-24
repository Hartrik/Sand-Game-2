
// Každý nástroj obsahuje metodu apply(int x, int y, BrushInserter<?> inserter)
// použití:

// libovolný nástroj...
var rectangle = ToolFactory.rectangle(20, 20);

// vytvoření instance třídy BrushInserter
var ironBrush = brush("železo");
var inserter = canvas.getInserter().with(ironBrush);

rectangle.apply(10, 5, inserter);
rectangle.apply(50, 20, inserter);


// Některé nástroje (všechny tvary) podporují nanášení tahem metodou
//   stroke(int x1, int y1, int x2, int y2, BrushInserter<?> inserter)

rectangle.stroke(20, 80, 200, 50, inserter);