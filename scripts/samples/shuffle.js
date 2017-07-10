// Shuffles the canvas content

var width  = canvas.getWidth();
var height = canvas.getHeight();

var count  = width * height / 2;  // promíchá polovinu elementů
var random = new java.util.Random();

for (var i = 0; i < count; i++) {
    var x1 = random.nextInt(width);
    var y1 = random.nextInt(height);
    var x2 = random.nextInt(width);
    var y2 = random.nextInt(height);

    var element1 = canvas.get(x1, y1);
    var element2 = canvas.get(x2, y2);

    canvas.set(x1, y1, element2);
    canvas.set(x2, y2, element1);
}
