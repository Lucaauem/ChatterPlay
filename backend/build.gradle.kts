plugins {
    id("java-library")
}

dependencies {
    implementation("org.restlet.jee:org.restlet:2.3.12")
    implementation("org.restlet.jee:org.restlet.ext.servlet:2.3.12")
    implementation("org.restlet.jee:org.restlet.ext.jackson:2.3.12")
    implementation("org.restlet.jee:org.restlet.ext.xml:2.3.12")
    implementation("org.json:json:20090211")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}