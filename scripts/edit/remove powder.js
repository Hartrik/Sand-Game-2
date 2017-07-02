
var airBrush = brush(1);
var predicate = function(e) { return Elements.isPowder(e) };

canvas.getTools().replaceAll(predicate, airBrush);
