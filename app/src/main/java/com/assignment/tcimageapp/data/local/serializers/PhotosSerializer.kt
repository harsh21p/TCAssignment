package com.assignment.tcimageapp.data.local.serializers

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.assignment.tcimageapp.data.remote.dto.CachedPhotos
import jakarta.inject.Inject
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class PhotosSerializer @Inject constructor() : Serializer<CachedPhotos> {

    override val defaultValue: CachedPhotos = CachedPhotos()

    override suspend fun readFrom(input: InputStream): CachedPhotos =
        try {
            // TODO: Decryption from Crypto
            Json.decodeFromString(
                input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            throw CorruptionException("Unable to read cached photos", e)
        }

    override suspend fun writeTo(t: CachedPhotos, output: OutputStream) {
        // TODO: Encryption from Crypto
        output.write(
            Json.encodeToString(t).encodeToByteArray()
        )
    }
}