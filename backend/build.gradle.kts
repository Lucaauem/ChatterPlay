plugins {
    id("java-library")
}

dependencies {
    implementation(libs.org.restlet)

    // Restlet-Java-EE-Extension for Server- und Client-Support
    implementation(libs.org.restlet.ext.servlet)

    // JSON Extension
    implementation(libs.org.restlet.ext.jackson)

    // XML Extension
    implementation(libs.org.restlet.ext.xml)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}