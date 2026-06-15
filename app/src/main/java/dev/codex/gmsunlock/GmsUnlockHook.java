package dev.codex.gmsunlock;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public final class GmsUnlockHook implements IXposedHookLoadPackage {
    private static final String TAG = "GMSUnlock";
    private static final String ANDROID_PACKAGE = "android";
    private static final String SYSTEM_CONFIG_CLASS = "com.android.server.SystemConfig";
    private static final String[] CN_GMS_FEATURES = {
        "cn.google.services",
        "com.google.android.feature.services_updater",
    };

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        if (!ANDROID_PACKAGE.equals(lpparam.packageName)) {
            return;
        }

        try {
            Class<?> systemConfigClass = XposedHelpers.findClass(
                SYSTEM_CONFIG_CLASS,
                lpparam.classLoader
            );

            XposedHelpers.findAndHookConstructor(systemConfigClass, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    removeCnGmsFeatures(param.thisObject, "SystemConfig constructor");
                }
            });

            hookAvailableFeatures(systemConfigClass);
            debugLog("installed for process " + lpparam.processName);
        } catch (Throwable throwable) {
            errorLog("failed to install hook", throwable);
        }
    }

    private static void hookAvailableFeatures(Class<?> systemConfigClass) {
        final XC_MethodHook.Unhook[] unhookRef = new XC_MethodHook.Unhook[1];
        unhookRef[0] = XposedHelpers.findAndHookMethod(
            systemConfigClass,
            "getAvailableFeatures",
            new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    removeCnGmsFeatures(param.thisObject, "getAvailableFeatures");
                    if (unhookRef[0] != null) {
                        unhookRef[0].unhook();
                        unhookRef[0] = null;
                        debugLog("removed one-shot getAvailableFeatures hook");
                    }
                }
            }
        );
    }

    private static void removeCnGmsFeatures(Object systemConfig, String source) {
        boolean removedAny = false;
        for (String feature : CN_GMS_FEATURES) {
            try {
                XposedHelpers.callMethod(systemConfig, "removeFeature", feature);
                removedAny = true;
            } catch (Throwable throwable) {
                errorLog("failed to remove feature " + feature, throwable);
            }
        }

        if (removedAny) {
            debugLog("removed CN GMS restriction features via " + source);
        }
    }

    private static void debugLog(String message) {
        if (BuildConfig.DEBUG) {
            XposedBridge.log(TAG + ": " + message);
        }
    }

    private static void errorLog(String message, Throwable throwable) {
        XposedBridge.log(TAG + ": " + message);
        XposedBridge.log(throwable);
    }
}
