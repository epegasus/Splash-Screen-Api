# Android SplashScreen API with Kotlin

## Introduction
In Android 11 or lower, we developed custom splash screens. Now, with Android 12 and higher, migrate your app to the SplashScreen API to ensure it displays correctly. The SplashScreen API in Android 12 allows apps to launch with an animated icon and a branding image at the bottom.

## How the Splash Screen Works
When a user launches an app while the app's process isn't running (a cold start) or the Activity isn't created (a warm start), the following events occur:

1. The system shows the splash screen using themes and any animations that you define.
2. When the app is ready, the splash screen is dismissed, and the app displays.


https://github.com/user-attachments/assets/8853d2dd-70c7-4aa4-8023-517b9512920f


https://github.com/user-attachments/assets/89fbad64-75e4-459d-bce2-fbd438240fe1




## Mechanism
1. The app icon must be a vector drawable. It can be static or animated. Google recommends not to exceed 1000 ms for animated ones.
2. The icon background is optional.
3. The window background consists of a single opaque color.

## Splash Screen Dimensions
The splash screen icon uses the same specifications as adaptive icons, as follows:

1. **Branded image:** This must be 200×80 dp.
2. **App icon with an icon background:** This must be 240×240 dp and fit within a circle 160 dp in diameter.
3. **App icon without an icon background:** This must be 288×288 dp and fit within a circle 192 dp in diameter.

Example: If the full size of the image is 300 dp x 300 dp, the icon needs to fit within a circle with a diameter of 200 dp. Everything outside the circle will be invisible (masked).

![Export Splash Logos](https://github.com/user-attachments/assets/937175d3-59fd-4a4e-9762-68ed2178261d)


## Splash Screen Animation Requirements
1. **Format:** The icon must be an AnimatedVectorDrawable (AVD) XML.
2. **Duration:** We recommend not exceeding 1,000 ms on phones. You can use a delayed start, but this can't be longer than 166 ms. If the app startup time is longer than 1,000 ms, consider a looping animation.

## Steps to Implement

### Step 1: Add Dependency
Add the following dependency in your `build.gradle` file:
```groovy
    implementation "androidx.core:core-splashscreen:1.0.0"
```

### Step 2: Create a New Theme
Create a new theme in resources as res > values > splash, also its night variant. Define a new Theme (e.g., Theme.App.Starting) with its parent set to Theme.SplashScreen or Theme.SplashScreen.IconBackground.

### Step 3: Update Manifest
In your manifest, set the theme attribute of the whole <application> or just the starting <activity> to Theme.App.Starting.

### Step 4: Update Activity
In the `onCreate` method of the starting activity, call `installSplashScreen` just before `super.onCreate()`. Also, make sure that `postSplashScreenTheme` is set to the application's theme.

### Theme without Icon Background
```
<style name="Theme.App.Starting" parent="Theme.SplashScreen">
    <item name="windowSplashScreenBackground">#F8F9FA</item>
    <item name="windowSplashScreenAnimatedIcon">@drawable/splash_logo_without_background</item>
    <item name="windowSplashScreenAnimationDuration">300</item>
    <item name="android:windowSplashScreenBrandingImage" tools:targetApi="s">@drawable/splash_branding</item>
    <item name="postSplashScreenTheme">@style/Theme.SplashScreenApi</item>
</style>
```
### Theme with Icon Background
```
<style name="Theme.App.Starting.Background" parent="Theme.SplashScreen.IconBackground">
    <item name="windowSplashScreenBackground">#F8F9FA</item>
    <item name="windowSplashScreenAnimatedIcon">@drawable/splash_logo_with_background</item>
    <item name="windowSplashScreenAnimationDuration">300</item>
    <item name="windowSplashScreenIconBackgroundColor">#DCDCDC</item>
    <item name="android:windowSplashScreenBrandingImage" tools:targetApi="s">@drawable/splash_branding</item>
    <item name="postSplashScreenTheme">@style/Theme.SplashScreenApi</item>
</style>
```
## Known Incompatibilities (Limitations)
On API < 31, windowSplashScreenAnimatedIcon cannot be animated. If you want to provide an animated icon for API 31+ and a still icon for API < 31, you can do so by overriding the still icon with an animated vector drawable in res/drawable-v31.

On API < 31, if the value of windowSplashScreenAnimatedIcon is an adaptive icon, it will be cropped and scaled. The workaround is to respectively assign windowSplashScreenAnimatedIcon and windowSplashScreenIconBackgroundColor to the values of the adaptive icon foreground and background.

