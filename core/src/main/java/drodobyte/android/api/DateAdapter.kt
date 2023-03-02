package drodobyte.android.api

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.util.*

object DateAdapter : JsonAdapter<Date>() {
    @FromJson
    override fun fromJson(reader: JsonReader) =
        try {
            Date(reader.nextLong())
        } catch (e: Exception) {
            null
        }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        if (value != null) writer.value(value.time)
    }
}
