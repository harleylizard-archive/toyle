package com.chaottic.toyle

import it.unimi.dsi.fastutil.ints.IntArrayList
import it.unimi.dsi.fastutil.ints.IntStack
import org.objectweb.asm.*
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

class Compiler {

	fun compile(name: String, list: List<TokenValue>, path: Path) {
		val size = list.filter { it.`is`(Token.ENUM) }.size

		val joined = "${list.get(1)!!.value.replace(".", "/")}/$name"

		Files.write(path, if (size == 1) enum(joined) else clazz(joined, list))
	}

	private fun function(iterator: Iterator<TokenValue>, cw: ClassWriter, stack: IntStack) {
		val identifier = iterator.next()

		val parameters = collectParameters(iterator)

		val builder = StringBuilder()
		builder.append('(')
		val parametersIterator = parameters.iterator()
		while (parametersIterator.hasNext()) {
			val next = parametersIterator.next()
			if (next.`is`(Token.COMMA)) {
				continue
			}

			val type = parametersIterator.next()
			if (type.`is`(Token.IDENTIFIER)) {
				builder.append(getType(type.value).descriptor)
			}
		}
		builder.append(')')

		val returnType = iterator.next()
		if (returnType.`is`(Token.RETURN)) {
			builder.append(getType(iterator.next().value).descriptor)
		} else {
			builder.append("V")
		}

		val mv = cw.visitMethod(getAccess(stack) + Opcodes.ACC_STATIC, identifier.value, builder.toString(), null, null)
		mv.visitCode()
		mv.visitEnd()
	}

	private fun enum(name: String): ByteArray {
		val cw = ClassWriter(ClassWriter.COMPUTE_FRAMES)
		cw.visit(Opcodes.V19, Opcodes.ACC_PUBLIC + Opcodes.ACC_ENUM, name, "java/lang/Enum", null, null)

		cw.visitEnd()
		return cw.toByteArray()
	}

	private fun enumConstant() {

	}

	private fun clazz(name: String, list: List<TokenValue>): ByteArray {
		val cw = ClassWriter(ClassWriter.COMPUTE_FRAMES)
		cw.visit(Opcodes.V19, Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL, name, null, "java/lang/Object", null)

		val constructor = cw.visitMethod(Opcodes.ACC_PRIVATE, "<init>", "()V", null, null)
		constructor.visitEnd()

		val access = IntArrayList()
		access.push(Opcodes.ACC_PUBLIC)

		val iterator = list.iterator();
		while (iterator.hasNext()) {
			val pair = iterator.next()

			if (pair.`is`(Token.PRIVATE)) {
				access.push(Opcodes.ACC_PRIVATE)
			}

			if (pair.`is`(Token.FUNCTION)) {
				function(iterator, cw, access)
			}
		}

		cw.visitEnd()
		return cw.toByteArray()
	}
	
	// Utility
	private companion object {

		fun getAccess(stack: IntStack) = if (stack.isEmpty) Opcodes.ACC_PUBLIC else stack.popInt()
		
		fun getType(value: String): Type = when (value) {
			"void" -> Type.VOID_TYPE
			"byte" -> Type.BYTE_TYPE
			"short" -> Type.SHORT_TYPE
			"int" -> Type.INT_TYPE
			"long" -> Type.LONG_TYPE
			"float" -> Type.FLOAT_TYPE
			"double" -> Type.DOUBLE_TYPE
			"boolean" -> Type.BOOLEAN_TYPE
			"string" -> Type.getType(String::class.java)
			else -> Type.getType(value)
		}
		
		fun collectParameters(iterator: Iterator<TokenValue>): List<TokenValue> = arrayListOf<TokenValue>().let {
			var next = iterator.next()
			while (iterator.next().also { next = it }.`is`(Token.RPAREN)) {
				it.add(next)
			}
			
			return@let Collections.unmodifiableList(it)
		}
	}
}