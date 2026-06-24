# GMS Unlock LSPosed

Standalone LSPosed/Xposed module extracted from the REAREye GMS unlock hook.

This module removes two CN Google Play Services restriction feature flags from
`com.android.server.SystemConfig`:

- `cn.google.services`
- `com.google.android.feature.services_updater`

It is intentionally always-on after LSPosed activation. There is no extra
settings switch.

## Upstream Sync

Version `1.0.1` follows the upstream REAREye fix discussed in
[killerprojecte/REAREye#3](https://github.com/killerprojecte/REAREye/issues/3):
features are removed after `SystemConfig` construction and again before the
first `getAvailableFeatures()` read, then that one-shot hook is removed.

## Project Note

This standalone module was assembled with the help of OpenAI Codex at the
user's request. Thanks to `killerprojecte/REAREye` for the original GMS unlock
implementation this module was extracted from.

This project is not affiliated with or endorsed by REAREye.

## Build

Requirements:

- JDK 17
- Android SDK Platform 35
- Android Gradle Plugin dependencies from Google Maven and Maven Central

Build a debug APK:

```powershell
gradle :app:assembleDebug
```

Build a release APK:

```powershell
gradle :app:assembleRelease
```

The APKs will be generated under:

```text
app/build/outputs/apk/debug/
app/build/outputs/apk/release/
```

GitHub Actions builds both debug and release APKs on every push, pull request,
and manual `workflow_dispatch` run. Download the APKs from these artifacts:

- `gms-unlock-debug-apk`
- `gms-unlock-release-apk`

Release builds are signed. If no signing secrets are configured, CI falls back
to Android's debug signing config so the release APK is still installable but
not stable for future updates. For stable release updates, add these repository
secrets and rerun the workflow:

- `RELEASE_KEYSTORE_BASE64`
- `RELEASE_KEYSTORE_PASSWORD`
- `RELEASE_KEY_ALIAS`
- `RELEASE_KEY_PASSWORD`

## Install

1. Install the APK.
2. Enable `GMS Unlock` in LSPosed.
3. Keep the `android` scope selected.
4. Reboot the device.

If REAREye is also installed, do not enable REAREye's identical GMS unlock
option at the same time unless you are intentionally testing duplicate hooks.

## Source And License

The hook was adapted from:

https://github.com/killerprojecte/REAREye

Original file:

```text
app/src/main/java/hk/uwu/reareye/hook/scopes/system/modules/misc/GMSUnlockModule.kt
```

REAREye is licensed under GPL-3.0, so this extracted module is distributed
under GPL-3.0 as well. See `LICENSE` and `NOTICE`.
