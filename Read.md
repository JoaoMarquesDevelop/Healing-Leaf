# <font color="#9bf080"> Isle Tower Defense Server</font> 

## Version: <font color="#69c6f0"> Healing Leaf 1.0</font> 

### Patterns
This code have to follow the defined patterns:

* Dates are returned and saved in seconds.
* Method's starts with lower case.
* Strings must be defined in /data/StringsPackage.java. Separated by each class with comments
* Service public methods must have output a log like this: "ServiceName -> methodName -> message"
* Controller methods must output a log like this: "ControllerName -> methodName -> message"
* Exceptions must output a error log.
* Towers, feedstocks, traps... are called entities and each one has a identifier. An identifier is a number used to separate items of the same entity,
for example a feedstock can have identifier = 2 and a tower can have identifier = 2.
* Chances are stored in %.
