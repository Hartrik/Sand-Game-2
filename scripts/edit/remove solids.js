
var airBrush = brush(1);
var predicate = function(e) Elements.isSolid(e);

canvas.getTools().replaceAll(predicate, airBrush);