// Removes water above certain level

var waterLevel = 100;
var airBrush = brush(1);

var predicate = function(element, x, y) {
    return (y < canvas.getHeight() - waterLevel) && Elements.isWater(element)
};

canvas.getTools().replaceAll(predicate, airBrush);
