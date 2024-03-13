package gr.novidea.museumapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import gr.novidea.museumapp.databinding.FragmentHomeBinding
import gr.novidea.museumapp.user.data.RecycleItemAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).isVisible = true

        super.onViewCreated(view, savedInstanceState)
        val choices = listOf(
            Pair("By a ticket to a show", R.drawable.sale_icon),
            Pair("Check museums exhibitions", R.drawable.museum_icon),
            Pair("Visit recorded lectures", R.drawable.cinema_icon),
            Pair("Digital DJ", R.drawable.dj_icon)
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        val adapter = RecycleItemAdapter(
            choices,
            object : RecycleItemAdapter.OnItemClickListener {
                override fun onItemClick(position: Int, item: Int) {
                    when(item) {
                        0 -> {
                            findNavController().navigate(R.id.action_homeFragment_to_cinemaFragment)
                        }

                        1 -> {
                            findNavController().navigate(R.id.action_homeFragment_to_museumFragment)
                        }

                        2 -> {
                            findNavController().navigate(R.id.action_homeFragment_to_saleFragment)
                        }

                        3 -> {
                            findNavController().navigate(R.id.action_homeFragment_to_DJFragment)
                        }
                    }
                }
            }
        )

        binding.recyclerView.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}