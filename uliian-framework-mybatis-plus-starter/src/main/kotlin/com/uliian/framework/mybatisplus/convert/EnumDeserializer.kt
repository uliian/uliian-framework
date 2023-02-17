package com.uliian.framework.mybatisplus.convert

import com.baomidou.mybatisplus.annotation.EnumValue
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.deser.ContextualDeserializer
import com.uliian.framework.core.convert.IDescribeEnum
import java.io.IOException

/**
 * Created with IntelliJ IDEA.
 * @author Anders Xiao
 * @date 2019-10-15
 */
class EnumDeserializer(private val property: BeanProperty? = null) : JsonDeserializer<Enum<*>>(), ContextualDeserializer {

    override fun createContextual(ctxt: DeserializationContext?, property: BeanProperty?): JsonDeserializer<*> {
        return EnumDeserializer(property)
    }

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Enum<*>? {
        val rawValue = p.text?.trim()?.toIntOrNull()
        if(rawValue != null){
            try {
                val clazz = if(ctxt.contextualType != null) ctxt.contextualType.rawClass else this.property!!.type.rawClass
                return this.getEnumFromNumber(clazz, rawValue)
            }catch (ex: NumberFormatException){
                throw IOException("Cant read json value '$rawValue' as a BigDecimal.")
            }
        }
        return null
    }

    private fun getEnumFromNumber (enumClass:Class<*>, value:Number): Enum<*>? {
        val enumClz = enumClass.enumConstants.map {
            it as Enum<*>
        }
        val enumValueField = enumClass.declaredFields.firstOrNull {it.isAnnotationPresent(EnumValue::class.java) }
        if(enumValueField!=null){
            val filedName = enumValueField.name
            val getMethodName = filedName[0].toUpperCase() + filedName.substring(1)
            val getMethod = enumClass.getMethod("get${getMethodName}")
//            val data = getMethod.invoke(value)
            return enumClz.firstOrNull{
                getMethod.invoke(it).toString() == value.toString()
            }
        }

        return enumClz.firstOrNull {
            val desc = (it as? IDescribeEnum<*>)
            if(desc != null){
                it.code.toInt() == value
            }else {
                it.ordinal == value
            }
        }
    }
}