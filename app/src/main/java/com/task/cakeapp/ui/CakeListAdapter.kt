package com.task.cakeapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.task.cakeapp.databinding.CakeListItemBinding
import android.view.animation.AlphaAnimation
import com.task.cakeapp.data.CakeModel

//Cake list adapter for CakeList recyclerview
class CakeListAdapter : ListAdapter<CakeModel, RecyclerView.ViewHolder>(CakeDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val plant = getItem(position)
        (holder as PlantViewHolder).bind(plant,cakeClickListener!!)// setting data and listeners to binding class
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlantViewHolder(
            CakeListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))// Initializing Data binding class
    }

    var cakeClickListener : OnCakeClickListener? = null

    fun setClickListener(listener : OnCakeClickListener){
        cakeClickListener= listener

    }

    class PlantViewHolder(
        private val binding: CakeListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CakeModel, listener : OnCakeClickListener) {
            binding.apply {
                cake = item
                executePendingBindings()
            }

            setFadeAnimation(binding.root)// To set fade animation

            //Setting Listner to entire view
            binding.root.setOnClickListener(View.OnClickListener {
                listener.onCakeClick(item)
            })

        }
        fun setFadeAnimation(view: View) {
            val anim = AlphaAnimation(0.0f, 1.0f)
            anim.duration = 300
            view.startAnimation(anim)
        }
    }


}

//Difference callback for CakeList adapter yo check that data has changed or not
private class CakeDiffCallback : DiffUtil.ItemCallback<CakeModel>() {

    override fun areItemsTheSame(oldItem: CakeModel, newItem: CakeModel): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: CakeModel, newItem: CakeModel): Boolean {
        return oldItem == newItem
    }
}

//Listener for Recyclerview
interface OnCakeClickListener{
    fun onCakeClick(cake : CakeModel)
}