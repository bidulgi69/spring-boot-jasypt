package kr.dove.encrypt

import org.jasypt.encryption.StringEncryptor
import org.jasypt.registry.AlgorithmRegistry
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class JasyptTest(
	@Autowired private val jasyptEncryptor: StringEncryptor,
) {
	private val logger: Logger = LoggerFactory.getLogger(javaClass)

	@Test
	@DisplayName("Encryption test")
	fun jasypt_test() {
		val plainText = "plain text"
		val encryptText = jasyptEncryptor.encrypt(plainText)
		logger.info("After encryption: {}", encryptText)
		val decryptText = jasyptEncryptor.decrypt(encryptText)
		logger.info("After decryption: {}", decryptText)

		Assertions.assertEquals(plainText, decryptText)
	}

	@Test
	@DisplayName("All possible algorithms")
	fun algorithms() {
		logger.info("DigestAlgorithms: {}", AlgorithmRegistry.getAllDigestAlgorithms())
		logger.info("PBEAlgorithms: {}", AlgorithmRegistry.getAllPBEAlgorithms())
	}
}
