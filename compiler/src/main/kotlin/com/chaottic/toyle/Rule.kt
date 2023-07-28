package com.chaottic.toyle

import java.util.function.Predicate

sealed interface Rule : Predicate<Iterator<Token>>