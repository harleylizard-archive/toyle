package com.chaottic.toyle

import com.chaottic.toyle.TokenType.Companion.to
import java.util.*

@JvmInline
value class Tokenizer private constructor(private val list: MutableList<Token>) {

	override fun toString(): String {
		val builder = StringBuilder()

		list.forEach {
			builder.append(it.toString()).append("\n")
		}
		return builder.toString()
	}

	companion object {
		private val id = "^[a-zA-Z0-9_.]+\$".toRegex()

		fun tokenize(source: String): Tokenizer {
			val tokenizer = StringTokenizer(removeComments(source), " (){\t\n\r},:", true)

			val list = buildList {
				while (tokenizer.hasMoreTokens()) {
					getToken(tokenizer.nextToken())?.let(this::add)
				}
			}

			return Tokenizer(Collections.unmodifiableList(list))
		}

		private fun removeComments(source: String): String {
			val builder = StringBuilder()
			var comment = false

			for (i in source.indices) {
				val char = source[i]

				if (comment) {
					if (char == '\n') {
						comment = false
					}
				} else {
					if (char == '/' && i + 1 < source.length && source[i + 1] == '/') {
						comment = true
					} else {
						builder.append(char)
					}
				}
			}

			return builder.toString()
		}

		private fun getToken(string: String): Token? {
			return when (string) {
				"package" -> TokenType.PACKAGE.asToken()
				"import" -> TokenType.IMPORT.asToken()
				"class" -> TokenType.CLASS.asToken()
				"function" -> TokenType.FUNCTION.asToken()
				"return" -> TokenType.RETURN.asToken()
				"->" -> TokenType.RETURN_LIGATURE.asToken()
				":" -> TokenType.COLON.asToken()
				"," -> TokenType.COMMA.asToken()
				"(" -> TokenType.L_PARENTHESIS.asToken()
				")" -> TokenType.R_PARENTHESIS.asToken()
				"{" -> TokenType.L_BRACKET.asToken()
				"}" -> TokenType.R_BRACKET.asToken()
				else -> {
					if (string.matches(id)) return TokenType.IDENTIFIER to string

					null
				}
			}
		}
	}
}