// Draws rectangle in the old way - without tools

function rectangle(x, y, width, height, brush) {
    for (var ix = x; ix < (x + width); ix++) {
        for (var iy = y; iy < (y + height); iy++) {
            canvas.set(ix, iy, brush);
        }
    }
}

var iron = brush(20);
var wood = brush(30);

rectangle(100, 100, 500,  50, wood);
rectangle(200,  20,  50, 300, iron);
