import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.keelim.core.R

/** Color */

fun Context.getColorCompat(@ColorRes colorResId: Int) =
    ContextCompat.getColor(this, colorResId)

fun Context.getColorStateListCompat(@ColorRes colorResId: Int) =
    ContextCompat.getColorStateList(this, colorResId)

/** Toast */

fun Context.showToast(msg: CharSequence) =
    Toast.makeText(this, msg, LENGTH_SHORT).show()

fun Context.showToast(@StringRes msgId: Int) =
    Toast.makeText(this, msgId, LENGTH_SHORT).show()

/** Activity */

fun Context.startActivitySafely(intent: Intent) {
    if (intent.isValid(this)) {
        startActivity(intent)
    } else {
        showToast("실행할 앱을 찾을 수 없습니다.")
    }
}

private fun Intent.isValid(ctx: Context): Boolean {
    return resolveActivity(ctx.packageManager) != null
}

fun Context.executeWeb(url: String?) {
    if (url == null) return
    startNonBrowserActivity(url, fallback = { startBrowserActivity(url) })
}

private fun Context.startBrowserActivity(url: String) {
    CustomTabsIntent.Builder()
        .setShareState(CustomTabsIntent.SHARE_STATE_ON)
        .setColorScheme(CustomTabsIntent.COLOR_SCHEME_SYSTEM)
        .setShowTitle(true)
        .setStartAnimations(this, R.anim.fade_in, R.anim.fade_out)
        .setExitAnimations(this, R.anim.fade_in, R.anim.fade_out)
        .build()
        .launchUrl(this, Uri.parse(url))
}

private fun Context.startNonBrowserActivity(url: String, fallback: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addCategory(Intent.CATEGORY_BROWSABLE)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER
            }
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            fallback()
        }
    } else {
        fallback()
    }
}