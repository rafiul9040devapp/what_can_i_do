package com.rafiul.whatcanido.utils

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import com.rafiul.whatcanido.R


fun View.showSnackBar(
    lifecycleOwner: LifecycleOwner,
    snackBarMessage: LiveData<Int>,
    timeLength: Int = Snackbar.LENGTH_SHORT,
    actionText: String = context.getString(R.string.ok),
    action: (() -> Unit)? = null,
    backgroundColor: Int = Color.GRAY,
    actionTextColor: Int = Color.RED,
    messageTextColor: Int = Color.WHITE
) {
    snackBarMessage.observe(lifecycleOwner) { message ->
        val snackBar = Snackbar.make(this, message, timeLength).apply {
            setBackgroundTint(backgroundColor)
            setActionTextColor(actionTextColor)

            setAction(actionText) {
                action?.invoke() ?: dismiss()
            }

            view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                .setTextColor(messageTextColor)

        }
        snackBar.show()
    }
}


@SuppressLint("SetTextI18n")
fun TextView.showNumberOfCharacters(
    lifecycleOwner: LifecycleOwner,
    characterMessage: LiveData<String>
) {
    characterMessage.observe(lifecycleOwner) { message ->
        if (message.isNullOrEmpty()) {
            inVisible()
        } else {
            visible()
            val characterText =
                if (message.length == 1) context.getString(R.string.character)
                else context.getString(R.string.characters)

            text = "${message.length} $characterText"
        }
    }
}

fun String?.toTrimString(): String {
    return this.toString().trim()
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.inVisible() {
    this.visibility = View.INVISIBLE
}

@BindingAdapter("isVisible")
fun View.setIsVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("isSaving", "buttonText")
fun showTextWhileSaving(button: Button, isSaving: Boolean, buttonText: String) {
    button.text = if (isSaving) "" else buttonText
}
