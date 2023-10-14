package com.example.android.pokedex.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.pokedex.R
import com.example.android.pokedex.adapter.RecyclerViewAdapter
import com.example.android.pokedex.databinding.FragmentMainBinding
import com.example.android.pokedex.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RecyclerViewAdapter { pokemon ->
            val action = MainFragmentDirections.actionMainFragmentToPokemonDetailsFragment(pokemon)
            findNavController().navigate(action)
        }

        viewModel.pokemonList.observe(viewLifecycleOwner) { pokemons ->
            adapter.updateData(pokemons.toMutableList())
        }

        with(binding){
            pokemonRecyclerView.adapter = adapter
            pokemonRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            buttonSearch.setOnClickListener {
                if(isInternetAvailable())
                    findNavController().navigate(MainFragmentDirections.actionMainFragmentToPokemonSearchFragment())
                else
                    Snackbar.make(requireView(), "Internet not available, connect to the internet!", Snackbar.LENGTH_SHORT).show()

                }
        }
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}