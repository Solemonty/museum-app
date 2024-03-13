package gr.novidea.museumapp.user.data

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import gr.novidea.museumapp.databinding.CustomDialogBinding

class CustomDialogFragment(
    private val cancelable: Boolean
): DialogFragment() {
    private var _binding: CustomDialogBinding? = null
    private val binding get() = _binding!!
    private var isShowing = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = CustomDialogBinding.inflate(requireActivity().layoutInflater)
        val builder = MaterialAlertDialogBuilder(this.requireActivity())
        builder.setView(binding.root)


        return builder.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val window: Window? = dialog?.window
        val wlp: WindowManager.LayoutParams? = window?.attributes
        wlp?.gravity = Gravity.CENTER
        window?.attributes = wlp
        window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        binding.completeButton.setOnClickListener {
            this.dismiss()
        }

        binding.coffeeCupsPicker.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateTotalPrice()
            }
        })

        binding.chocolateBarsPicker.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateTotalPrice()
            }
        })

        isCancelable = cancelable

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun updateTotalPrice() {
        val coffeeCups = binding.coffeeCupsPicker.text.toString().toIntOrNull() ?: 0
        val chocolateBars = binding.chocolateBarsPicker.text.toString().toIntOrNull() ?: 0
        val totalPrice = coffeeCups * 2 + chocolateBars * 5
        binding.total.text = "Your Total: ${totalPrice}$"
    }

    override fun show(manager: FragmentManager, tag: String?) {
        // check if it is already showing (to avoid crash)
        if (isShowing) {
            return
        }

        try {
            isShowing = true
            super.show(manager, tag)
        } catch (e: Exception) {
            // no-op
            isShowing = false
        }
    }

    override fun dismiss() {
        try {
            super.dismissAllowingStateLoss()
        } catch (e: Exception) {

            e.printStackTrace()
        }

        isShowing = false
    }

    override fun onDismiss(dialog: DialogInterface) {
        try {
            super.onDismiss(dialog)
        } catch (e: Exception) {

            e.printStackTrace()
        }

        isShowing = false
    }

    override fun onDestroyView() {
        isShowing = false
        _binding = null
        super.onDestroyView()
    }
}