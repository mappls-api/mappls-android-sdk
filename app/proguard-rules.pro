# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# progressDialogHide the original source file name.
#-renamesourcefileattribute SourceFile
# Retrofit 2
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes *Annotation*,Signature
# Retain declared checked exceptions for use by a Proxy instance.
 -keepattributes Exceptions
# For using GSON @Expose annotation
-keepattributes *Annotation*
# Gson specific classes
-dontwarn sun.misc.**
-dontwarn okio.**
-dontwarn okhttp3.**
-keep class retrofit.**
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
 @retrofit.http.* <methods>;
}

-keep class com.mmi.services.account.** {
    <fields>;
    <methods>;
}
-keep class com.mmi.services.api.** {
    <fields>;
    <methods>;
}
-keep class com.mmi.services.utils.** {
    <fields>;
    <methods>;
}
-keep class com.mapbox.mapboxsdk.maps.session.model.SessionRequestModel {
    <fields>;
    <methods>;
}
-keep class com.mapbox.mapboxsdk.maps.session.model.SessionResponse {
    <fields>;
    <methods>;
}