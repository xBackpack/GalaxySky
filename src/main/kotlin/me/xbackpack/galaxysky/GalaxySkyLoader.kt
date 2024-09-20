package me.xbackpack.galaxysky

import io.papermc.paper.plugin.loader.PluginClasspathBuilder
import io.papermc.paper.plugin.loader.PluginLoader
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver
import org.eclipse.aether.artifact.DefaultArtifact
import org.eclipse.aether.graph.Dependency

class GalaxySkyLoader : PluginLoader {
    override fun classloader(classpath: PluginClasspathBuilder) {
        classpath.addLibrary(
            MavenLibraryResolver().apply {
                addDependency(Dependency(DefaultArtifact("org.jetbrains.kotlin", "kotlin-stdlib", "jar", "2.0.20"), "provided"))
            },
        )
    }
}
