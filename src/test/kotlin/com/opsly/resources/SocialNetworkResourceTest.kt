package com.opsly.resources

import com.opsly.model.Response
import com.opsly.resources.SocialNetworkResource
import com.opsly.services.SocialNetworkService
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.client.RestTemplate
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SocialNetworkResourceTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var socialNetworkService: SocialNetworkService

    @MockBean
    lateinit var restTemplate: RestTemplate

    @Test
    @Throws(Exception::class)
    fun socialNetworkDetailReturnsOK() {
        BDDMockito.given(socialNetworkService.allSocialNetworkDetails).willReturn(populateResponse())
        mockMvc.perform(MockMvcRequestBuilders.get(""))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.twitter", Matchers.hasSize<Any>(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.facebook", Matchers.hasSize<Any>(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instagram", Matchers.hasSize<Any>(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.twitter[0].username", Matchers.`is`("@abc")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.twitter[0].tweet", Matchers.`is`("this tweet is from @abc")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.facebook[0].name", Matchers.`is`("facebook user")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.facebook[0].status", Matchers.`is`("facebook status")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instagram[0].username", Matchers.`is`("insta user")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instagram[0].picture", Matchers.`is`("insta picture")))

    }


    @Test
    @Throws(Exception::class)
    fun socialNetworkDetailReturnsError() {

            BDDMockito.given(socialNetworkService.allSocialNetworkDetails).willThrow(Exception())
            mockMvc.perform(MockMvcRequestBuilders.get(""))
                    .andExpect(MockMvcResultMatchers.status().is5xxServerError)
    }

    private fun populateResponse(): Response {
        val response = Response()
        val map = LinkedHashMap<String, String>()
        map["username"] = "@abc"
        map["tweet"] = "this tweet is from @abc"
        val list: MutableList<LinkedHashMap<String, String>?> = ArrayList()
        list.add(map)
        val facebookMap = LinkedHashMap<String, String>()
        facebookMap["name"] = "facebook user"
        facebookMap["status"] = "facebook status"
        val facebooklist: MutableList<LinkedHashMap<String, String>?> = ArrayList()
        facebooklist.add(facebookMap)
        val instaMap = LinkedHashMap<String, String>()
        instaMap["username"] = "insta user"
        instaMap["picture"] = "insta picture"
        val instalist: MutableList<LinkedHashMap<String, String>?> = ArrayList()
        instalist.add(instaMap)
        response.twitter = list
        response.facebook = facebooklist
        response.instagram = instalist
        return response
    }
}