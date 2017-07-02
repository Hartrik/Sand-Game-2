
var airBrush = brush(1);
var predicate = function(e) { return Elements.isGas(e) };

canvas.getTools().replaceAll(predicate, airBrush);
