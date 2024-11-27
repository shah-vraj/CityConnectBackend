package com.vraj.cityconnect.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sns.SnsClient

@Configuration
class SnsConfig(private val awsProperties: AwsProperties) {

    @Bean
    fun snsClient(): SnsClient = SnsClient.builder()
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
