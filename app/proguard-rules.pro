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
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class com.google.android.** { *; }
-keep class com.google.firebase.** { *; }
-keep class com.google.ads.** { *;}
-keep class android.support.v7.widget.SearchView { *; }
-dontwarn com.google.android.**
-dontnote android.net.http.**
-dontnote org.apache.http.**
-dontnote com.google.android.**
-dontnote com.google.android.gms.**
-dontwarn com.squareup.okhttp.**
-keepattributes *Annotation*
-keepclassmembers class com.google.**.R$* {
    public static <fields>;
}
-keep public class com.google.ads.** {*;}
-keep public class com.google.android.gms.** {*;}
-keep public class com.tappx.** { *; }
