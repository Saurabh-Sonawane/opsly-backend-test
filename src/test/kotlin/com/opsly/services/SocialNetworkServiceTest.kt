package com.opsly.services

import com.opsly.clients.SocialNetworkRestClient
import com.opsly.constant.AppConstant
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.util.ReflectionTestUtils
import java.util.*
import java.util.concurrent.CompletableFuture

@RunWith(SpringRunner::class)
@SpringBootTest
class SocialNetworkServiceTest {
    @Mock
    lateinit var socialNetworkRestClient: SocialNetworkRestClient

    @InjectMocks
    lateinit var socialNetworkService: SocialNetworkService

    @Test
    @Throws(Exception::class)
    fun shouldReturnAllSocialNetworkDetails() {
        //given
        ReflectionTestUtils.setField(socialNetworkRestClient, "clientUrl", "localhost:8085")
        val map = LinkedHashMap<String, String>()
        map["username"] = "@abc"
        map["tweet"] = "this tweet is from @abc"
        val list: MutableList<LinkedHashMap<String, String>> = ArrayList()
        list.add(map)
        val facebookMap = LinkedHashMap<String, String>()
        facebookMap["name"] = "facebook user"
        facebookMap["status"] = "facebook status"
        val facebooklist: MutableList<LinkedHashMap<String, String>> = ArrayList()
        facebooklist.add(facebookMap)
        val instaMap = LinkedHashMap<String, String>()
        instaMap["username"] = "insta user"
        instaMap["picture"] = "insta picture"
        val instalist: MutableList<LinkedHashMap<String, String>> = ArrayList()
        instalist.add(instaMap)
        val headers = HttpHeaders()
        headers.accept = Arrays.asList(MediaType.APPLICATION_JSON)
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
        val entity = HttpEntity("parameters", headers)
        Mockito.`when`(socialNetworkRestClient.getSocialNetworkDetails(AppConstant.TWITTER)).thenReturn(CompletableFuture.completedFuture(list))
        Mockito.`when`(socialNetworkRestClient.getSocialNetworkDetails(AppConstant.FACEBOOK)).thenReturn(CompletableFuture.completedFuture(facebooklist))
        Mockito.`when`(socialNetworkRestClient.getSocialNetworkDetails(AppConstant.INSTAGRAM)).thenReturn(CompletableFuture.completedFuture(instalist))

        //when
        val response = socialNetworkService.allSocialNetworkDetails

        //then
        Assert.assertNotNull(response)
        Assert.assertEquals("@abc", response.twitter!![0]!!["username"])
        Assert.assertEquals("this tweet is from @abc", response.twitter!![0]!!["tweet"])
        Assert.assertEquals("facebook user", response.facebook!![0]!!["name"])
        Assert.assertEquals("facebook status", response.facebook!![0]!!["status"])
        Assert.assertEquals("insta user", response.instagram!![0]!!["username"])
        Assert.assertEquals("insta picture", response.instagram!![0]!!["picture"])
    }
}