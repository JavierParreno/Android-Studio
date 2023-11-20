import org.gradle.internal.impldep.org.jsoup.safety.Safelist.basic

pluginManagement {
    repositories {
        google()
        mavenCentral()
        jcenter()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()

    }
}

rootProject.name = "practica"
include(":app")
