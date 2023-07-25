package com.chaottic.toyle

import java.util.*

class Tokenizer {

	fun tokenize(source: String): List<Pair<Token, String>> {
		val list = arrayListOf<Pair<Token, String>>()

		StringTokenizer(source, " (){\t\n\r},:", true).also {
			while (it.hasMoreTokens()) {
				val next = it.nextToken()
				val optional = getToken(next)

				if (optional.isPresent) {
					list.add(optional.get() to next)
				}
			}
		}

		return Collections.unmodifiableList(list)
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
		} else if (value == "private") {
			return Optional.of(Token.PRIVATE)
		} else if (value.matches(identifier)) {
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
		} else if (value == "=") {
			return Optional.of(Token.EQUALS)
		}
		return Optional.empty()
	}

	private companion object {
		val identifier = "^[a-zA-Z0-9_.]+\$".toRegex()
	}
}