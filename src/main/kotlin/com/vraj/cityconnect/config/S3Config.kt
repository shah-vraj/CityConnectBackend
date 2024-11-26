package com.vraj.cityconnect.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class S3Config(private val s3ConfigProperties: S3ConfigProperties) {

    @Bean
    fun s3Client(): S3Client = S3Client.builder()
        .region(Region.of(s3ConfigProperties.region))
        .credentialsProvider(
            StaticCredentialsProvider.create(
                AwsSessionCredentials.create(
                    s3ConfigProperties.accessKey,
                    s3ConfigProperties.secretKey,
                    s3ConfigProperties.sessionToken
                )
            )
        )
        .build()
}
