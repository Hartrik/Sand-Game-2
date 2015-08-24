
var airBrush = brush(1);
var predicate = function(e) Elements.isPlant(e);

canvas.getTools().replaceAll(predicate, airBrush);