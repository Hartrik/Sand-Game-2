// Vykreslí na plátno všechny elementy

var brushes = brushManager.getBrushesAll();

var H_COUNT = 8;
var V_COUNT = Math.ceil(brushes.size() / H_COUNT);

var H_SIZE = canvas.getWidth() / H_COUNT;
var V_SIZE = canvas.getHeight() / V_COUNT;

// pozadí, zároveň od sebe oddělí elementy
canvas.getTools().fill(brush("zeď"));

var i = 0;

for each (var next in brushes) {

    var x = i % H_COUNT;
    var y = Math.floor(i / H_COUNT);

    var rx = x * H_SIZE;
    var ry = y * V_SIZE;

    var rectangle = ToolFactory.rectangle(H_SIZE - 2, V_SIZE - 2);
    canvas.take(rectangle, rx + 1, ry + 1).getTools().fill(next);

    i++;
}