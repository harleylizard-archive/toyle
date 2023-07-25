package com.chaottic.toyle

import it.unimi.dsi.fastutil.ints.IntArrayList
import it.unimi.dsi.fastutil.ints.IntStack
import org.objectweb.asm.*
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

class Compile {

	fun compile(name: String, list: List<Pair<Token, String>>, path: Path) {
		val cw = ClassWriter(ClassWriter.COMPUTE_FRAMES)
		cw.visit(Opcodes.V19, Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL, "${list.get(1)!!.second.replace(".", "/")}/$name", null, "java/lang/Object", null)

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

		val parameters = collectParameters(iterator)

		val builder = StringBuilder()
		builder.append('(')
		val parametersIterator = parameters.iterator()
		while (parametersIterator.hasNext()) {
			val next = parametersIterator.next()
			if (next.first == Token.COMMA) {
				continue
			}

			val type = parametersIterator.next()
			if (type.first == Token.IDENTIFIER) {
				builder.append(getType(type.second).descriptor)
			}
		}
		builder.append(')')

		val returnType = iterator.next()
		if (returnType.first == Token.RETURN) {
			builder.append(getType(iterator.next().second).descriptor)
		} else {
			builder.append("V")
		}

		val mv = cw.visitMethod(getAccess(stack) + Opcodes.ACC_STATIC, identifier.second, builder.toString(), null, null)
		mv.visitCode()
		mv.visitEnd()
	}

	private fun collectParameters(iterator: Iterator<Pair<Token, String>>): List<Pair<Token, String>> {
		val list = arrayListOf<Pair<Token, String>>()

		var next = iterator.next()
		while (iterator.next().also { next = it }.first != Token.RPAREN) {
			list.add(next)
		}

		return Collections.unmodifiableList(list)
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
		"boolean" -> Type.BOOLEAN_TYPE
		else -> Type.getType(value)
	}
}