package com.dylan773.finalyearproject.utilities

/**
 * TODO
 *
 * @property delay The time delay before the [targetEvent] is executed.
 * @property targetEvent The event to be executed.
 *
 * @author Dylan Brand
 */
class DelayEvent(val delay: Long, val targetEvent: Runnable) : Runnable {

    init {
        Thread(this).start()
    }

    override fun run() {
        val startTime = System.currentTimeMillis()

        while ((System.currentTimeMillis() - startTime) < delay);
        targetEvent.run()
    }

}