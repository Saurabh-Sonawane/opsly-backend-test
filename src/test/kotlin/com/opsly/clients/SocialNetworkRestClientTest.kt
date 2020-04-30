package com.opsly.clients

import com.opsly.constant.AppConstant
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.*
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestTemplate
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.CompletableFuture

@RunWith(SpringRunner::class)
@SpringBootTest
class SocialNetworkRestClientTest {
    @Mock
    lateinit var restTemplate: RestTemplate

    @InjectMocks
    lateinit var socialNetworkRestClient: SocialNetworkRestClient

    @Test
    @Throws(Exception::class)
    fun shouldReturnListOfSocialNetworkDetails() {
        //given
        ReflectionTestUtils.setField(socialNetworkRestClient, "clientUrl", "localhost:8085")
        val map = LinkedHashMap<String, String>()
        map["username"] = "@abc"
        map["tweet"] = "this tweet is from @abc"
        val list: MutableList<LinkedHashMap<String, String>> = ArrayList()
        list.add(map)
        val headers = HttpHeaders()
        headers.accept = Arrays.asList(MediaType.APPLICATION_JSON)
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
        val entity = HttpEntity("parameters", headers)
        val list1 = CompletableFuture.completedFuture<List<LinkedHashMap<String, String>>>(list)
        Mockito.`when`(restTemplate.exchange(Matchers.eq("localhost:8085/twitter"), Matchers.eq(HttpMethod.GET), Matchers.eq(entity), Matchers.eq(MutableList::class.java))).thenReturn(ResponseEntity(list, HttpStatus.OK))

        //when
        val networkDetails: CompletableFuture<List<LinkedHashMap<String, String>?>> = socialNetworkRestClient.getSocialNetworkDetails(AppConstant.TWITTER)

        //then
        Assert.assertNotNull(networkDetails)
        Assert.assertEquals(1, networkDetails.get().size.toLong())
        Assert.assertEquals("@abc", networkDetails.get()[0]!!["username"])
        Assert.assertEquals("this tweet is from @abc", networkDetails.get()[0]!!["tweet"])
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnErrorIfNetworkThrowsSomeError() {
        //given
        ReflectionTestUtils.setField(socialNetworkRestClient, "clientUrl", "localhost:8085")
        val headers = HttpHeaders()
        headers.accept = Arrays.asList(MediaType.APPLICATION_JSON)
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
        val entity = HttpEntity("parameters", headers)
        Mockito.`when`(restTemplate.exchange(Matchers.eq("localhost:8085/twitter"), Matchers.eq(HttpMethod.GET), Matchers.eq(entity), Matchers.eq(MutableList::class.java))).thenThrow(HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "some issue".toByteArray(), Charset.defaultCharset()))

        //when
        val networkDetails: CompletableFuture<List<LinkedHashMap<String, String>?>> = socialNetworkRestClient!!.getSocialNetworkDetails(AppConstant.TWITTER)

        //then
        Assert.assertNotNull(networkDetails)
        Assert.assertEquals(1, networkDetails.get().size.toLong())
        Assert.assertEquals("some issue", networkDetails.get()[0]!!["error"])
    }
}