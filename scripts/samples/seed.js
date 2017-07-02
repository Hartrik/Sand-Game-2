// zaseje trávu všude, kde je to možné

var air = canvas.getBackground();
var soilBrush = brush("hlína");
var seedBrush = brush("semínka trávy");

canvas.forEach(function(element, x, y) {
    if (y == 0 || y == canvas.getHeight()) return;

    if (soilBrush.produces(element) && canvas.get(x, y - 1) == air)
        canvas.set(x, y - 1, seedBrush);
});
