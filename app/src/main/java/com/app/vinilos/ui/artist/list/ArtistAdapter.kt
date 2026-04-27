package com.app.vinilos.ui.artists.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.vinilos.R
import com.app.vinilos.data.model.Artist
import com.app.vinilos.databinding.ItemArtistBinding

class ArtistAdapter(
    private val onClick: (Artist) -> Unit
) : ListAdapter<Artist, ArtistAdapter.VH>(DIFF) {

    inner class VH(val binding: ItemArtistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Artist) {
            binding.tvName.text = artist.name
            val year = artist.birthDate?.take(4) ?: ""
            binding.tvBirthDate.text = if (year.isNotEmpty()) "b. $year" else ""

            val circularProgress = androidx.swiperefreshlayout.widget.CircularProgressDrawable(binding.root.context).apply {
                strokeWidth = 5f
                centerRadius = 30f
                setColorSchemeColors(
                    androidx.core.content.ContextCompat.getColor(
                        binding.root.context,
                        com.google.android.material.R.color.material_dynamic_primary40
                    )
                )
                start()
            }

            com.bumptech.glide.Glide.with(binding.imgArtist)
                .load(artist.image)
                .placeholder(circularProgress)
                .error(R.drawable.placeholder_circle)
                .transition(com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade())
                .into(binding.imgArtist)

            binding.root.setOnClickListener { onClick(artist) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemArtistBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Artist>() {
            override fun areItemsTheSame(a: Artist, b: Artist) = a.id == b.id
            override fun areContentsTheSame(a: Artist, b: Artist) = a == b
        }
    }
}