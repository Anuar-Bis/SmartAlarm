apply plugin: 'com.android.application'

android {
    compileSdkVersion 33
    defaultConfig {
        applicationId "com.example.tardinesstracker"
        minSdkVersion 21
        targetSdkVersion 33
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    
    // Core Android dependencies
    implementation 'androidx.appcompat:appcompat:1.5.1'
    implementation 'androidx.core:core-ktx:1.9.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'com.google.android.material:material:1.7.0'
    
    // Navigation components
    implementation 'androidx.navigation:navigation-fragment:2.5.3'
    implementation 'androidx.navigation:navigation-ui:2.5.3'
    
    // Room for database
    implementation 'androidx.room:room-runtime:2.4.3'
    annotationProcessor 'androidx.room:room-compiler:2.4.3'
    
    // ViewModel and LiveData
    implementation 'androidx.lifecycle:lifecycle-viewmodel:2.5.1'
    implementation 'androidx.lifecycle:lifecycle-livedata:2.5.1'
    
    // QR code generation and scanning
    implementation 'com.google.zxing:core:3.4.1'
    implementation 'com.journeyapps:zxing-android-embedded:4.3.0'
    
    // MPAndroidChart for data visualization
    implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'
    
    // Location services
    implementation 'com.google.android.gms:play-services-location:20.0.0'
    
    // Testing
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.4'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.0'
}
