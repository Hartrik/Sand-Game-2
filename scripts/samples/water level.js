// sníží vodní hladinu na požadovanou hranici

var waterLevel = 100;
var airBrush = brush("vzduch");

var predicate = function(element, x, y) {
    return (y < canvas.getHeight() - waterLevel) && Elements.isWater(element)
};

canvas.getTools().replaceAll(predicate, airBrush);
