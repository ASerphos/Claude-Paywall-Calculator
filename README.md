# Claude Paywall Calculator

A native Android Studio project (Kotlin + Jetpack Compose) implementing a fully working calculator gated by a realistic mock paywall. Compose's declarative UI mimics the Flutter look and feel without any Flutter dependency.

## Features

- **Realistic paywall** — gradient background, feature checklist, monthly/yearly pricing tiers with a "Best Value" badge, CTA button, restore link, and legal footer.
- **Trial gate flow** — "Try for free" gives 5 calculations, after which the paywall reappears as a hard gate.
- **Mock subscription** — tapping "Start Free Trial" / "Subscribe Now" unlocks unlimited calculations and a `PRO` badge in the calculator header.
- **iOS-style calculator** — dark background, orange operator keys, grey number/function keys, large display.

## Project Structure

```
app/src/main/java/com/example/calculatorpaywall/
├── MainActivity.kt              # Entry point + navigation between screens
├── logic/CalculatorLogic.kt     # Pure arithmetic state machine
└── ui/
    ├── PaywallScreen.kt         # Paywall composable
    ├── CalculatorScreen.kt      # Calculator composable
    └── theme/                   # Colors, typography, Material3 theme
```

## Requirements

- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android SDK 34 (`compileSdk` / `targetSdk`), `minSdk 24`

## Running

1. Open the project root in Android Studio.
2. Let Gradle sync (downloads Gradle 8.6, AGP 8.2.2, Kotlin 1.9.22, Compose BOM 2024.02).
3. Run the `app` configuration on an emulator or device (API 24+).

Or from the command line:

```bash
./gradlew :app:installDebug
```

## App Flow

1. App launches → paywall screen.
2. Tap **Try for free** → calculator with `5 calculations remaining` banner.
3. After 5 `=` presses → bounced back to paywall with "Your free trial has ended".
4. Tap **Subscribe Now** → calculator with `PRO` badge, unlimited use.
