
var someTool = ToolFactory.rectangle(20, 20);
var someBrush = brush("wall");

var inserter = canvas.getInserter().with(someBrush);


// apply(int x, int y, BrushInserter<?> inserter)

someTool.apply(10, 5, inserter);
someTool.apply(50, 20, inserter);


// stroke(int x1, int y1, int x2, int y2, BrushInserter<?> inserter)

someTool.stroke(20, 80, 200, 50, inserter);
