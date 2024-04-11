package kotlinx.coroutines

import kotlinx.coroutines.testing.*
import org.junit.Test
import org.openjdk.jol.info.ClassLayout
import org.openjdk.jol.info.GraphLayout
import kotlin.test.*


class MemoryFootprintTest : TestBase(true) {

    @Test
    fun testJobLayout() = assertLayout(Job().javaClass, 24)

    @Test
    fun testCancellableContinuationFootprint() = assertLayout(CancellableContinuationImpl::class.java, 48)


    @Test
    fun testJobSize() {
        assertTotalSize(jobWithChildren(1), 112)
        assertTotalSize(jobWithChildren(2), 336) // originally: 192
        assertTotalSize(jobWithChildren(3), 416) // originally: 248
        assertTotalSize(jobWithChildren(4), 496) // originally: 304
    }

    private fun jobWithChildren(numberOfChildren: Int): Job {
        val result = Job()
        repeat(numberOfChildren) {
            Job(result)
        }
        return result
    }

    private fun assertLayout(clz: Class<*>, expectedSize: Int) {
        val size = ClassLayout.parseClass(clz).instanceSize()
//        println(ClassLayout.parseClass(clz).toPrintable())
        assertEquals(expectedSize.toLong(), size)
    }

    private fun assertTotalSize(instance: Job, expectedSize: Int) {
        val size = GraphLayout.parseInstance(instance).totalSize()
        assertEquals(expectedSize.toLong(), size)
    }
}
