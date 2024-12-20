package com.vraj.cityconnect.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class S3Config(private val awsProperties: AwsProperties) {

    @Bean
    fun s3Client(): S3Client = S3Client.builder()
        .region(Region.of(awsProperties.region))
        .credentialsProvider(
            StaticCredentialsProvider.create(
                AwsSessionCredentials.create(
                    awsProperties.accessKey,
                    awsProperties.secretKey,
                    awsProperties.sessionToken
                )
            )
        )
        .build()
}
