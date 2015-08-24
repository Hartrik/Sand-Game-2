
var airBrush = brush(1);
var predicate = function(e) Elements.isFluid(e);

canvas.getTools().replaceAll(predicate, airBrush);