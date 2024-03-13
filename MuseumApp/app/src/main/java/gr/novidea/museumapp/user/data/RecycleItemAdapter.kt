package gr.novidea.museumapp.user.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gr.novidea.museumapp.databinding.LayoutUserChoicesBinding

class RecycleItemAdapter (
    private val choices: List<Pair<String, Int>>,
    onClickListener: OnItemClickListener
): RecyclerView.Adapter<RecycleItemAdapter.ViewHolder>() {

    private val _onClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutUserChoicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(choices[position]) {
                binding.choiceIcon.setImageResource(second)
                binding.choiceText.text = first
            }
        }
    }

    override fun getItemCount(): Int {
        return choices.size
    }

    init {
        this._onClickListener = onClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: Int)
    }

    inner class ViewHolder(val binding: LayoutUserChoicesBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.item.setOnClickListener {
                _onClickListener.onItemClick(adapterPosition, adapterPosition)
            }
        }
    }
}