package com.tang.jkorm.proxy

import com.tang.jkorm.session.SqlSession
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * @author Tang
 */
class MapperProxy<T>(

    private val sqlSession: SqlSession,

    private val mapperInterface: Class<T>

) : InvocationHandler {

    private val defaultImplsSuffix = "\$DefaultImpls"

    private val methodHandleMap: ConcurrentMap<Method, MethodHandle> = ConcurrentHashMap()

    private val allowedModes = MethodHandles.Lookup.PRIVATE or MethodHandles.Lookup.PROTECTED or MethodHandles.Lookup.PACKAGE or MethodHandles.Lookup.PUBLIC

    private val lookupConstructor: Constructor<MethodHandles.Lookup>? = run {
        if (privateLookupInMethod != null) {
            return@run null
        }
        return@run runCatching {
            val lookup = MethodHandles.Lookup::class.java.getDeclaredConstructor(Class::class.java, Int::class.java)
            lookup.isAccessible = true
            lookup
        }.getOrNull()
    }

    private val privateLookupInMethod: Method? = run {
        runCatching {
            MethodHandles::class.java.getMethod("privateLookupIn", Class::class.java, MethodHandles.Lookup::class.java)
        }.getOrNull()
    }

    override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any? {
        if (Any::class.java == method.declaringClass) {
            return method.invoke(this, *(args ?: arrayOf()))
        }
        if (isJavaDefaultMethod(method)) {
            return javaInvoker(proxy, method, args)
        }
        if (isKotlinDefaultMethod(method)) {
            return kotlinInvoker(proxy, method, args)
        }
        if (sqlSession.isBaseMethod(method)) {
            return baseMethodInvoker(method, args)
        }
        return method.invoke(this, *(args ?: arrayOf()))
    }

    private fun lookup(callerClass: Class<*>): MethodHandles.Lookup {
        return if (lookupConstructor != null) {
            lookupConstructor.newInstance(callerClass, allowedModes)
        } else {
            privateLookupInMethod!!.invoke(null, callerClass, MethodHandles.lookup()) as MethodHandles.Lookup
        }
    }

    private fun getSpecialMethodHandle(parentMethod: Method): MethodHandle {
        val declaringClass = parentMethod.declaringClass
        val lookup: MethodHandles.Lookup = lookup(declaringClass)
        return lookup.unreflectSpecial(parentMethod, declaringClass)
    }

    private fun isJavaDefaultMethod(method: Method): Boolean {
        return method.isDefault
    }

    private fun javaInvoker(proxy: Any, method: Method, args: Array<out Any>?): Any {
        val defaultMethodHandle = methodHandleMap.computeIfAbsent(method) {
            val methodHandle = getSpecialMethodHandle(method)
            methodHandle.bindTo(proxy)
        }
        return defaultMethodHandle.invokeWithArguments(*(args ?: arrayOf()))
    }

    private fun isKotlinDefaultMethod(method: Method): Boolean {
        return runCatching {
            val impl = Class.forName("${method.declaringClass.name}$defaultImplsSuffix")
            return impl.methods.any { isSameMethod(it, method) }
        }.getOrDefault(false)
    }

    private fun isSameMethod(methodImpl: Method, methodSource: Method): Boolean {
        return methodImpl.name == methodSource.name
            && methodImpl.returnType == methodSource.returnType
            && methodImpl.parameterTypes.contentEquals(arrayOf(methodSource.declaringClass, *methodSource.parameterTypes))
    }

    private fun kotlinInvoker(proxy: Any, method: Method, args: Array<out Any>?): Any {
        val impl = Class.forName("${method.declaringClass.name}$defaultImplsSuffix")
        val defaultMethod = impl.methods.first { isSameMethod(it, method) }
        return defaultMethod.invoke(null, proxy, *(args ?: arrayOf()))
    }

    private fun baseMethodInvoker(method: Method, args: Array<out Any>?): Any? {
        return sqlSession.execute(method, args, mapperInterface)
    }

}
