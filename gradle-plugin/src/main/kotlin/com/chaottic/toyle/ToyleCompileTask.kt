package com.chaottic.toyle

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.internal.tasks.compile.HasCompileOptions
import org.gradle.api.logging.LogLevel
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.compile.AbstractCompile
import org.gradle.api.tasks.compile.CompileOptions

open class ToyleCompileTask : AbstractCompile(), HasCompileOptions {
	@Input
	private val options = project.objects.newInstance(CompileOptions::class.java)

	@TaskAction
	fun compile() {

	}

	override fun getOptions(): CompileOptions = options
}