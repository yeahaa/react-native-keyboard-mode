package com.keyboardmode

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise

class KeyboardModeModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
    return NAME
  }

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  fun setMode(mode: String) {
    val activity = currentActivity ?: return

    activity.runOnUiThread {
      when (mode) {
        "pan" -> activity.window.setSoftInputMode(
          WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
        )
        "resize" -> activity.window.setSoftInputMode(
          WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )
      }
    }
  }

  companion object {
    const val NAME = "KeyboardMode"
  }
}
