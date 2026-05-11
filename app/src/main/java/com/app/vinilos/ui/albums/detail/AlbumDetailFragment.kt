package com.app.vinilos.ui.albums.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.vinilos.data.model.Album
import com.app.vinilos.databinding.FragmentAlbumDetailBinding
import com.app.vinilos.di.ServiceLocator
import com.app.vinilos.ui.common.UiState
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class AlbumDetailFragment : Fragment() {

    private var _binding: FragmentAlbumDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AlbumDetailViewModel by viewModels {
        AlbumDetailViewModel.Factory(ServiceLocator.albumRepository, args.albumId)
    }
    private val args: AlbumDetailFragmentArgs by navArgs()
    private val trackAdapter = TrackAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecyclerView()
        observeUiState()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        binding.rvTracks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = trackAdapter
        }
    }

    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> showAlbum(state.data)
                is UiState.Error   -> showError(state.message)
            }
        }
    }

    private fun showLoading() {
        binding.progress.isVisible = true
        binding.scrollContent.isVisible = false
        binding.tvError.isVisible = false
    }

    private fun showError(message: String) {
        binding.progress.isVisible = false
        binding.scrollContent.isVisible = false
        binding.tvError.isVisible = true
        binding.tvError.text = message
    }

    private fun showAlbum(album: Album) {
        binding.progress.isVisible = false
        binding.tvError.isVisible = false
        binding.scrollContent.isVisible = true

        with(binding) {
            toolbar.title = album.name

            Glide.with(requireContext())
                .load(album.cover)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivCover)

            tvName.text = album.name
            tvPerformers.text = album.performers
                .joinToString(", ") { it.name }
            tvReleaseDate.text = album.releaseDate
                .take(4)
            tvGenre.text = album.genre ?: "—"
            tvRecordLabel.text = album.recordLabel ?: "—"
            tvDescription.text = album.description ?: "No description available."

            trackAdapter.submitList(album.tracks)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}