
function snowflake(i, gap, turtle) {
    var width = Math.min(canvas.getWidth() - gap, canvas.getHeight() - gap)
    var height = 2 * width / Math.sqrt(3)
    var size = width / Math.pow(3.0, i)
    
    // přibližně...
    turtle.setX((canvas.getWidth() - width)/2 + gap);
    turtle.setY(canvas.getHeight() - ((canvas.getHeight() - height)/2 + gap/2));

    turtle.run(function(t) drawPart(i, size, t))
          .left(-120)
          .run(function(t) drawPart(i, size, t))
          .left(-120)
          .run(function(t) drawPart(i, size, t))
}

function drawPart(i, size, turtle) {
    if (i == 0) {
        turtle.draw(size)
    } else {
        drawPart(i-1, size, turtle)
        turtle.left(60)
        drawPart(i-1, size, turtle)
        turtle.left(-120)
        drawPart(i-1, size, turtle)
        turtle.left(60)
        drawPart(i-1, size, turtle)
    }
}

new java.lang.Thread(function() {
    for (var i = 0; i < 5; i++) {
        snowflake(i, 50, Turtle.create(ToolFactory.centeredSquare(1)))
        java.lang.Thread.sleep(500)
    }
}).start();