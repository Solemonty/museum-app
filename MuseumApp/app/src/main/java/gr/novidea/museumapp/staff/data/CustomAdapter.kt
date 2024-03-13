package gr.novidea.museumapp.staff.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gr.novidea.museumapp.R
import gr.novidea.museumapp.databinding.HallLayoutBinding
import gr.novidea.museumapp.databinding.ItemLayoutBinding

class CustomAdapter(
    onClickListener: CustomAdapter.OnItemClickListener
): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private val _onClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HallLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("Hall ${position + 1}")
        holder.binding.textBox.text = "Hall ${position + 1}"
    }

    override fun getItemCount(): Int {
        return 15
    }

    init {
        this._onClickListener = onClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: Int)
    }

    inner class ViewHolder(val binding: HallLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.item.setOnClickListener {
                _onClickListener.onItemClick(adapterPosition, adapterPosition)
            }
        }
    }
}