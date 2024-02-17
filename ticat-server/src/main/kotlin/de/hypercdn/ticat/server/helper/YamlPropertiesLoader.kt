package de.hypercdn.ticat.server.helper

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.springframework.util.PropertiesPersister
import org.springframework.vault.support.JsonMapFlattener
import org.xnio.streams.ReaderInputStream
import java.io.*
import java.util.*


class YamlPropertiesLoader : PropertiesPersister {
    override fun load(props: Properties, inputStream: InputStream) {
        val yaml = ObjectMapper(YAMLFactory()).readValue(inputStream, Map::class.java)
        val flatYaml = JsonMapFlattener.flattenToStringMap(yaml as Map<String, Any?>)
        flatYaml.entries.removeIf { it.value == null }
        props.putAll(flatYaml)
    }

    override fun load(props: Properties, reader: Reader) = load(props, ReaderInputStream(reader))

    override fun store(props: Properties, os: OutputStream, header: String) = throw UnsupportedEncodingException("Storing to XML is not supported by YamlPropertiesLoader")

    override fun store(props: Properties, writer: Writer, header: String) = throw UnsupportedEncodingException("Storing to XML is not supported by YamlPropertiesLoader")

    override fun loadFromXml(props: Properties, inputStream: InputStream) = throw UnsupportedEncodingException("Storing to XML is not supported by YamlPropertiesLoader")

    override fun storeToXml(props: Properties, outputStream: OutputStream, header: String) = throw UnsupportedEncodingException("Storing to XML is not supported by YamlPropertiesLoader")

    override fun storeToXml(props: Properties, outputStream: OutputStream, header: String, encoding: String) = throw UnsupportedEncodingException("Storing to XML is not supported by YamlPropertiesLoader")

}