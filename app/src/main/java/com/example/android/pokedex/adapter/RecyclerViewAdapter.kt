package com.example.android.pokedex.adapter

import com.example.android.pokedex.R
import com.example.android.pokedex.model.Pokemon
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private val callback: ((item: Pokemon) -> Unit)? = null) :
    RecyclerView.Adapter<DataBindingViewHolder<Pokemon>>() {

    private var _items: MutableList<Pokemon> = mutableListOf()

    override fun getItemCount() = _items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<Pokemon> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil
            .inflate<ViewDataBinding>(layoutInflater, getLayoutRes(), parent, false)
        binding.lifecycleOwner = getLifecycleOwner()
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<Pokemon>, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            callback?.invoke(item)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(items: List<Pokemon>) {
        _items.clear()
        _items.addAll(items)
        notifyDataSetChanged()
    }

    @LayoutRes
    fun getLayoutRes(): Int {
        return R.layout.item_pokemon
    }

    private fun getLifecycleOwner(): LifecycleOwner? {
        return null
    }

    private fun getItem(position: Int) = _items[position]
}
