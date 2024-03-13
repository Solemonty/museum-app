package gr.novidea.museumapp.staff

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import gr.novidea.museumapp.PreferenceManager
import gr.novidea.museumapp.databinding.FragmentHallSettingsBinding
import gr.novidea.museumapp.databinding.FragmentHomeBinding
import gr.novidea.museumapp.databinding.FragmentManagmentBinding
import gr.novidea.museumapp.user.DetailedFragmentArgs
import gr.novidea.museumapp.user.MuseumFragmentDirections
import gr.novidea.museumapp.user.data.CustomAdapter

class HallSettingsFragment : Fragment() {
    private var _binding: FragmentHallSettingsBinding? = null
    private val binding get() = _binding!!
    private val args: HallSettingsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHallSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(gr.novidea.museumapp.R.id.bottom_nav_bar).isVisible = false

        binding.title.text = "Hall ${args.number + 1}"

        binding.switch1.isChecked = PreferenceManager.getLightState(requireContext(), args.number)
        binding.switch2.isChecked = PreferenceManager.getDoorState(requireContext(), args.number)

        binding.switch1.setOnClickListener {
            if (binding.switch1.isChecked) {
                binding.switch1.text = "Lights On  "
                PreferenceManager.saveLightState(requireContext(), args.number, true)
            } else {
                binding.switch1.text = "Lights Off  "
                PreferenceManager.saveLightState(requireContext(), args.number, false)
            }
        }

        binding.switch2.setOnClickListener {
            if (binding.switch2.isChecked) {
                binding.switch2.text = "Doors Open  "
                PreferenceManager.saveDoorState(requireContext(), args.number, true)
            } else {
                binding.switch2.text = "Doors Close  "
                PreferenceManager.saveDoorState(requireContext(), args.number, false)
            }
        }

        binding.colorPickerButton.setOnClickListener {
            showColorPickerDialog()
        }

        binding.button.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showColorPickerDialog() {
        val colors = intArrayOf(
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.YELLOW,
            Color.CYAN,
            Color.MAGENTA
        )

        val colorNames = arrayOf(
            "Red",
            "Green",
            "Blue",
            "Yellow",
            "Cyan",
            "Magenta"
        )

        val currentColor = PreferenceManager.getColor(requireContext(), args.number)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Pick a color")
            .setSingleChoiceItems(colorNames, colors.indexOf(currentColor)) { dialog, which ->
                val selectedColor = colors[which]
                PreferenceManager.saveColor(requireContext(), args.number, selectedColor)
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}