/*
Chapter 8:
    includes:
    - Structs
	- Methods
*/

package main

import (
    "fmt"
)

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

type rect struct {
	width, height int
}


func chapter08() {
    fmt.Println("------------------------------------------------")
    fmt.Println("------------------ Chapter 8 -------------------")
    fmt.Println("------------------------------------------------")
    fmt.Println()

	structExplanation()
	fmt.Println()

	methodExplanation()
	fmt.Println()
}

func structExplanation() {
	fmt.Println(person{"Alexandra", 20, 2000})
	fmt.Println(person{name: "Brian", age: 45, salary:4500})
	fmt.Println(person{name: "Catherine", age: 28})	// Need to mention the parameter key if fewer values are passed

	fmt.Println(&person{name: "Damian", age: 32, salary: 5400})
	// An & prefix yields a pointer to the struct.

	// It’s idiomatic to encapsulate new struct creation in constructor functions
	fmt.Println(newPerson("Eliza"))

	f := person{"Frasier", 52, 25000}
	fmt.Println(f.salary)	// Access struct fields with a dot.
	fp := &f
	fmt.Println(fp.age)	// can also use dots with struct pointers; the pointers are automatically dereferenced.

	fp.age = 65		// Structs are mutable
	fmt.Println(fp)	// &{Frasier 65 25000}
	fmt.Println(f)	// {Frasier 65 25000}

	dog := struct {
		name string
		isGood bool
	}{
		"Eclair",
		true,
	}
	fmt.Println(dog)
}

func methodExplanation() {
	// methods are defined on struct types.
	r := rect{width: 10, height: 5}

	// Methods can be defined for either pointer or value receiver types.
	// Go automatically handles conversion between values and pointers for method calls.
	// You may want to use a pointer receiver type to avoid copying on method calls or to allow the method to mutate the receiving struct.

	fmt.Println("Value Receiver: Area:", r.area())
	fmt.Println("Value Receiver: Perimeter:", r.perimeter())

	rp := &r
	fmt.Println("Pointer Receiver: Area:", rp.area())
	fmt.Println("Pointer Receiver: Perimeter:", rp.perimeter())
}

func (r *rect) area() int {
	// This method has a receiver type of *rect; in other words it has pointer receiver type
	return r.width * r.height
}

func (r rect) perimeter() int {
	// Here’s an example of a value receiver.
	return 2 * (r.width + r.height)
}