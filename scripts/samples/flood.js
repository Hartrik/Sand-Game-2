// Slowly fills the canvas with water up to a certain height

var water_level = 250;
var delay = 20;  // milliseconds
var water = brush(70);

new java.lang.Thread(function() {
    var predicate = function(e) { return Elements.isAir(e) };

    canvas.streamLinesReversed()
          .limit(water_level)
          .peek(function(line) { return java.lang.Thread.sleep(delay) })
          .forEach(function(line) { return line.getTools().replaceAll(predicate, water) });

}).start();
