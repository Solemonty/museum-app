package gr.novidea.museumapp.user

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import gr.novidea.museumapp.R
import gr.novidea.museumapp.databinding.FragmentDJBinding

class DJFragment : Fragment() {
    private var _binding: FragmentDJBinding? = null
    private val binding get() = _binding!!
    private var mediaPlayer: MediaPlayer? = null
    private val songs = listOf(R.raw.elvis, R.raw.mozart, R.raw.tchaikovsky)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDJBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).isVisible = false

        binding.button.setOnClickListener {
            findNavController().navigateUp()
        }

        var numberCount = 1

        mediaPlayer = MediaPlayer.create(context, songs[songs.size % numberCount])
        setPlaying(songs.size % numberCount)
        binding.playButton.setOnClickListener {
            mediaPlayer?.start()
        }

        binding.pauseButton.setOnClickListener {
            mediaPlayer?.stop()
        }

        binding.skipButton.setOnClickListener {
            numberCount++
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(context, songs[songs.size % numberCount])
            setPlaying(songs.size % numberCount)
            mediaPlayer?.start()
        }

        binding.tempoSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Adjust playback speed based on seek bar progress
                mediaPlayer?.playbackParams = mediaPlayer?.playbackParams!!.setSpeed(progress / 100f)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun setPlaying(order: Int) {
        when(songs[order]) {
            R.raw.elvis -> {
                binding.songInfoTextView.text = "Now Playing: “That’s All Right” : Elvis Presley"
            }

            R.raw.tchaikovsky -> {
                binding.songInfoTextView.text = "Now Playing: The “Old French Song : Tchaikovsky”"
            }

            R.raw.mozart -> {
                binding.songInfoTextView.text = "Now Playing: The “Alla Turca” : Mozart"
            }
        }
    }

    override fun onDestroyView() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        super.onDestroyView()
        _binding = null
    }
}