package br.com.adrianorazzini.widgetchallenge.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class DefaultAlertDialog : DialogFragment() {

    private lateinit var mInteractionListener: InteractionDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is InteractionDialogListener) {
            mInteractionListener = context
        } else {
            throw ClassCastException("$context must implement InteractionDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments?.getString(TITLE_KEY)
        val message = arguments?.getString(MESSAGE_KEY)
        val positiveButtonText = arguments?.getString(
            POSITIVE_BUTTON_KEY,
            getString(android.R.string.ok)
        )
        val negativeButtonText = arguments?.getString(NEGATIVE_BUTTON_KEY, null)
        val isCancelable = arguments?.getBoolean(CANCELABLE_KEY, true) ?: true

        activity?.let { activity ->
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP
                    && !event.isCanceled && isCancelable
                ) {
                    dialog.cancel()
                }

                return@OnKeyListener true
            })
            builder.setPositiveButton(positiveButtonText) { _, _ ->
                mInteractionListener.onPositiveClicked(this@DefaultAlertDialog)
            }

            if (!negativeButtonText.isNullOrEmpty()) {
                builder.setNegativeButton(negativeButtonText) { _, _ ->
                    mInteractionListener.onNegativeClicked(this@DefaultAlertDialog)
                }
            }

            val alertDialog = builder.create()
            alertDialog.setCancelable(isCancelable)
            alertDialog.setCanceledOnTouchOutside(isCancelable)

            return alertDialog
        }

        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)

        mInteractionListener.onDialogCancelled(this@DefaultAlertDialog)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        mInteractionListener.onDialogDismissed(this@DefaultAlertDialog)
    }

    interface InteractionDialogListener {
        fun onPositiveClicked(dialog: DefaultAlertDialog)
        fun onNegativeClicked(dialog: DefaultAlertDialog)
        fun onDialogCancelled(dialog: DefaultAlertDialog)
        fun onDialogDismissed(dialog: DefaultAlertDialog)
    }

    companion object {
        val TITLE_KEY = "$DefaultAlertDialog.TITLE.KEY"
        val MESSAGE_KEY = "$DefaultAlertDialog.MESSAGE.KEY"
        val POSITIVE_BUTTON_KEY = "$DefaultAlertDialog.POSITIVE.BUTTON.KEY"
        val NEGATIVE_BUTTON_KEY = "$DefaultAlertDialog.NEGATIVE.BUTTON.KEY"
        val CANCELABLE_KEY = "$DefaultAlertDialog.CANCELABLE.KEY"

        fun newInstance(
            context: Context, title: String, message: String, positiveButton: String?,
            negativeButton: String?, cancelable: Boolean
        ): DefaultAlertDialog {
            val dialog = DefaultAlertDialog()

            val args = Bundle()
            args.putString(TITLE_KEY, title)
            args.putString(MESSAGE_KEY, message)
            args.putBoolean(CANCELABLE_KEY, cancelable)
            args.putString(
                POSITIVE_BUTTON_KEY, positiveButton
                    ?: context.getString(android.R.string.ok)
            )
            args.putString(NEGATIVE_BUTTON_KEY, negativeButton ?: "")

            dialog.arguments = args

            return dialog
        }
    }
}