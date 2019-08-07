package ru.shabarov.properties

import ru.shabarov.conventions.MyDate
import java.util.Calendar
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

//Properties
class PropertyExample() {
    var counter = 0
    var propertyWithCounter: Int? = null
        set(value: Int?) {
            field = value
            counter++
        }
}

//Lazy property
class LazyProperty(val initializer: () -> Int) {
    var value: Int? = null
    val lazy: Int
        get() {
            if (value == null){
                value = initializer()
            }
            return value!!
        }
}

//Delegates example
class LazyProperty2(private val initializer: () -> Int) {
    val lazyValue: Int by lazy(initializer)
}

//Delegates how it works

class D {
    var date: MyDate by EffectiveDate()
}

class EffectiveDate<R> : ReadWriteProperty<R, MyDate> {

    var timeInMillis: Long? = null

    override fun getValue(thisRef: R, property: KProperty<*>): MyDate {
        return timeInMillis!!.toDate()
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: MyDate) {
        timeInMillis = value.toMillis()
    }
}

fun MyDate.toMillis(): Long {
    val c = Calendar.getInstance()
    c.set(year, month, dayOfMonth, 0, 0, 0)
    c.set(Calendar.MILLISECOND, 0)
    return c.timeInMillis
}

fun Long.toDate(): MyDate {
    val c = Calendar.getInstance()
    c.timeInMillis = this
    return MyDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
}