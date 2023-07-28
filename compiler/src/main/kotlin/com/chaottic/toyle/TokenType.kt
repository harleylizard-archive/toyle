package com.chaottic.toyle

enum class TokenType : Rule {
	PACKAGE,
	IMPORT,
	CLASS,
	FUNCTION,

	IDENTIFIER,

	RETURN,
	RETURN_LIGATURE,

	COLON,
	COMMA,

	L_PARENTHESIS,
	R_PARENTHESIS,
	L_BRACKET,
	R_BRACKET
	;

	fun asToken() = Token(this, Optional.empty())

	override fun test(t: Iterator<Token>) = t.next().`is`(this)

	companion object {
		infix fun TokenType.to(string: String) = Token(this, Optional(string))
	}
}