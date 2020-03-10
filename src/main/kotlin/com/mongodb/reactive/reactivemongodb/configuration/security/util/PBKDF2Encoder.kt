package com.mongodb.reactive.reactivemongodb.configuration.security.util

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import java.util.*
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

@Component
class PBKDF2Encoder : PasswordEncoder {

    @Value("\${password.encoder.secret}")
    private lateinit var secret: String

    @Value("\${password.encoder.iteration}")
    private var iteration: Int = 0

    @Value("\${password.encoder.keyLength}")
    private var keyLength: Int = 0

    override fun encode(rawPassword: CharSequence?): String {
        return try {
            SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512").generateSecret(PBEKeySpec(
                    rawPassword.toString().toCharArray(),
                    secret.toByteArray(),
                    iteration,
                    keyLength
            )).encoded.run(Base64.getEncoder()::encodeToString)
        } catch (exception: NoSuchAlgorithmException) {
            throw RuntimeException(exception)
        } catch (exception: InvalidKeySpecException) {
            throw RuntimeException(exception)
        }
    }

    override fun matches(rawPassword: CharSequence?, encodedPassword: String?) = encode(rawPassword) == encodedPassword
}