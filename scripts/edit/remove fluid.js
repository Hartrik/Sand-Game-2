
var airBrush = brush(1);
var predicate = function(e) { return Elements.isFluid(e) };

canvas.getTools().replaceAll(predicate, airBrush);
