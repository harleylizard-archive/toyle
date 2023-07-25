package com.chaottic.toyle

import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

class ToyleTest {

	@Test
	fun tokenizer() {
		Tokenizer().tokenize(Files.readString(Paths.get("src/test/resources/Example.toyle")))
	}

	@Test
	fun compile() {
		Compile().compile("Example", Tokenizer().tokenize(Files.readString(Paths.get("src/test/resources/Example.toyle"))), Paths.get("build/Example.class"))
	}
}