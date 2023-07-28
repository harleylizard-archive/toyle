package com.chaottic.toyle

class Grammar private constructor() {

	companion object {
		private val function = ExplicitRule
		private val parameters = ExplicitRule
		private val expression = ExplicitRule

		private val grammar = grammar {
			function then TokenType.IDENTIFIER then parameters then expression

			parameters then TokenType.L_PARENTHESIS then TokenType.R_PARENTHESIS

			expression then TokenType.L_BRACKET then TokenType.R_BRACKET
		}

		private class Builder {

			infix fun Rule.then(rule: Rule): Rule {
				return ExplicitRule
			}

			fun build(): Grammar {
				return Grammar()
			}
		}

		private inline fun grammar(block: Builder.() -> Unit): Grammar {
			val builder = Builder()
			builder.block()
			return builder.build()
		}
	}
}