package com.chaottic.toyle

@JvmInline
value class Optional<T>(private val t: T) {

	companion object {
		private val empty = Optional(null)

		@Suppress("UNCHECKED_CAST")
		fun <T> empty() = empty as Optional<T>
	}
}