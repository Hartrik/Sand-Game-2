
// vypíše seznam všech štětců s jejich ID

var brushes = brushManager.getBrushes();

for each (var brush in brushes) {
    var info = brush.getInfo();
    
    var id      = info.getId();
    var name    = info.getName();
    var desc    = info.getDescription();
    var labels  = info.getLabels();
    var aliases = info.getAliases();
    
    print(id + "\t" + name);
}