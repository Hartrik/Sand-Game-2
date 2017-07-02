// postupně zaplaví plátno vodou až do určité výšky

var water_level = 250;  // maximální výška
var delay = 20;         // prodleva mezi cykly v milisekundách
var water = brush("voda");

new java.lang.Thread(function() {
    var predicate = function(e) { return Elements.isAir(e) };

    canvas.streamLinesReversed()
          .limit(water_level)
          .peek(function(line) { return java.lang.Thread.sleep(delay) })
          .forEach(function(line) { return line.getTools().replaceAll(predicate, water) });

}).start();
