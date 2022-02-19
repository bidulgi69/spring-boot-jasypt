package kr.dove.encrypt

import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import java.security.Security

@Configuration
@PropertySource("classpath:jasypt.properties")
class JasyptConfiguration(
    private val env: Environment,
) {
    private val _prefix = "jasypt.encryptor"

    @Bean
    fun jasyptEncryptor(): StringEncryptor {
        val encryptor = PooledPBEStringEncryptor()
        val config = SimpleStringPBEConfig()

        Security.addProvider(BouncyCastleProvider())

        val envPassword: String? = env.getProperty("${_prefix}.password")
        config.password = if (envPassword == null || envPassword == "") "test" else envPassword
        config.algorithm = env.getProperty("${_prefix}.algorithm")
        config.setKeyObtentionIterations(env.getProperty("${_prefix}.key-obtention-iterations"))
        config.setPoolSize(env.getProperty("${_prefix}.pool-size"))
        config.providerName = env.getProperty("${_prefix}.provider-name")
        config.setSaltGeneratorClassName(env.getProperty("${_prefix}.salt-generator-classname"))
        config.setIvGeneratorClassName(env.getProperty("${_prefix}.iv-generator-classname"))
        config.stringOutputType = env.getProperty("${_prefix}.string-output")

        encryptor.setConfig(config)
        return encryptor
    }
}