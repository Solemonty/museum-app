package gr.novidea.museumapp.user

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import gr.novidea.museumapp.R
import gr.novidea.museumapp.databinding.FragmentHomeBinding
import gr.novidea.museumapp.databinding.FragmentSaleBinding
import gr.novidea.museumapp.user.data.CustomDialogFragment

class SaleFragment : Fragment() {
    private var _binding: FragmentSaleBinding? = null
    private val binding get() = _binding!!
    private var dialog: CustomDialogFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSaleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).isVisible = false

        val videoUri = Uri.parse("android.resource://" + requireContext().packageName + "/" + R.raw.video)
        binding.videoView.setVideoURI(videoUri)

        binding.videoView.start()

        binding.button.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.orderButton.setOnClickListener {
            dialog?.dismiss()
            dialog = CustomDialogFragment(false)
            dialog?.show(requireActivity().supportFragmentManager, "CustomDialog")
        }
    }

    override fun onDestroyView() {
        binding.videoView.stopPlayback()
        super.onDestroyView()
        _binding = null
    }
}