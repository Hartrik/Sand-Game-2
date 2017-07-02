
var airBrush = brush(1);
var predicate = function(e) { return Elements.isPlant(e) };

canvas.getTools().replaceAll(predicate, airBrush);
