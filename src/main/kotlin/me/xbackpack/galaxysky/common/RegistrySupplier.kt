package me.xbackpack.galaxysky.common

interface RegistrySupplier<T> {
    fun get(): List<T>
}
