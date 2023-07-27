package com.chaottic.toyle

enum class TokenType {
	PACKAGE,
	CLASS,
	FUNCTION,

	IDENTIFIER,

	L_PARENTHESIS,
	R_PARENTHESIS,
	L_BRACKET,
	R_BRACKET
	;

	fun asToken() = Token(this, Optional.empty())

	companion object {
		infix fun TokenType.to(string: String) = Token(this, Optional(string))
	}
}