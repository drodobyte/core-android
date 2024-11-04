package drodobyte.android.api

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.util.UUID

object UuidAdapter : JsonAdapter<UUID>() {
    @FromJson
    override fun fromJson(reader: JsonReader): UUID? =
        reader.nextString().takeIf { it.isNotEmpty() }?.let(UUID::fromString)

    @ToJson
    override fun toJson(writer: JsonWriter, id: UUID?) {
        writer.value(id?.toString() ?: "")
    }
}
