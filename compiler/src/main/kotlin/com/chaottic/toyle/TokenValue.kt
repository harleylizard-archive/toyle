package com.chaottic.toyle

@JvmRecord
data class TokenValue(val token: Token, val value: String) {

	fun `is`(token: Token) = this.token == token

	companion object {

		infix fun Token.to(value: String) = TokenValue(this, value)
	}
}
