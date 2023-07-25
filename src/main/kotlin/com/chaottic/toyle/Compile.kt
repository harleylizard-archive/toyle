package com.chaottic.toyle

import it.unimi.dsi.fastutil.ints.IntArrayList
import it.unimi.dsi.fastutil.ints.IntStack
import org.objectweb.asm.*
import java.nio.file.Files
import java.nio.file.Path

class Compile {

	fun compile(name: String, list: List<Pair<Token, String>>, path: Path) {
		val cw = ClassWriter(ClassWriter.COMPUTE_FRAMES)
		cw.visit(Opcodes.V19, Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL, name, null, "java/lang/Object", null)

		val constructor = cw.visitMethod(Opcodes.ACC_PRIVATE, "<init>", "()V", null, null)
		constructor.visitEnd()

		val access = IntArrayList()
		access.push(Opcodes.ACC_PUBLIC)

		val iterator = list.iterator();
		while (iterator.hasNext()) {
			val pair = iterator.next()

			if (pair.first == Token.PRIVATE) {
				access.push(Opcodes.ACC_PRIVATE)
			}

			if (pair.first == Token.FUNCTION) {
				function(iterator, cw, access)
			}
		}

		cw.visitEnd()
		Files.write(path, cw.toByteArray())
	}

	private fun function(iterator: Iterator<Pair<Token, String>>, cw: ClassWriter, stack: IntStack) {
		val identifier = iterator.next()

		val builder = StringBuilder()
		builder.append(iterator.next().second)
		builder.append(iterator.next().second)

		val returnType = iterator.next()
		if (returnType.first == Token.RETURN) {
			builder.append(getType(iterator.next().second).descriptor)
		} else {
			builder.append("V")
		}

		val mv = cw.visitMethod(getAccess(stack) + Opcodes.ACC_STATIC, identifier.second, builder.toString(), null, null)
		mv.visitEnd()
	}

	private fun getAccess(stack: IntStack) = if (stack.isEmpty) Opcodes.ACC_PUBLIC else stack.popInt()

	private fun getType(value: String) = when (value) {
		"void" -> Type.VOID_TYPE
		"byte" -> Type.BYTE_TYPE
		"short" -> Type.SHORT_TYPE
		"int" -> Type.INT_TYPE
		"long" -> Type.LONG_TYPE
		"float" -> Type.FLOAT_TYPE
		"double" -> Type.DOUBLE_TYPE
		else -> throw RuntimeException()
	}
}