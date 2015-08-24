
// získání elementů z plátna

var element_1 = canvas.get(0, 0);                      // levý horní roh
var element_2 = canvas.get(canvas.getWidth() - 1, 0);  // pravý horní roh


// prohození těchto elementů

canvas.set(0, 0, element_2);
canvas.set(canvas.getWidth() - 1, 0, element_1);


// metodou getBackground() získáme element, který se používá k naznačení
// ničeho - tj. "vzduch"

var bg = canvas.getBackground();

canvas.set(1, 0, bg);

// canvas.set(1, 0, null)  -- takto ne!


// aplikování štětce na určité pozici

var brush = brush("písek");
canvas.set(2, 0, brush);