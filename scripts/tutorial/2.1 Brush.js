
// Instance tříd implementujících rozhraní Brush slouží především jako
// továrny na elementy.
// lze je získaz následovně:

var sandBrush_1 = brushManager.getBrush("písek");  // podle jména
var sandBrush_2 = brushManager.getBrush(10);       // podle ID

// sandBrush_1 == sandBrush_2

// Stejným způsobem je také možné použít globální metody
// brush(id) a brush(name)

var soilBrush = brush("hlína");


// získání seznamu všech štětců

var brushes = brushManager.getBrushes();


// získání štětce, kterým je nanášen konkrétní element

var element = canvas.get(0, 0);  // nějaký element...
var producer = brushManager.getProducer(element);


// zjištětění, jestli je konkrétní element nanášen určitým štětcem

producer.produces(element);  // => true


// získání seznamu všech štětců, kterými může být konkrétní element nanesen

var producers = brushManager.getProducers(element);