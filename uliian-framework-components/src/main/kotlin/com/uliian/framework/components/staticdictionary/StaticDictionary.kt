package com.uliian.framework.components.staticdictionary

/**
 * Marks an enum as a frontend-visible static dictionary.
 *
 * [value] is the stable, globally unique type used by the HTTP API.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class StaticDictionary(val value: String)
