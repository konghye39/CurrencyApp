package com.kong.currencyapp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.kong.currencyapp", appContext.packageName)
    }

//    @Test
//    fun testApi() {
//        val time = 1682684223L
//        val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
//        val date = Date(time)
//        print(format.format(date))
//    }
}