package com.chaottic.toyle

import java.util.*
import java.util.function.*

@JvmInline
value class Grammar private constructor(private val list: List<Rule>) : Predicate<Tokenizer> {

	override fun test(t: Tokenizer): Boolean {
		val iterator = t.iterator()
		while (iterator.hasNext()) {

		}

		return false
	}

	companion object {

		val grammar = grammar {
			val parameter = create(TokenType.IDENTIFIER then TokenType.COLON then TokenType.IDENTIFIER)

			val parameters = create(TokenType.L_PARENTHESIS then parameter then TokenType.R_PARENTHESIS)

			val expression = create(TokenType.L_BRACKET then TokenType.R_BRACKET)

			val function = create(TokenType.FUNCTION then TokenType.IDENTIFIER then parameters then expression)
		}

		private class Builder {
			private val list = mutableListOf<Rule>()

			infix fun Rule.then(rule: Rule): Rule {
				return ExplicitRule {
					it.hasNext() && this.test(it) && rule.test(it)
				}
			}

			fun create(rule: Rule) = rule.also(list::add)

			fun build() = Grammar(Collections.unmodifiableList(list))
		}

		private inline fun grammar(block: Builder.() -> Unit): Grammar {
			val builder = Builder()
			builder.block()
			return builder.build()
		}
	}
}