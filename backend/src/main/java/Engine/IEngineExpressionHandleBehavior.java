package Engine;


// Engine will have different expression handling behaviors  for different expressions
// if client wants their own implementation of handling behavior , they can implement this
// abstract class and define their algorithms for handling different items of the select statement
// or any other SQL statement in their area of interest with different column , function, filters handling
// algorithms as they intended to have in the siddhi app or any other use case in their area of interest in the
// work
// (eg -  for select statement, for filter expression)
public abstract class IEngineExpressionHandleBehavior extends IEngine {

}
