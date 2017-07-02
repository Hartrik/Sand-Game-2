
var airBrush = brush(1);
var predicate = function(e) { return Elements.isNonSolid(e) };

canvas.getTools().replaceAll(predicate, airBrush);
