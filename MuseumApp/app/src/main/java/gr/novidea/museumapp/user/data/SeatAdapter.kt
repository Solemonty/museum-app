package gr.novidea.museumapp.user.data

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import gr.novidea.museumapp.R
import gr.novidea.museumapp.databinding.LayoutUserChoicesBinding

class SeatAdapter(
    private val seats: List<Seat>,
    onClickListener: RecycleItemAdapter.OnItemClickListener,
    onRemoveListener: RecycleItemAdapter.OnItemClickListener,
) : RecyclerView.Adapter<SeatAdapter.SeatViewHolder>() {

    private val _onClickListener: RecycleItemAdapter.OnItemClickListener
    private val _onRemoveListener: RecycleItemAdapter.OnItemClickListener

    inner class SeatViewHolder(val button: ImageButton) : RecyclerView.ViewHolder(button)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatViewHolder {
        val button = ImageButton(parent.context).apply {
            layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
            scaleType = ImageView.ScaleType.FIT_CENTER
            adjustViewBounds = true
        }
        return SeatViewHolder(button)
    }

    override fun onBindViewHolder(holder: SeatViewHolder, position: Int) {
        with(holder.button) {
            setImageResource(R.drawable.seat_icon)
            backgroundTintList = if (seats[position].isAvailable) ColorStateList.valueOf(Color.GREEN) else ColorStateList.valueOf(Color.RED)
            setOnClickListener {
                if (backgroundTintList?.defaultColor == Color.MAGENTA) {
                    _onRemoveListener.onItemClick(seats[position].number, position)
                    backgroundTintList = ColorStateList.valueOf(Color.GREEN)
                } else if (seats[position].isAvailable) {
                    _onClickListener.onItemClick(seats[position].number, position)
                    seats[position].isAvailable = false
                    backgroundTintList = ColorStateList.valueOf(Color.MAGENTA)
                }
            }
        }
    }

    init {
        this._onClickListener = onClickListener
        this._onRemoveListener = onRemoveListener
    }

    override fun getItemCount() = seats.size
}