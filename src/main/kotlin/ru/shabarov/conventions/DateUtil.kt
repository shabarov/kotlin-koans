package ru.shabarov.conventions

import java.util.Calendar

enum class TimeInterval { DAY, WEEK, YEAR;
    operator fun times(number: Int) : MultTimeInterval = MultTimeInterval(this, number)
}

class MultTimeInterval(val timeInterval: TimeInterval, val number: Int)

fun MyDate.addTimeIntervals(timeInterval: TimeInterval, number: Int) : MyDate {
    val c = Calendar.getInstance()
    c.set(year, month, dayOfMonth)
    when (timeInterval) {
        TimeInterval.DAY -> c.add(Calendar.DAY_OF_MONTH, number)
        TimeInterval.WEEK -> c.add(Calendar.WEEK_OF_MONTH, number)
        TimeInterval.YEAR -> c.add(Calendar.YEAR, number)
    }
    return MyDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
}