package com.vraj.cityconnect.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "aws.s3")
class AwsProperties {

    lateinit var accountId: String
    lateinit var bucketName: String
    lateinit var region: String
    lateinit var accessKey: String
    lateinit var secretKey: String
    lateinit var sessionToken: String
}
