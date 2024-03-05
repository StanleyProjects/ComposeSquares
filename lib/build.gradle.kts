import com.android.build.gradle.api.BaseVariant
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import sp.gx.core.Badge
import sp.gx.core.GitHub
import sp.gx.core.Markdown
import sp.gx.core.Maven
import sp.gx.core.asFile
import sp.gx.core.assemble
import sp.gx.core.buildDir
import sp.gx.core.camelCase
import sp.gx.core.check
import sp.gx.core.colonCase
import sp.gx.core.create
import sp.gx.core.kebabCase
import sp.gx.core.resolve
import sp.gx.core.task

version = "0.0.1"

val maven = Maven.Artifact(
    group = "com.github.kepocnhh",
    id = rootProject.name,
)

val gh = GitHub.Repository(
    owner = "StanleyProjects",
    name = rootProject.name,
)

repositories {
    google()
    mavenCentral()
}

plugins {
    id("com.android.library")
    id("kotlin-android")
}

fun BaseVariant.getVersion(): String {
    check(flavorName.isEmpty())
    return when (buildType.name) {
        "debug" -> kebabCase(version.toString(), "SNAPSHOT")
        "release" -> version.toString()
        else -> error("Build type \"${buildType.name}\" is not supported!")
    }
}

fun BaseVariant.getOutputFileName(extension: String): String {
    check(extension.isNotEmpty())
    return "${kebabCase(rootProject.name, getVersion())}.$extension"
}

fun assemblePom(variant: BaseVariant) {
    tasks.create("assemble", variant.name, "Pom") {
        doLast {
            val file = buildDir()
                .dir("maven")
                .dir(variant.name)
                .file(variant.getOutputFileName("pom"))
                .assemble(
                    maven.pom(
                        version = variant.getVersion(),
                        packaging = "aar",
                    ),
                )
            println("POM: ${file.absolutePath}")
        }
    }
}

fun assembleMetadata(variant: BaseVariant) {
    tasks.create("assemble", variant.name, "Metadata") {
        doLast {
            val file = buildDir()
                .dir("yml")
                .dir(variant.name)
                .file("metadata.yml")
                .assemble(
                    """
                        repository:
                         owner: '${gh.owner}'
                         name: '${gh.name}'
                        version: '${variant.getVersion()}'
                    """.trimIndent(),
                )
            println("Metadata: ${file.absolutePath}")
        }
    }
}

fun checkReadme(variant: BaseVariant) {
    tasks.create("check", variant.name, "Readme") {
        doLast {
            TODO("Unstable!")
            val badge = Markdown.image(
                text = "version",
                url = Badge.url(
                    label = "version",
                    message = variant.getVersion(),
                    color = "2962ff",
                ),
            )
            val expected = setOf(
                badge,
                Markdown.link("Maven", Maven.Snapshot.url(maven.group, maven.id, variant.getVersion())),
                Markdown.link("Documentation", GitHub.pages(gh.owner, gh.name).resolve("doc").resolve(variant.getVersion())),
                "implementation(\"${colonCase(maven.group, maven.id, variant.getVersion())}\")",
            )
            val report = buildDir()
                .dir("reports/analysis/readme")
                .dir(variant.name)
                .asFile("index.html")
            rootDir.resolve("README.md").check(
                expected = expected,
                report = report,
            )
        }
    }
}

fun assembleSource(variant: BaseVariant) {
    task<Jar>("assemble", variant.name, "Source") {
        archiveBaseName = maven.id
        archiveVersion = variant.getVersion()
        archiveClassifier = "sources"
        val sourceSets = variant.sourceSets.flatMap { it.kotlinDirectories }.distinctBy { it.absolutePath }
        from(sourceSets)
        doLast {
            val file = archiveFile.get().asFile
            println("Archive: ${file.absolutePath}")
        }
    }
}

android {
    namespace = "sp.ax.jc.squares"
    compileSdk = Version.Android.compileSdk

    defaultConfig {
        minSdk = Version.Android.minSdk
    }

    buildTypes.getByName(testBuildType) {
        isTestCoverageEnabled = true
    }

    buildFeatures.compose = true

    composeOptions.kotlinCompilerExtensionVersion = Version.Android.compose

    libraryVariants.all {
        val variant = this
        val output = variant.outputs.single()
        check(output is com.android.build.gradle.internal.api.LibraryVariantOutputImpl)
        output.outputFileName = getOutputFileName("aar")
        checkReadme(variant)
        if (buildType.name == testBuildType) {
            // todo
        }
        assemblePom(variant)
        assembleSource(variant)
        assembleMetadata(variant)
//        assembleMavenMetadata(variant) // todo
        afterEvaluate {
            tasks.getByName<JavaCompile>(camelCase("compile", variant.name, "JavaWithJavac")) {
                targetCompatibility = Version.jvmTarget
            }
            tasks.getByName<KotlinCompile>(camelCase("compile", variant.name, "Kotlin")) {
                kotlinOptions {
                    jvmTarget = Version.jvmTarget
                    freeCompilerArgs = freeCompilerArgs + setOf("-module-name", colonCase(maven.group, maven.id))
                }
            }
        }
    }
}

dependencies {
    implementation("androidx.compose.foundation:foundation:${Version.Android.compose}")
}
