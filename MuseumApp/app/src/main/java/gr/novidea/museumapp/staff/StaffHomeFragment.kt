package gr.novidea.museumapp.staff

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import gr.novidea.museumapp.databinding.FragmentStaffHomeBinding

class StaffHomeFragment : Fragment() {
    private var _binding: FragmentStaffHomeBinding? = null
    private val binding get() = _binding!!
    private val pinStringBuilder = StringBuilder()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStaffHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.pinEntryEditText.text.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(gr.novidea.museumapp.R.id.bottom_nav_bar).isVisible = true
        binding.wrongPassword.isVisible = false
        binding.pinEntryEditText.setMaxNumbers(4)
        pinStringBuilder.clear()

        setUpKeyboardClickListeners()
    }

    private fun setUpKeyboardClickListeners() {
        binding.key0.setOnClickListener { appendKey("0") }
        binding.key1.setOnClickListener { appendKey("1") }
        binding.key2.setOnClickListener { appendKey("2") }
        binding.key3.setOnClickListener { appendKey("3") }
        binding.key4.setOnClickListener { appendKey("4") }
        binding.key5.setOnClickListener { appendKey("5") }
        binding.key6.setOnClickListener { appendKey("6") }
        binding.key7.setOnClickListener { appendKey("7") }
        binding.key8.setOnClickListener { appendKey("8") }
        binding.key9.setOnClickListener { appendKey("9") }

        binding.keyBackspace.setOnClickListener { clearLastKey() }
        binding.keyOk.setOnClickListener { buttonOkClick() }
    }

    private fun appendKey(key: String) {
        if (binding.pinEntryEditText.text.length < 4) {
            binding.pinEntryEditText.append("*") // hide the actual pin value
            pinStringBuilder.append(key)
        }
    }

    private fun clearLastKey() {
        if (binding.pinEntryEditText.text.isNotEmpty()) {
            binding.pinEntryEditText.setText(binding.pinEntryEditText.text.dropLast(1))
            pinStringBuilder.deleteCharAt(pinStringBuilder.lastIndex)
        }
    }

    private fun buttonOkClick() {
        if (pinStringBuilder.toString() == "1111") {
            findNavController().navigate(StaffHomeFragmentDirections.actionStaffHomeFragmentToManagmentFragment())
        } else {
            binding.wrongPassword.isVisible = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}