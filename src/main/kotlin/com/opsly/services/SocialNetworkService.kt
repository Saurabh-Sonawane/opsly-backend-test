package com.opsly.services

import com.opsly.clients.SocialNetworkRestClient
import com.opsly.constant.AppConstant
import com.opsly.model.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SocialNetworkService {
    @Autowired
    lateinit var socialNetworkRestClient: SocialNetworkRestClient

    @get:Throws(Exception::class)
    val allSocialNetworkDetails: Response
        get() {
            val response = Response()
            val list1 = socialNetworkRestClient.getSocialNetworkDetails(AppConstant.TWITTER)
            val list2 = socialNetworkRestClient.getSocialNetworkDetails(AppConstant.FACEBOOK)
            val list3 = socialNetworkRestClient.getSocialNetworkDetails(AppConstant.INSTAGRAM)
            response.twitter = list1.get()
            response.facebook = list2.get()
            response.instagram = list3.get()
            return response
        }
}