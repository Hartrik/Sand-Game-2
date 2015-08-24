
var airBrush = brush(1);
var predicate = function(e) Elements.isWater(e);

canvas.getTools().replaceAll(predicate, airBrush);