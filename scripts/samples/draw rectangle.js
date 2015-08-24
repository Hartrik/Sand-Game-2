// vykreslí na plátno obdélník

function rectangle(x, y, width, height, brush) {
    for (var ix = x; ix < (x + width); ix++) {
        for (var iy = y; iy < (y + height); iy++) {
            canvas.set(ix, iy, brush);
        }
    }
}

rectangle(100, 100, 500,  50, brush("dřevo"));
rectangle(200,  20,  50, 300, brush("železo"));
