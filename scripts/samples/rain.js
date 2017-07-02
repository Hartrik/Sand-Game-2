// simulace deště

var count = 500;  // celkový počet "kapek"
var delay = 5;    // prodleva mezi jednotlivými kapkami v milisekundách
var water = brush("voda")
var random = new java.util.Random();

new java.lang.Thread(function() {
    for (var i = 0; i < count; i++) {
        var x = random.nextInt(canvas.getWidth());
        canvas.set(x, 0, water);

        java.lang.Thread.sleep(delay)
    }
}).start();
