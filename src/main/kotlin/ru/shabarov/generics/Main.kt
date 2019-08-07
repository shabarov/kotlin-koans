package ru.shabarov.generics

fun main(args: Array<String>) {
    partitionLettersAndOtherSymbols()
    partitionWordsAndLines()
}

fun <T, C : MutableCollection<T>> Collection<T>.partitionTo(a: C, b: C, p : (T) -> Boolean) : Pair<C, C> {
    this.forEach {
        if (p(it)) a += it else b += it
    }
    return Pair(a, b)
}

fun partitionWordsAndLines() {
    val (words, lines) = listOf("a", "a b", "c", "d e").
        partitionTo(ArrayList<String>(), ArrayList()) { s -> !s.contains(" ") }
    words == listOf("a", "c")
    lines == listOf("a b", "d e")
    println(words)
    println(lines)
}

fun partitionLettersAndOtherSymbols() {
    val (letters, other) = setOf('a', '%', 'r', '}').
        partitionTo(HashSet<Char>(), HashSet()) { c -> c in 'a'..'z' || c in 'A'..'Z'}
    letters == setOf('a', 'r')
    other == setOf('%', '}')
    println(letters)
    println(other)
}