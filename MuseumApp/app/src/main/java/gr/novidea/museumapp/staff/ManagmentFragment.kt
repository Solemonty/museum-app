package gr.novidea.museumapp.staff

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import gr.novidea.museumapp.databinding.FragmentHomeBinding
import gr.novidea.museumapp.databinding.FragmentManagmentBinding
import gr.novidea.museumapp.user.MuseumFragmentDirections
import gr.novidea.museumapp.user.data.CustomAdapter

class ManagmentFragment : Fragment() {
    private var _binding: FragmentManagmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManagmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(gr.novidea.museumapp.R.id.bottom_nav_bar).isVisible = false

        val adapter = gr.novidea.museumapp.staff.data.CustomAdapter(object : gr.novidea.museumapp.staff.data.CustomAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, item: Int) {
                val action = ManagmentFragmentDirections.actionManagmentFragmentToHallSettingsFragment(item)

                findNavController().navigate(action)
            }

        })
        binding.recyclerView.layoutManager = GridLayoutManager(context, 3)
        binding.recyclerView.adapter = adapter

        binding.button.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}