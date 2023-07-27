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
			val tokenizer = StringTokenizer(source, " (){\t\n\r},:", true)

			val list = buildList {
				while (tokenizer.hasMoreTokens()) {
					getToken(tokenizer.nextToken())?.let(this::add)
				}
			}

			return Tokenizer(Collections.unmodifiableList(list))
		}

		private fun getToken(string: String): Token? {
			return when (string) {
				"package" -> TokenType.PACKAGE.asToken()
				"class" -> TokenType.CLASS.asToken()
				"function" -> TokenType.FUNCTION.asToken()
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