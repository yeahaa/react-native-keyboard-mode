package com.keyboardmode

import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.facebook.react.bridge.*

class KeyboardModeModule(private val reactContext: ReactApplicationContext) :
        ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String = "KeyboardMode"

  @ReactMethod
  fun setMode(mode: String) {

    val activity = currentActivity ?: return

    activity.runOnUiThread {
      val window = activity.window
      val root: View = activity.findViewById(android.R.id.content)

      if (mode == "pan") {

        val desiredMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN

        if (window.attributes.softInputMode != desiredMode) {
          window.setSoftInputMode(desiredMode)
        }

        // Insets listener resetten
        ViewCompat.setOnApplyWindowInsetsListener(root, null)
      } else {

        if (Build.VERSION.SDK_INT >= 30) {

          // Android 11+ moderne aanpak
          window.setDecorFitsSystemWindows(false)

          // Android zelf niets laten doen
          window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

          ViewCompat.setOnApplyWindowInsetsListener(root) { view, insets ->
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())

            view.setPadding(view.paddingLeft, view.paddingTop, view.paddingRight, imeInsets.bottom)

            insets
          }
        } else {

          val desiredMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE

          if (window.attributes.softInputMode != desiredMode) {
            window.setSoftInputMode(desiredMode)
          }
        }
      }
    }
  }
}
