
// Brushes acts as element factories. For example they are handling textures.

var sandBrush_1 = brushManager.getBrush("sand");  // by name
var sandBrush_2 = brushManager.getBrush(40);      // by id

if (sandBrush_1 != sandBrush_2)
    throw "This should not happen";


// Or we can use global methods brush(id) and brush(name)

var wallBrush_1 = brush("wall");
var wallBrush_2 = brush(10);

if (wallBrush_1 != wallBrush_2)
    throw "This should not happen";


// Getting list of all of the brushes

var brushes = brushManager.getBrushes();

print("List of brushes:");
brushes.forEach(function(brush) {
    var info = brush.getInfo();

    var id = info.getId();
    var name = info.getName();
    var desc = info.getDescription();
    var labels = info.getLabels();
    var aliases = info.getAliases();

    print(id + "\t" + name);
});


// Getting a brush that was used to insert an element
// (same as a picker tool - middle mouse button)

var element = canvas.get(0, 0);  // just some element...
var producer = brushManager.getProducer(element);


// Determining if a brush produces some element

producer.produces(element);  // => true
