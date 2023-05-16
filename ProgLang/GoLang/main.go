package main

func main() {
	HelloWorld()
}


// go run main.go chapter1.go
// go run *.go


/*
The package of your main.go file must be `main`.
When there is a `main` package, and a function `main` in your project, the compiler knows it will be compiled as a executable, and not as a library.

So try to change package `main` inside the main.go file.
*/