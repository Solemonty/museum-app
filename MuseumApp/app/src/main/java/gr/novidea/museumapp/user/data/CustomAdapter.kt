package gr.novidea.museumapp.user.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gr.novidea.museumapp.R
import gr.novidea.museumapp.databinding.ItemLayoutBinding

class CustomAdapter(
    onClickListener: CustomAdapter.OnItemClickListener
): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private val _onClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.choiceIcon.setImageResource(when (position) {
            0 -> R.drawable.mozart
            1 -> R.drawable.tchikovsky
            2 -> R.drawable.elvis
            else -> R.drawable.mozart // Provide a default song resource ID
        })
    }

    override fun getItemCount(): Int {
        return 3
    }

    init {
        this._onClickListener = onClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: Int)
    }

    inner class ViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.item.setOnClickListener {
                _onClickListener.onItemClick(adapterPosition, adapterPosition)
            }
        }
    }
}