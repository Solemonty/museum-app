package gr.novidea.museumapp.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import gr.novidea.museumapp.R
import gr.novidea.museumapp.databinding.FragmentMuseumBinding
import gr.novidea.museumapp.user.data.CustomAdapter

class MuseumFragment : Fragment() {
    private var _binding: FragmentMuseumBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMuseumBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).isVisible = false

        val adapter = CustomAdapter(object : CustomAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, item: Int) {
                val action = MuseumFragmentDirections.actionMuseumFragmentToDetailedFragment(item)

                findNavController().navigate(action)
            }

        })
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
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