package com.chaottic.toyle

import org.junit.jupiter.api.Test
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes

class ToyleTest {

	@Test
	fun tokenizer() {
		Tokenizer().tokenize(Files.readString(Paths.get("src/test/resources/Example.toyle"))).forEach {
			println(it)
		}
	}

	@Test
	fun compile() {
		val compile = Compile()
		val tokenizer = Tokenizer()

		Files.walkFileTree(Paths.get("src/main/toyle"), object : SimpleFileVisitor<Path>() {

			override fun visitFile(file: Path, attrs: BasicFileAttributes?): FileVisitResult {
				if (file.toString().endsWith("toyle")) {
					val name = file.fileName.toString().let { it.substring(0, it.indexOf('.')) }
					val namespace = file.parent.toString().let { it.substring(it.indexOf("src") + 15) }

					val path = Paths.get("build/classes/toyle/test", namespace, "${name}.class")
					path.parent.takeUnless(Files::isDirectory)?.let(Files::createDirectories)

					compile.compile(name, tokenizer.tokenize(Files.readString(file)), path)
				}
				return super.visitFile(file, attrs)
			}
		})
	}
}