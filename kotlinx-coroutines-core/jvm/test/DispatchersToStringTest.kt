package kotlinx.coroutines

import kotlin.coroutines.*
import kotlin.test.*

class DispatchersToStringTest {

    @Test
    fun testStrings() {
        assertEquals("Dispatchers.Unconfined", Dispatchers.Unconfined.toString())
        assertEquals("Dispatchers.Default", Dispatchers.Default.toString())
        assertEquals("Dispatchers.IO", Dispatchers.IO.toString())
        assertEquals("Dispatchers.Main[missing]", Dispatchers.Main.toString())
        assertEquals("Dispatchers.Main[missing]", Dispatchers.Main.immediate.toString())
    }

    @Test
    fun testLimitedParallelism() {
        assertEquals("Dispatchers.IO.limitedParallelism(1)", Dispatchers.IO.limitedParallelism(1).toString())
        assertEquals("Dispatchers.Default.limitedParallelism(1)", Dispatchers.Default.limitedParallelism(1).toString())
        assertEquals("platformJsonDispatcher", Dispatchers.IO.limitedParallelism(1, "platformJsonDispatcher").toString())
        assertEquals("databasePoolDispatcher", Dispatchers.Default.limitedParallelism(1, "databasePoolDispatcher").toString())
    }

    object MyDispatcher : CoroutineDispatcher() {
        override fun dispatch(context: CoroutineContext, block: Runnable) {
            TODO("Not yet implemented")
        }

        @ExperimentalCoroutinesApi
        override fun limitedParallelism(parallelism: Int): CoroutineDispatcher {
            return super.limitedParallelism(parallelism, null)
        }
    }

    @Test
    fun testMyDispatcher() {
        (MyDispatcher).limitedParallelism(1)
        MyDispatcher.limitedParallelism(1)
    }
}