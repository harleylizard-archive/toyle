package com.chaottic.toyle

@JvmRecord
data class Token(private val type: TokenType, private val optional: Optional<String>) : Comparable<TokenType> {

	fun `is`(type: TokenType) = this.type == type

	override fun compareTo(other: TokenType) = type.compareTo(other)
}
