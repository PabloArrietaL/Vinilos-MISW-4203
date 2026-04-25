package com.app.vinilos.ui.albums.catalogue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.vinilos.databinding.FragmentAlbumsCatalogueBinding
import com.app.vinilos.di.ServiceLocator
import com.app.vinilos.ui.common.UiState

class AlbumsCatalogueFragment : Fragment() {

    private var _binding: FragmentAlbumsCatalogueBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AlbumsCatalogueViewModel by viewModels {
        AlbumsCatalogueViewModel.Factory(ServiceLocator.albumRepository)
    }

    private lateinit var adapter: AlbumAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumsCatalogueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == com.app.vinilos.R.id.action_home) {
                findNavController().popBackStack(com.app.vinilos.R.id.homeFragment, false)
                true
            } else false
        }

        adapter = AlbumAdapter { album ->
            // TODO: navegar al detalle cuando la feature feature/albums-detail esté lista
        }

        binding.rvAlbums.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvAlbums.adapter = adapter

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.tvError.visibility = View.GONE
                    binding.rvAlbums.visibility = View.GONE
                }
                is UiState.Success -> {
                    binding.progress.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                    binding.rvAlbums.visibility = View.VISIBLE
                    adapter.submitList(state.data)
                }
                is UiState.Error -> {
                    binding.progress.visibility = View.GONE
                    binding.rvAlbums.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = state.message
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}