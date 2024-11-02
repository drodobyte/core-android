package drodobyte.android.api

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import drodobyte.core.model.Id
import java.util.UUID

object IdAdapter : JsonAdapter<Id>() {
    @FromJson
    override fun fromJson(reader: JsonReader): UUID =
        Id.fromString(reader.nextString())

    @ToJson
    override fun toJson(writer: JsonWriter, id: Id?) {
        if (id != null) writer.value(id.toString())
    }
}
