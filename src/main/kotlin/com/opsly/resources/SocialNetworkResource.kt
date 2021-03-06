package com.opsly.resources

import com.opsly.model.Response
import com.opsly.services.SocialNetworkService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController



@Api(value = "Social Network Resource", description = "Social network resource to fetch details for twitter, facebook and instagram")
@RestController
@RequestMapping("")
class SocialNetworkResource {
    @Autowired
    lateinit var socialNetworkService: SocialNetworkService

    private val logger = LoggerFactory.getLogger(SocialNetworkResource::class.java)

    @ApiOperation(value = "Fetch social network details for twitter, facebook and instagram", response = Response::class)
    @GetMapping(value = [""], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun socialNetworkDetail(): ResponseEntity<*> {
        return try {
            ResponseEntity(socialNetworkService.allSocialNetworkDetails, HttpStatus.OK)
        } catch (e: Exception) {
            logger.error("SocialNetworkResource : getSocialNetworkDetail : failed due to exception {}", e)
            ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}