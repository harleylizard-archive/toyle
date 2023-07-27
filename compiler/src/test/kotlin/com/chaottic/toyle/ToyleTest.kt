package com.chaottic.toyle

import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

class ToyleTest {

	@Test
	fun tokenizer() {
		val tokenizer = Tokenizer.tokenize(Files.readString(Paths.get("src/main/toyle/com/chaottic/toyle/Example.toyle")))
		println(tokenizer.toString())
	}

	@Test
	fun compile() {
		//Files.walkFileTree(Paths.get("src/main/toyle"), object : SimpleFileVisitor<Path>() {
		//	override fun visitFile(file: Path, attrs: BasicFileAttributes?): FileVisitResult {
		//		if (file.toString().endsWith("toyle")) {
		//			val name = file.fileName.toString().let { it.substring(0, it.indexOf('.')) }
//
		//			Compiler.compile(name, Tokenizer.tokenize(Files.readString(file))).thenAccept { bytes ->
		//				val namespace = file.parent.toString().let { it.substring(it.indexOf("src") + 15) }
		//				val path = Paths.get("build/classes/toyle/test", namespace, "${name}.class")
//
		//				path.parent.takeUnless(Files::isDirectory)?.let(Files::createDirectories)
		//				Files.write(path, bytes)
		//			}
		//		}
		//		return super.visitFile(file, attrs)
		//	}
		//})
	}
}