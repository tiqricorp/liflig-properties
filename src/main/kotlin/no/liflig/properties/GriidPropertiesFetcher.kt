package no.liflig.properties

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.json.JsonElement
import java.util.Properties

object GriidPropertiesFetcher {
    private val serializer = JsonElement.serializer()
    private val json = Json(JsonConfiguration.Stable)

    @JvmStatic
    fun forPrefix(ssmPrefix: String): Properties =
            Properties().apply {
                putAll(parametersByPath(ssmPrefix))
                putAll(secretsByPath(ssmPrefix))
            }

    private fun parametersByPath(path: String): Map<String, String> =
            AwsClientHelper.getParametersByPath("${path}/config/")
                    .mapKeys { (key, _) -> key.removePrefix("${path}/config/") }

    private fun secretsByPath(path: String): Map<String, String> =
            AwsClientHelper.getParametersByPath("${path}/secrets/")
                    .flatMap { (parameterKey, parameterValue) ->
                        val baseKey = parameterKey.removePrefix("${path}/secrets/")
                        val jsonSecret = AwsClientHelper.getSecret(parameterValue)

                        renameKeyAndSerializeValue(jsonSecret, baseKey)
                    }
                    .toMap()

    private fun renameKeyAndSerializeValue(jsonSecret: String, baseKey: String): List<Pair<String, String>> =
            json.parse(serializer, jsonSecret)
                    .jsonObject
                    .entries
                    .map { (secretKey, secretValue) ->
                        "${baseKey}.${secretKey}" to secretValue.toString()
                    }
                    .toList()
}