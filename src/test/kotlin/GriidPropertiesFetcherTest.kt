import no.liflig.properties.GriidPropertiesFetcher
import org.junit.jupiter.api.Assertions.*

internal class GriidPropertiesFetcherTest {

    @org.junit.jupiter.api.Test
    fun `renameKeyAndSerializeValue serializes values correctly`() {
        val expected = listOf(Pair("app.db.password", "eeShee0haiv9"))
        val jsonSecret = """{"password": "eeShee0haiv9"}"""
        val baseKey = "app.db"
        val ret = GriidPropertiesFetcher.renameKeyAndSerializeValue(jsonSecret, baseKey)
        assertEquals(expected, ret)
    }
}
