package com.opsly.clients

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestTemplate
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class SocialNetworkRestClient {

    @Autowired
    lateinit var restTemplate: RestTemplate

    @Value("\${client.base.url}")
    private val clientUrl: String? = null

    @Async
    fun getSocialNetworkDetails(network: String): CompletableFuture<List<LinkedHashMap<String, String>?>> {
        var networkList: MutableList<LinkedHashMap<String, String>?> = ArrayList()
        var map: LinkedHashMap<String, String> = LinkedHashMap<String, String>()
        try {
            val headers = HttpHeaders()
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON))
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
            val entity: HttpEntity<String> = HttpEntity<String>("parameters", headers)
            val response: ResponseEntity<List<*>> = restTemplate.exchange("$clientUrl/$network", HttpMethod.GET, entity, List::class.java)
            networkList = response.getBody() as MutableList<LinkedHashMap<String, String>?>
        } catch (clientErrorException: HttpClientErrorException) {
            map["error"] = clientErrorException.getResponseBodyAsString()
            networkList.add(map)
        } catch (httpServerErrorException: HttpServerErrorException) {
            System.out.println(httpServerErrorException)
            map["error"] = httpServerErrorException.getResponseBodyAsString()
            networkList.add(map)
        }
        return CompletableFuture.completedFuture(networkList)
    }
}