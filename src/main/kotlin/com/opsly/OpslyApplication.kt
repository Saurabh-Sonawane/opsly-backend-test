package com.opsly

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import org.springframework.web.client.RestTemplate

@PropertySource("classpath:application.properties")
@SpringBootApplication
class OpslyApplication {
    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.build()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(OpslyApplication::class.java, *args)
        }
    }
}