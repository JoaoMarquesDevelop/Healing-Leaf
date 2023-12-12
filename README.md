# <font color="#9bf080"> Isle Tower Defense Server</font> 

## Version: <font color="#69c6f0"> Healing Leaf 1.0</font> 

## This is my old server of Isle Tower Defense on Steam, I hope it helps anyone
I started the development of this game in 2018, i improved this server lots of times.

## Include Game Marketplace to include revenue
Make sure to adapt service and controllers with Steam API. Everything should be handled there.

## Security
I was handling game security with JWTS, authentication is made by Steam API. feel free to change the encryption lib for the jwt.

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
