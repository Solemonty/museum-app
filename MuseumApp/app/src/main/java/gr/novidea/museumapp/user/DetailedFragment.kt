package gr.novidea.museumapp.user

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import gr.novidea.museumapp.R
import gr.novidea.museumapp.databinding.FragmentDetailedBinding

class DetailedFragment : Fragment() {
    private var _binding: FragmentDetailedBinding? = null
    private val binding get() = _binding!!
    private val args: DetailedFragmentArgs by navArgs()
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).isVisible = false

        when(args.artist) {
            0 -> {
                binding.artistView.setImageResource(R.drawable.mozart)
                binding.descriptionTextView.text = "The “Alla Turca” from Mozart’s Sonata KV 331 captivates with its brisk, march-like tempo and exoticism, reflecting the 18th-century European fascination with Turkish music, characterized by its rhythmic vitality and distinct melody"
            }

            1 -> {
                binding.artistView.setImageResource(R.drawable.tchikovsky)
                binding.descriptionTextView.text = "Tchaikovsky’s “Old French Song” from Opus 39, No. 16, is a delicate piece that evokes nostalgia. Its simple, plaintive melody and understated elegance capture the essence of a bygone era with grace and poignancy"
            }

            2 -> {
                binding.artistView.setImageResource(R.drawable.elvis)
                binding.descriptionTextView.text = "Elvis Presley’s “That’s All Right” is a seminal rockabilly track that marked his debut single. Recorded in 1954, it’s a spirited declaration of youthful independence and resilience, embodying the rock and roll spirit"
            }
        }

        val songResourceId = when (args.artist) {
            0 -> R.raw.mozart
            1 -> R.raw.tchaikovsky
            2 -> R.raw.elvis
            else -> R.raw.mozart
        }

        binding.playerView.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.stop()
            } else {
                mediaPlayer = MediaPlayer.create(context, songResourceId)
                mediaPlayer?.start()
            }
        }

        binding.button.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}