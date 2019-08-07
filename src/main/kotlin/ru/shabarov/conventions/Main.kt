package ru.shabarov.conventions

import java.time.LocalDate
import kotlin.test.assertTrue

fun main(args: Array<String>) {
    assertTrue { compare(MyDate(2019, 1, 30), MyDate(2019, 1, 31)) }
    assertTrue { checkInRange(MyDate(2019, 8, 7), MyDate(2019, 8, 6), MyDate(2019, 8, 8)) }
    assertTrue { checkInRange2(MyDate(2019, 8, 7), MyDate(2019, 8, 6), MyDate(2019, 8, 8)) }
    iterateOverDateRange(MyDate(2019, 8, 7), MyDate(2019, 8, 9)) {println("next date = $it")}
    println(task1(MyDate(2019, 8, 8)))
    println(task2(MyDate(2019, 8, 8)))
    invokeTwice(Invokable())
}

//Comparison
data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate) = if (year == other.year && month == other.month && dayOfMonth == other.dayOfMonth) 0
        else if (year > other.year
            || (year == other.year && month > other.month)
            || (year == other.year && month == other.month && dayOfMonth > other.dayOfMonth)) 1
        else -1

    fun nextDay() : MyDate {
        val nextDay = LocalDate.of(year, month, dayOfMonth).plusDays(1)
        return MyDate(nextDay.year, nextDay.monthValue, nextDay.dayOfMonth)
    }
    //OR:
//    override fun compareTo(other: MyDate) = when {
//        year != other.year -> year - other.year
//        month != other.month -> month - other.month
//        else -> dayOfMonth - other.dayOfMonth
//    }
}

fun compare(date1: MyDate, date2: MyDate) = date1 < date2

//In Range
class DateRange(val start: MyDate, val endInclusive: MyDate) {
    operator fun contains(d: MyDate) : Boolean = start <= d && d <= endInclusive
}

fun checkInRange(date: MyDate, first: MyDate, last: MyDate): Boolean {
    return date in DateRange(first, last)
}

//RangeTo
operator fun MyDate.rangeTo(other: MyDate) : DateRange2 = DateRange2(this, other)

class DateRange2(override val start: MyDate, override val endInclusive: MyDate): ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> = MyIterator(this)
}

fun checkInRange2(date: MyDate, first: MyDate, last: MyDate): Boolean {
    return date in first..last
}

//For loop
class MyIterator(val range: DateRange2) : Iterator<MyDate> {
    override fun next(): MyDate {
        val result = current
        current = current.nextDay()
        return result
    }

    override fun hasNext(): Boolean = current <= range.endInclusive

    var current : MyDate = range.start
}

fun iterateOverDateRange(firstDate: MyDate, secondDate: MyDate, handler: (MyDate) -> Unit) {
    for (date in firstDate..secondDate) {
        handler(date)
    }
}

//Operator overloading

operator fun MyDate.plus(timeInterval: TimeInterval): MyDate = addTimeIntervals(timeInterval, 1)

operator fun MyDate.plus(multTimeInterval: MultTimeInterval): MyDate = addTimeIntervals(multTimeInterval.timeInterval, multTimeInterval.number)

fun task1(today: MyDate): MyDate {
    return today + TimeInterval.YEAR + TimeInterval.WEEK
}

fun task2(today: MyDate): MyDate {
    return today + TimeInterval.YEAR * 2 + TimeInterval.WEEK * 3 + TimeInterval.DAY * 15
}

//Destructuring declarations
data class MyDate2(val year: Int, val month: Int, val dayOfMonth: Int)

fun isLeapDay(date: MyDate2): Boolean {

    val (year, month, dayOfMonth) = date

    // 29 February of a leap year
    return year % 4 == 0 && month == 2 && dayOfMonth == 29
}

//Invoke
class Invokable {
    var numberOfInvocations: Int = 0
        private set
    operator fun invoke(): Invokable {
        this.numberOfInvocations++
        println(this.numberOfInvocations)
        return this
    }
}

fun invokeTwice(invokable: Invokable) = invokable()()