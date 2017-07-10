// Shows all brushes in a grid

var brushes = brushManager.getBrushesAll();

var H_COUNT = 8;
var V_COUNT = Math.ceil(brushes.size() / H_COUNT);

var H_SIZE = canvas.getWidth() / H_COUNT;
var V_SIZE = canvas.getHeight() / V_COUNT;

// Background
var wallBrush = brush(10);
canvas.getTools().fill(wallBrush);

var rectangle = ToolFactory.rectangle(H_SIZE - 2, V_SIZE - 2);
var i = 0;

brushes.forEach(function(next) {
    var x = i % H_COUNT;
    var y = Math.floor(i / H_COUNT);

    var rx = x * H_SIZE;
    var ry = y * V_SIZE;

    canvas.take(rectangle, rx + 1, ry + 1).getTools().fill(next);

    i++;
});
