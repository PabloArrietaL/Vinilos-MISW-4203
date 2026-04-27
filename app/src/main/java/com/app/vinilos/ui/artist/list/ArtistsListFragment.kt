package com.app.vinilos.ui.artists.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.vinilos.R
import com.app.vinilos.databinding.FragmentArtistsListBinding
import com.app.vinilos.di.ServiceLocator
import com.app.vinilos.ui.common.UiState

class ArtistsListFragment : Fragment() {

    private var _binding: FragmentArtistsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ArtistsListViewModel by viewModels {
        ArtistsListViewModel.Factory(ServiceLocator.artistRepository)
    }

    private lateinit var adapter: ArtistAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtistsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.action_home) {
                findNavController().popBackStack(R.id.homeFragment, false)
                true
            } else false
        }

        adapter = ArtistAdapter { artist ->
            val action = ArtistsListFragmentDirections.actionArtistsListToArtistDetail(artist.id)
            findNavController().navigate(action)
        }

        binding.rvArtists.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvArtists.adapter = adapter

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.tvError.visibility = View.GONE
                    binding.rvArtists.visibility = View.GONE
                }
                is UiState.Success -> {
                    binding.progress.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                    binding.rvArtists.visibility = View.VISIBLE
                    adapter.submitList(state.data)
                }
                is UiState.Error -> {
                    binding.progress.visibility = View.GONE
                    binding.rvArtists.visibility = View.GONE
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