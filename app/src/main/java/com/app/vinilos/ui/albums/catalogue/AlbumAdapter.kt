package com.app.vinilos.ui.albums.catalogue

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.vinilos.R
import com.app.vinilos.data.model.Album
import com.app.vinilos.databinding.ItemAlbumBinding

class AlbumAdapter(
    private val onClick: (Album) -> Unit
) : ListAdapter<Album, AlbumAdapter.VH>(DIFF) {

    inner class VH(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.tvTitle.text = album.name
            val year = album.releaseDate.take(4)
            val artistName = album.performers.firstOrNull()?.name ?: "Unknown"
            "$artistName, $year".also { binding.tvSubtitle.text = it }

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

            com.bumptech.glide.Glide.with(binding.imgCover)
                .load(album.cover)
                .placeholder(circularProgress)
                .error(R.drawable.placeholder_square)
                .transition(com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade())
                .into(binding.imgCover)

            binding.root.setOnClickListener { onClick(album) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemAlbumBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Album>() {
            override fun areItemsTheSame(a: Album, b: Album) = a.id == b.id
            override fun areContentsTheSame(a: Album, b: Album) = a == b
        }
    }
}