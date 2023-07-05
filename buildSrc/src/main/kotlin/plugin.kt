import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

fun PluginDependenciesSpec.pophory(pluginName: String): PluginDependencySpec {
    return id("com.teampophory.pophory.$pluginName")
}
