
var airBrush = brush(1);
var predicate = function(e) Elements.isNonSolid(e);

canvas.getTools().replaceAll(predicate, airBrush);