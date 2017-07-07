// Simulates rain

var count = 1000;  // total number of "water drops"
var delay = 5;     // delay between two "water drops" in milliseconds
var water = brush(70);  // btw: 141 for napalm ;)
var random = new java.util.Random();

new java.lang.Thread(function() {
    for (var i = 0; i < count; i++) {
        var x = random.nextInt(canvas.getWidth());
        canvas.set(x, 0, water);

        java.lang.Thread.sleep(delay)
    }
}).start();
