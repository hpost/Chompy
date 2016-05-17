package cc.femto.kommon.extensions

inline fun ld(exponent: Int) = Math.pow(2.0, exponent.toDouble()).toInt()

inline fun Int.clamp(min: Int, max: Int) = Math.min(Math.max(this, min), max)
inline fun Long.clamp(min: Long, max: Long) = Math.min(Math.max(this, min), max)
inline fun Float.clamp(min: Float, max: Float) = Math.min(Math.max(this, min), max)
inline fun Double.clamp(min: Double, max: Double) = Math.min(Math.max(this, min), max)

