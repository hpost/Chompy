package cc.femto.kommon

import android.content.Context
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Kommon {
    companion object {
        var ctx: Context by NotNullValue()
            private set

        /**
         * Kommon needs to be initialized with a [Context].
         * Typically called from your [Application] class.
         * @param context The context used to retrieve resources
         */
        fun init(context: Context) {
            ctx = context
        }
    }

    private class NotNullValue<T>() : ReadWriteProperty<Any?, T> {
        private var value: T? = null

        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return value ?: throw IllegalStateException("Kommon needs to be initialized with an android.content.Context instance")
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            this.value = value
        }
    }
}
