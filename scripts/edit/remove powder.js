
var airBrush = brush(1);
var predicate = function(e) Elements.isPowder(e);

canvas.getTools().replaceAll(predicate, airBrush);