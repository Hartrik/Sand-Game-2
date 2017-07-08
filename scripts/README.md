
This directory contains example in-game scripts.  
If you put it into the working directory you will be able to run all scripts from the menu.


## API Reference

### Global variables

Name             | Description
---------------- | ------------------------------
`canvas`         | Allows direct manipulation with elements
`brushManager`   | Manages brushes
`serviceManager` | Manages services
`controls`       | Manages selected brushes and tools

### Global methods

Name             | Description
---------------- | ------------------------------
`brush(name)`    | Same as `brushManager.getBrush(name)`
`brush(id)`      | Same as `brushManager.getBrush(id)`
`service(name)`  | Same as `serviceManager.run(name)`

### Classes

Name             | Description
---------------- | ------------------------------
`Elements`       | Provides methods for working with elements
`ToolFactory`    | Provides methods for creating tools
`Turtle`         | Provides turtle graphics API


*Don't forget to check `scripts/tutorial`.*
