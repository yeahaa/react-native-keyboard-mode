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

        // --- Pan mode ---
        val desiredMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN

        if (window.attributes.softInputMode != desiredMode) {
          window.setSoftInputMode(desiredMode)
        }

        // Insets listener verwijderen
        ViewCompat.setOnApplyWindowInsetsListener(root, null)

        // Reset padding: behoud alleen top/bottom van system bars, wordt dubbel geteld??
       /* val systemBars =
                ViewCompat.getRootWindowInsets(root)
                        ?.getInsets(WindowInsetsCompat.Type.systemBars())
        val topPadding = systemBars?.top ?: 0
        val bottomPadding = systemBars?.bottom ?: 0
*/
        root.setPadding(0, 0, 0, 0)

        // force apply insets
        ViewCompat.requestApplyInsets(root)

      } else {

        // --- Resize mode ---
        if (Build.VERSION.SDK_INT >= 30) {

          // Android ≥ 11: nothing + insets
          window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

          ViewCompat.setOnApplyWindowInsetsListener(root) { view, insets ->
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
           // val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            //val bottom = maxOf(imeInsets.bottom, systemBars.bottom)

            view.setPadding(
                    view.paddingLeft,
                    imeInsets.top, // topbar
                    view.paddingRight,
                    imeInsets.bottom // keyboard of bottom bar
            )

            insets
          }

          ViewCompat.requestApplyInsets(root)
        } else {

          // Android < 11
          val desiredMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE

          if (window.attributes.softInputMode != desiredMode) {
            window.setSoftInputMode(desiredMode)
          }
        }
      }
    }
  }
}
