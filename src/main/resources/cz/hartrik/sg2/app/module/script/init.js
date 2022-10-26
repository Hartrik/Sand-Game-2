
/* global Java, serviceManager, brushManager */

// since 2.01

function brush(nameOrID) {
    return brushManager.getBrush(nameOrID);
}

function service(name) {
    serviceManager.run(name);
}

// since 2.02

var Point = Java.type("cz.hartrik.common.Point");
var Elements = Java.type("cz.hartrik.sg2.world.element.Elements");