package com.keyboardmode

import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

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
    val window = activity.window

    when (mode) {
      "pan" -> {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
      }
      "resize" -> {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
          // nieuwe Android manier
          window.setDecorFitsSystemWindows(false)
          val rootView: View = activity.findViewById(android.R.id.content)
          ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, insets ->
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            view.setPadding(0, 0, 0, imeInsets.bottom)
            insets
          }
        }
        // fallback voor oudere Android
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
      }
    }
  }

  companion object {
    const val NAME = "KeyboardMode"
  }
}
