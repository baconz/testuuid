plugins {
    kotlin("multiplatform")
}

val applePresets = setOf(
    "iosArm64",
    "iosX64",
    "iosSimulatorArm64",
    "tvosArm64",
    "tvosX64",
    "tvosSimulatorArm64",
)

kotlin {
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        compilations.get("main").kotlinOptions.freeCompilerArgs += "-Xexport-kdoc"
    }

    js(IR) {
        moduleName = "@philo/client-common"
        useCommonJs()
        browser {
            webpackTask {
                sourceMaps = true
            }
        }
        generateTypeScriptDefinitions()
        binaries.library()
        binaries.executable()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
        tvosX64(),
        tvosArm64(),
        tvosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val appleMain = sourceSets.create("appleMain")
        applePresets.forEach { presetName ->
            targetFromPreset(
                presets.getByName(presetName),
                presetName,
            )

            sourceSets.getByName("${presetName}Main").dependsOn(appleMain)
        }
        appleMain.dependsOn(sourceSets.getByName("commonMain"))

        val commonMain by getting {
            dependencies {
                api(deps.apollo.api)

                api(deps.uuid)
            }
        }
        val jsMain by getting {
        }
    }
}
