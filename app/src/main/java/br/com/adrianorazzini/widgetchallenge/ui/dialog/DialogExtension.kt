package br.com.adrianorazzini.widgetchallenge.ui.dialog

import br.com.adrianorazzini.widgetchallenge.R
import br.com.adrianorazzini.widgetchallenge.ui.main.MainActivity

const val SIMPLE_ALERT_DIALOG_TAG = "SIMPLE_ALERT_DIALOG"
const val GENERIC_ERROR_DIALOG_TAG = "GENERIC_ERROR_DIALOG"
const val INVALID_CARD_ID_DIALOG_TAG = "INVALID_CARD_ID_DIALOG"
const val INVALID_ACCOUNT_ID_DIALOG_TAG = "INVALID_ACCOUNT_ID_DIALOG"

fun showAlertDialog(
    activity: MainActivity, dialogTag: String, title: String,
    message: String, positiveButton: String?, negativeButton: String?,
    cancelable: Boolean
) {
    activity.let {
        it.runOnUiThread {
            val alertDialog = DefaultAlertDialog.newInstance(
                it, title, message,
                positiveButton, negativeButton, cancelable
            )

            val transaction = it.supportFragmentManager.beginTransaction()
            val dialogFound = it.supportFragmentManager.findFragmentByTag(dialogTag)
            dialogFound?.let { currentDialog ->
                transaction.remove(currentDialog).commitNowAllowingStateLoss()
            }

            transaction.add(alertDialog, dialogTag)
            transaction.commitAllowingStateLoss()
        }
    }
}

fun MainActivity.showSimpleAlertDialog(
    title: String, message: String, positive: String? = null,
    negative: String? = null, cancelable: Boolean,
    customTag: String = SIMPLE_ALERT_DIALOG_TAG
) {
    showAlertDialog(this, customTag, title, message, positive, negative, cancelable)
}

fun MainActivity.showGenericErrorDialog() {
    showAlertDialog(
        this, GENERIC_ERROR_DIALOG_TAG,
        getString(R.string.generic_error_alert_title),
        getString(R.string.generic_error_alert_message),
        null, null, true
    )
}

fun MainActivity.showInvalidCardIdDialog() {
    showAlertDialog(
        this, INVALID_CARD_ID_DIALOG_TAG,
        getString(R.string.card_info_invalid_id_title),
        getString(R.string.card_info_invalid_id_message),
        null, null, false
    )
}

fun MainActivity.showInvalidAccountIdDialog() {
    showAlertDialog(
        this, INVALID_ACCOUNT_ID_DIALOG_TAG,
        getString(R.string.statement_info_invalid_account_id_title),
        getString(R.string.statement_info_invalid_account_id_message),
        null, null, false
    )
}