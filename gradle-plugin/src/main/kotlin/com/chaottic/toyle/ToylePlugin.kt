package com.chaottic.toyle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension

class ToylePlugin : Plugin<Project> {
	override fun apply(target: Project) {
		target.pluginManager.apply(JavaPlugin::class.java)

		target.extensions.getByType(JavaPluginExtension::class.java).sourceSets.all {
			val toyleSourceDirectorySet = target.objects.sourceDirectorySet("toyle", "Toyle")
			toyleSourceDirectorySet.srcDir("src/${it.name}/toyle")
			toyleSourceDirectorySet.filter.include("**/*.toyle")

			it.extensions.add("toyle", toyleSourceDirectorySet)

			it.allJava.source(toyleSourceDirectorySet)
			it.allSource.source(toyleSourceDirectorySet)

		}
	}
}