package com.app.vinilos.ui.artists.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.app.vinilos.R
import com.app.vinilos.databinding.FragmentArtistDetailBinding
import com.app.vinilos.di.ServiceLocator
import com.app.vinilos.ui.common.UiState

class ArtistDetailFragment : Fragment() {

    private var _binding: FragmentArtistDetailBinding? = null
    private val binding get() = _binding!!

    private val args: ArtistDetailFragmentArgs by navArgs()

    private val viewModel: ArtistDetailViewModel by viewModels {
        ArtistDetailViewModel.Factory(ServiceLocator.artistRepository, args.artistId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtistDetailBinding.inflate(inflater, container, false)
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

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.scrollContent.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                }
                is UiState.Success -> {
                    val artist = state.data
                    binding.progress.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                    binding.scrollContent.visibility = View.VISIBLE

                    binding.toolbar.title = artist.name
                    binding.tvName.text = artist.name
                    binding.tvDescription.text = artist.description ?: ""
                    val year = artist.birthDate?.take(4) ?: ""
                    binding.tvBirthDate.text = if (year.isNotEmpty())
                        getString(R.string.artist_birth_year, year)
                    else ""

                    val circularProgress = androidx.swiperefreshlayout.widget.CircularProgressDrawable(requireContext()).apply {
                        strokeWidth = 5f
                        centerRadius = 30f
                        setColorSchemeColors(
                            androidx.core.content.ContextCompat.getColor(
                                requireContext(),
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

                    val albumNames = artist.albums.joinToString("\n") { "• ${it.name}" }
                    binding.tvAlbums.text = albumNames.ifEmpty {
                        getString(R.string.artist_no_albums)
                    }
                }
                is UiState.Error -> {
                    binding.progress.visibility = View.GONE
                    binding.scrollContent.visibility = View.GONE
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