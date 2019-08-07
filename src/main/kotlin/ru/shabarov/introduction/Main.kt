package ru.shabarov.introduction

import java.util.regex.Pattern
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import java.util.*

fun main(args: Array<String>) {
    assertTrue(joinOptions(listOf("one", "two", "three")) == "[one, two, three]")
    assertTrue(containsEven(listOf(1,2,3,4,5)))
    assertFalse(containsEven(listOf(1,3,5)))
    assertTrue(Pattern.compile(getPattern()).matcher("13 JUN 1992").matches())
    assertFalse(Pattern.compile(getPattern()).matcher("13.02.1992").matches())
    assertTrue(getPeople().toString() == "[Person(name=Alice, age=29), Person(name=Bob, age=31)]")
    assertTrue(eval(Sum(Num(1), Num(2))) == 3)
    assertTrue(getList().toString() == "[5, 2, 1]")
}

//Hello, World
fun start(): String = "OK"

//Named arguments
fun joinOptions(options: Collection<String>) = options.joinToString(", ", "[", "]")

//Default arguments
fun foo(name: String, number: Int = 42, toUpperCase: Boolean = false) =
    (if (toUpperCase) name.toUpperCase() else name) + number

fun useFoo() = listOf(
    foo("a"),
    foo("b", number = 1),
    foo("c", toUpperCase = true),
    foo(name = "d", number = 2, toUpperCase = true)
)

//Lambdas
fun containsEven(collection: Collection<Int>): Boolean = collection.any { it % 2 == 0 }

//Strings
const val month = "(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)"

fun getPattern(): String = """\d{2} $month \d{4}"""

//Data classes
data class Person(val name: String, val age: Int)

fun getPeople(): List<Person> {
    return listOf(Person("Alice", 29), Person("Bob", 31))
}

//Nullable types
fun sendMessageToClient(
    client: Client?, message: String?, mailer: Mailer
){
    val email = client?.personalInfo?.email ?: return
    if (message != null) mailer.sendMessage(email, message);

    // OR:
//    val email = client?.personalInfo?.email
//    if (email != null && message != null) {
//        mailer.sendMessage(email, message)
//    }
}

class Client (val personalInfo: PersonalInfo?)
class PersonalInfo (val email: String?)
interface Mailer {
    fun sendMessage(email: String, message: String)
}

//Smart casts
fun eval(expr: Expr): Int =
    when (expr) {
        is Num -> expr.value
        is Sum -> eval(expr.left) + eval(expr.right)
        else -> throw IllegalArgumentException("Unknown expression")
    }

interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

//Extension functions
fun Int.r(): RationalNumber = RationalNumber(this, 1)
fun Pair<Int, Int>.r(): RationalNumber = RationalNumber(this.first, this.second)

data class RationalNumber(val numerator: Int, val denominator: Int)

//Object expressions

fun getList(): List<Int> {
    val arrayList = arrayListOf(1, 5, 2)
    Collections.sort(arrayList, object : Comparator<Int> {
        override fun compare(o1: Int?, o2: Int?): Int {
            if (o1 == null || o2 == null) throw java.lang.IllegalArgumentException()
            return o2.compareTo(o1)
        }
    })

//    //OR:
//    object : Comparator<Int> {
//        override fun compare(x: Int, y: Int) = y - x
//    }
    return arrayList
}

//SAM conversions
fun getList2(): List<Int> {
    val arrayList = arrayListOf(1, 5, 2)
    Collections.sort(arrayList, { x, y -> y - x })
    return arrayList
}

//Extensions on collections
fun getList3(): List<Int> {
    return arrayListOf(1, 5, 2).sortedDescending()
}


