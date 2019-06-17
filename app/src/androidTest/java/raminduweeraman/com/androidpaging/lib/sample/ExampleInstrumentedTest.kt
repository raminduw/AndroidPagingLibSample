package raminduweeraman.com.androidpaging.lib.sample

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented startGetData, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under startGetData.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("raminduweeraman.com.androidpaging.lib.sample", appContext.packageName)
    }
}
