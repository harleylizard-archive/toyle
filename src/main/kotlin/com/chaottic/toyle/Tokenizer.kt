package com.chaottic.toyle

import java.util.*

class Tokenizer {

	fun tokenize(source: String) {
		StringTokenizer(source, " (){\t\n\r},:", true).also {
			while (it.hasMoreTokens()) {
				val next = it.nextToken()
				val optional = getToken(next)

				if (optional.isPresent) {
					println(optional.get() to next)
				}
			}
		}
	}

	private fun getToken(value: String): Optional<Token> {
		if (value == "package") {
			return Optional.of(Token.PACKAGE)
		} else if (value == "class") {
			return Optional.of(Token.CLASS)
		}  else if (value == "function") {
			return Optional.of(Token.FUNCTION)
		} else if (value == "var") {
			return Optional.of(Token.VAR)
		} else if (value == "=") {
			return Optional.of(Token.EQUALS)
		} else if (value.matches(name)) {
			return Optional.of(Token.IDENTIFIER)
		} else if (value == "{") {
			return Optional.of(Token.LBRACE)
		} else if (value == "}") {
			return Optional.of(Token.RBRACE)
		} else if (value == "(") {
			return Optional.of(Token.LPAREN)
		} else if (value == ")") {
			return Optional.of(Token.RPAREN)
		} else if (value == "->") {
			return Optional.of(Token.RETURN)
		} else if (value == ",") {
			return Optional.of(Token.COMMA)
		} else if (value == ":") {
			return Optional.of(Token.COLON)
		}
		return Optional.empty()
	}

	private companion object {
		val name = "^[a-zA-Z0-9_.]+\$".toRegex()
	}
}