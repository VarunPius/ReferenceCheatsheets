/*
Chapter 8:
    includes:
    - Structs
    - Methods
    - Interface
    - Struct Embeddings
*/

package main

import (
    "fmt"
    "math"
)


func chapter08() {
    fmt.Println("------------------------------------------------")
    fmt.Println("------------------ Chapter 8 -------------------")
    fmt.Println("------------------------------------------------")
    fmt.Println()

    structExplanation()
    fmt.Println()

    methodExplanation()
    fmt.Println()

    interfaceExplanation()
    fmt.Println()

    structEmbedExplanation()
    fmt.Println()
}

// Struct explanations:
type person struct {
    name string
    age int
    salary int
}

func newPerson(name string) *person {
    p := person{name: name}
    p.age = 45
    p.salary  = 10000
    return &p
}

func structExplanation() {
    fmt.Println(person{"Alexandra", 20, 2000})
    fmt.Println(person{name: "Brian", age: 45, salary:4500})
    fmt.Println(person{name: "Catherine", age: 28})    // Need to mention the parameter key if fewer values are passed

    fmt.Println(&person{name: "Damian", age: 32, salary: 5400})
    // An & prefix yields a pointer to the struct.

    // It’s idiomatic to encapsulate new struct creation in constructor functions
    fmt.Println(newPerson("Eliza"))

    f := person{"Frasier", 52, 25000}
    fmt.Println(f.salary)    // Access struct fields with a dot.
    fp := &f
    fmt.Println(fp.age)    // can also use dots with struct pointers; the pointers are automatically dereferenced.

    fp.age = 65        // Structs are mutable
    fmt.Println(fp)    // &{Frasier 65 25000}
    fmt.Println(f)    // {Frasier 65 25000}

    dog := struct {
        name string
        isGood bool
    }{
        "Eclair",
        true,
    }
    fmt.Println(dog)
}

// Method explanations:
type rect struct {
    width, height int
}

type circle struct {
    radius float64
}

func methodExplanation() {
    // methods are defined on struct types.
    r := rect{width: 10, height: 5}

    // Methods can be defined for either pointer or value receiver types.
    // Go automatically handles conversion between values and pointers for method calls.
    // You may want to use a pointer receiver type to avoid copying on method calls or to allow the method to mutate the receiving struct.

    fmt.Println("Value Receiver: Area:", r.methodArea())
    fmt.Println("Value Receiver: Perimeter:", r.methodPerimeter())

    rp := &r
    fmt.Println("Pointer Receiver: Area:", rp.methodArea())
    fmt.Println("Pointer Receiver: Perimeter:", rp.methodPerimeter())
}

// Method
func (r *rect) methodArea() int {
    // This method has a receiver type of *rect; in other words it has pointer receiver type
    return r.width * r.height
}

func (r rect) methodPerimeter() int {
    // Here’s an example of a value receiver.
    return 2 * (r.width + r.height)
}

// Interface explanations:
// Interfaces are named collections of method signatures
type geometry interface {
    area() float64
    perimeter() float64
}

func (r *rect) area() float64 {
    return float64(r.width * r.height)
}

func (r *rect) perimeter() float64 {
    return float64(2 * (r.width + r.height))
}

func (c *circle) area() float64 {
    return math.Pi * c.radius * c.radius
}

func (c *circle) perimeter() float64 {
    return 2 * math.Pi * c.radius
}

func measure(g geometry) {
    fmt.Println("Inside Measure")
    fmt.Println("OG G: ", g)
    fmt.Println("G Area: ", g.area())
    fmt.Println("G Perimeter: ", g.perimeter())
}

func interfaceExplanation() {
    r := rect{width: 15, height: 12}
    c := circle{radius: 10}

    rp := &r
    cp := &c

    measure(rp)
    measure(cp)
    // if `area` and `perimeter` receiver type is circle/rect instead of *circle/*rect;
    // then measure(r) or measure(c)
    // geometry will not be a pointer in any case: type *geometry is pointer to interface, not interface
}

// Struct Embeddings explanation
// Go supports embedding of structs and interfaces to express a more seamless composition of types

type base struct {
    num int
}

type container struct {
    // A `container` embeds a `base`. An embedding looks like a field without a name.
    base
    str string
}

func (b base) describe() string {
    return fmt.Sprintf("base with num=%v", b.num)
}

func structEmbedExplanation() {
    co := container{
        base: base{num: 1},
        str: "random string",
    }
    // either end `str: "random string"` with } and if pushing to new line then end with ,
    // Struct declarations end with } or if moving it to new line then add a comma at end to indicate still things to come

    fmt.Printf("co={num: %v, str: %v}", co.num, co.str) // can access base's fields directly on co
    fmt.Println("co = num:", co.base.num)    // can use full path
    fmt.Println("describe: ", co.describe()) // Since container embeds base, the methods of base also become methods of a container.

    type describer interface {
        describe() string
    }

    var d describer = co
    fmt.Println("Interface: ", d.describe())
    // Embedding structs with methods may be used to bestow interface implementations onto other structs
    // Here we see that a container now implements the describer interface because it embeds base.
}
