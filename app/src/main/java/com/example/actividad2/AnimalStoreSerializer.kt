package com.example.actividad2

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.datastoreapp.AnimalStore
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

//TODO 6 create the serializer (proto datastore)
object AnimalStoreSerializer : Serializer<AnimalStore> {

    override val defaultValue: AnimalStore = AnimalStore.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AnimalStore {
        try {
            return AnimalStore.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: AnimalStore, output: OutputStream) = t.writeTo(output)
}