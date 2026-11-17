package com.uliian.framework.components.staticdictionary

import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AnnotationTypeFilter
import org.springframework.util.ClassUtils
import java.util.LinkedHashMap

/**
 * Discovers and validates static dictionary enums at application startup.
 */
class StaticDictionaryRegistry(
    basePackages: Collection<String>,
    classLoader: ClassLoader = requireNotNull(ClassUtils.getDefaultClassLoader())
) {
    private val dictionaries: Map<String, Class<out Enum<*>>>

    init {
        val scanner = ClassPathScanningCandidateComponentProvider(false)
        scanner.addIncludeFilter(AnnotationTypeFilter(StaticDictionary::class.java))
        val found = LinkedHashMap<String, Class<out Enum<*>>>()

        basePackages.asSequence()
            .filter { it.isNotBlank() }
            .distinct()
            .flatMap { scanner.findCandidateComponents(it).asSequence() }
            .distinctBy { it.beanClassName }
            .forEach { candidate ->
                val className = requireNotNull(candidate.beanClassName)
                val candidateClass = ClassUtils.forName(className, classLoader)
                val dictionary = requireNotNull(candidateClass.getAnnotation(StaticDictionary::class.java))
                val enumClass = validateDictionaryClass(candidateClass)
                check(found.putIfAbsent(dictionary.value, enumClass) == null) {
                    "Duplicate static dictionary type: ${dictionary.value}"
                }
                validateValues(dictionary.value, enumClass)
            }

        dictionaries = found.toMap()
    }

    /**
     * Returns options ordered by sort, with unsorted options last.
     * Equal sort values preserve enum declaration order.
     */
    fun get(type: String): List<StaticDictionaryOption> {
        val enumClass = dictionaries[type]
            ?: throw IllegalArgumentException("Unknown static dictionary type: $type")
        return enumClass.enumConstants
            .map { it as StaticDictionaryItem }
            .sortedWith(compareBy(nullsLast<Int>()) { it.sort })
            .map(StaticDictionaryItem::toOption)
    }

    @Suppress("UNCHECKED_CAST")
    private fun validateDictionaryClass(candidateClass: Class<*>): Class<out Enum<*>> {
        check(candidateClass.isEnum && StaticDictionaryItem::class.java.isAssignableFrom(candidateClass)) {
            "Static dictionary must be an enum implementing StaticDictionaryItem: ${candidateClass.name}"
        }
        return candidateClass as Class<out Enum<*>>
    }

    private fun validateValues(type: String, enumClass: Class<out Enum<*>>) {
        val values = LinkedHashMap<String, Enum<*>>()
        enumClass.enumConstants.forEach { constant ->
            val item = constant as StaticDictionaryItem
            check(values.putIfAbsent(item.value, constant) == null) {
                "Duplicate value '${item.value}' in static dictionary '$type' (${constant.name})"
            }
        }
    }
}
