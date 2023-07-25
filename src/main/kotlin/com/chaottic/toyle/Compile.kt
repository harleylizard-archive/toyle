package com.chaottic.toyle

import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import java.nio.file.Files
import java.nio.file.Path

class Compile {

	fun compile(name: String, list: List<Pair<Token, String>>, path: Path) {
		val cw = ClassWriter(ClassWriter.COMPUTE_FRAMES)
		cw.visit(Opcodes.V19, Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL, name, null, "java/lang/Object", null)

		val constructor = cw.visitMethod(Opcodes.ACC_PRIVATE, "<init>", "()V", null, null)
		constructor.visitEnd()

		val iterator = list.iterator();
		while (iterator.hasNext()) {
			val pair = iterator.next()

			if (pair.first == Token.FUNCTION) {
				val identifier = iterator.next()

				val mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, identifier.second, "()V", null, null)
				mv.visitEnd()
			}
		}

		cw.visitEnd()
		Files.write(path, cw.toByteArray())
	}
}