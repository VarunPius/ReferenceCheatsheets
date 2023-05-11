Scala 3 is a remodel/refit for the scala language. It attracts developers due to its improved features such as simpler syntax, better type inference, improved error messages, and enhanced support for functional programming. In this article, we will compare the syntactical enhancement of Scala 2 & Scala 3. How indentation will help developers to write code efficiently and effortlessly.
https://medium.com/@knoldus/scala-3-a-look-at-its-improved-syntax-9706bbf581da

https://www.youtube.com/watch?v=sby4rxdmabI

# New control syntax
https://dotty.epfl.ch/docs/reference/other-new-features/control-syntax.html#
```scala
if x < 0 then
  "negative"
else if x == 0 then
  "zero"
else
  "positive"

if x < 0 then -x else x

while x >= 0 do x = f(x)

for x <- xs if x > 0
yield x * x

for
  x <- xs
  y <- ys
do
  println(x + y)

try body
catch case ex: IOException => handle
```

The rules in detail are:
- The condition of an if-expression can be written without enclosing parentheses if it is followed by a then.
- The condition of a while-loop can be written without enclosing parentheses if it is followed by a do.
- The enumerators of a for-expression can be written without enclosing parentheses or braces if they are followed by a yield or do.
- A do in a for-expression expresses a for-loop.
- A catch can be followed by a single case on the same line. If there are multiple cases, these have to appear within braces (just like in Scala 2) or an indented block.

> Rewrites
> The Scala 3 compiler can rewrite source code from old syntax to new syntax and back. When invoked with options `-rewrite -new-syntax` it will rewrite from old to new syntax, dropping parentheses and braces in conditions and enumerators. When invoked with options `-rewrite -old-syntax` it will rewrite in the reverse direction, inserting parentheses and braces as needed.


# Classes vs Objects vs Case Classes vs Traits
Classes should be used when we need to instantialte objects

We use "Objects" only when we have a singleton requirement meaning no need for multiple instances of "objects". I am thinking when we are building common library functions or utility functions in a project, we can use "objects" that way we need to instantiate each time we use methods/functions in that Object.
