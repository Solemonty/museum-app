package gr.novidea.museumapp.user

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import gr.novidea.museumapp.PreferenceManager
import gr.novidea.museumapp.databinding.FragmentCinemaBinding
import gr.novidea.museumapp.user.data.RecycleItemAdapter
import gr.novidea.museumapp.user.data.Seat
import gr.novidea.museumapp.user.data.SeatAdapter


class CinemaFragment : Fragment() {
    private var _binding: FragmentCinemaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCinemaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<BottomNavigationView>(gr.novidea.museumapp.R.id.bottom_nav_bar).isVisible = false

        val unavailableSeats = PreferenceManager.getUnavailableSeats(requireContext())

        val selectedSeats = mutableListOf<Int>()
        val seats = List(40) { Seat(it + 1, it !in unavailableSeats) }
        val adapter = SeatAdapter(seats,
            object : RecycleItemAdapter.OnItemClickListener {
                override fun onItemClick(position: Int, item: Int) {
                    selectedSeats.add(item)

                    binding.payButton.isVisible = true
                    binding.payButton.text = "Pay ${selectedSeats.size * 10}$"
                }

            },
            object : RecycleItemAdapter.OnItemClickListener {
                override fun onItemClick(position: Int, item: Int) {
                    selectedSeats.remove(element = item)

                    if (selectedSeats.isEmpty()) {
                        binding.payButton.isVisible = false
                    } else {
                        binding.payButton.text = "Pay ${selectedSeats.size * 10}$"
                    }
                }

            })

        binding.payButton.setOnClickListener {
            selectedSeats.addAll(unavailableSeats)
            PreferenceManager.saveUnavailableSeats(requireContext(), selectedSeats)
            findNavController().navigateUp()
        }

        binding.recyclerView.layoutManager = GridLayoutManager(context, 8)
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