// Sows grass everywhere

var air = canvas.getBackground();
var soilBrush = brush(41);
var seedBrush = brush(110);

canvas.forEach(function(element, x, y) {
    if (y == 0 || y == canvas.getHeight()) return;

    if (soilBrush.produces(element) && canvas.get(x, y - 1) == air)
        canvas.set(x, y - 1, seedBrush);
});
