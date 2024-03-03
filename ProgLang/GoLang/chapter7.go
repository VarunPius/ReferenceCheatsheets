/*
Chapter 7:
    includes:
    - Strings
    - Runes
*/

package main

import (
    "fmt"
)

func chapter7() {
    fmt.Println("------------------------------------------------")
    fmt.Println("------------------ Chapter 7 -------------------")
    fmt.Println("------------------------------------------------")
    fmt.Println()

    stringsExplanation()
    
    fmt.Println()

}

/*
String Fundamentals
String formatting basics:
%s − By using this we can print the uninterpreted strings or slice.
%q − By using this we can print the string with double quotes.
%x − By using this we can print the lower case character string with base 16.
%X − By using this we can print the upper case character string with base 16.
*/

func stringsExplanation() {
    const s = "Test String"
    fmt.Println("String1: str:", s)
    fmt.Println("String1: rune:", s[5])         // Will print the rune and not the char
    fmt.Printf("String1: Char: %c \n", s[5])    // Will print the character
    //Output:
    //String1: str: Test String
    //String1: rune: 83
    //String1: Char: S 

    a_c := "a"
    var b_r rune = 'c'          // Rune declaration always in single quotes
    fmt.Print(a_c, b_r, "\n")   // a99; 99 is int value of `c`. The rune is int of the char `c`
    fmt.Printf("%x\n", a_c)     // 61; Hex value of `a`


    fmt.Println("Len:", len(s))

    for i := 0; i < len(s); i++ {
        fmt.Print("String2: utf8 int value:", s[i])          // rune is printed here
        fmt.Printf("; String2 Hex: %x %s", s[i], s[i])      // Hex value is printed here, along 
        // In my experience, GoLang doesn't extract just one character from string when iterating
        // When iterating, we are referencing the rune of the character
        // Rune explanation below in notes
        fmt.Printf("; String2 Char: %c \n", s[i])
        
        // Output for `Test`:
        //String2 utf8 int value:84; String2 Hex: 54 %!s(uint8=84); String2 Char: T 
        //String2 utf8 int value:101; String2 Hex: 65 %!s(uint8=101); String2 Char: e 
        //String2 utf8 int value:115; String2 Hex: 73 %!s(uint8=115); String2 Char: s 
        //String2 utf8 int value:116; String2 Hex: 74 %!s(uint8=116); String2 Char: t 
    }

    // Rune to string conversion:
    r := rune('a')          // always in a single quote when referencing a single character
                            // will give error: cannot convert "a" (untyped string constant) to type rune
    fmt.Println(r, string(r)) // 97 a

    // The following is different because we are iterating over string array
    // and not characters of single string
    var st_ar = [4]string{"axcz", "b", "c", "d"}
    fmt.Println("String array print:", st_ar)

    for i:=0; i < 4; i++ {
        fmt.Println("String Array Loop:", i, st_ar[i])
    }
    
    // String Character comparison:
    var str_comp = "Delta"
    if str_comp[1] == 'e' { // error if double quotes used: invalid operation: str_comp[1] == "e" (mismatched types byte and untyped string)
        fmt.Println("String char comparison: ", true)
    } else {
        fmt.Println("False")
    }
}

func runeEval() {
    
}

/*
Rune
In Golang, a rune is an alias for int32.
It represents a single Unicode character and is used to handle Unicode characters in strings.
Unicode assigns a unique number, called a Unicode code point, to each character.
Strings in Go are encoded using UTF-8, which allows them to contain Unicode characters.
Since rune represents a Unicode character, a string in Go is often considered as a sequence of runes.
However, runes are stored as 1, 2, 3, or 4 bytes depending on the character.

For example, you can declare a rune variable by placing a character inside single quotes like myRune := '¿'.

Printing the type of a rune variable will yield `int32`, and printing its value will yield its integer (decimal) value.

To work with runes, you can convert a slice of runes to a string and vice versa.

Additionally, you can use functions like utf8.RuneCountInString() to count the number of runes in a string
*/
