package com.example.android.pokedex.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.pokedex.R
import com.example.android.pokedex.databinding.FragmentPokemonSearchBinding
import com.example.android.pokedex.model.Pokemon
import com.example.android.pokedex.sendNotification
import com.example.android.pokedex.viewmodel.PokemonSearchViewModel
import jp.wasabeef.glide.transformations.ColorFilterTransformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Suppress("DEPRECATION")
class PokemonSearchFragment : Fragment() {

    private lateinit var pokemonSearchViewModel: PokemonSearchViewModel
    private lateinit var binding: FragmentPokemonSearchBinding
    private var currentPokemon: Pokemon? = null
    private val KEY_CURRENT_POKEMON = "current_pokemon"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pokemon_search, container, false)
        pokemonSearchViewModel = ViewModelProvider(this)[PokemonSearchViewModel::class.java]

        pokemonSearchViewModel.viewModelScope.launch {
            pokemonSearchViewModel.fetchPokemon()
            pokemonSearchViewModel.pokemon.observe(viewLifecycleOwner) { pokemon ->
                pokemon?.let {
                    setupView(pokemon)
                }
            }
        }

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.mainFragment)
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        if (savedInstanceState != null) {
            currentPokemon = savedInstanceState.getParcelable(KEY_CURRENT_POKEMON)
            setupView(currentPokemon)
        }

        return binding.root
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_CURRENT_POKEMON, currentPokemon)
    }

    private fun setupView(pokemon: Pokemon?) {
        with(binding) {
            val frontDefault: String =
                pokemonSearchViewModel.pokemon.value?.frontDefault ?: ""
            loadPokemonImageWithBlack(imageView, frontDefault)

            val rightAnswer = pokemonSearchViewModel.pokemon.value!!.name
            val wrongAnswer1 = pokemonSearchViewModel.getRandomPokemonName()
            val wrongAnswer2 = pokemonSearchViewModel.getRandomPokemonName()
            val wrongAnswer3 = pokemonSearchViewModel.getRandomPokemonName()

            val options =
                listOf(rightAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3).shuffled()
            optionButton1.text = options[0]
            optionButton2.text = options[1]
            optionButton3.text = options[2]
            optionButton4.text = options[3]

            optionButton1.setOnClickListener {
                checkAnswer(
                    optionButton1,
                    captureButton,
                    imageView,
                    rightAnswer,
                    frontDefault
                )
                disableButtons(optionButton2, optionButton3, optionButton4)
            }

            optionButton2.setOnClickListener {
                checkAnswer(
                    optionButton2,
                    captureButton,
                    imageView,
                    rightAnswer,
                    frontDefault
                )
                disableButtons(optionButton1, optionButton3, optionButton4)
            }

            optionButton3.setOnClickListener {
                checkAnswer(
                    optionButton3,
                    captureButton,
                    imageView,
                    rightAnswer,
                    frontDefault
                )
                disableButtons(optionButton1, optionButton2, optionButton4)
            }

            optionButton4.setOnClickListener {
                checkAnswer(
                    optionButton4,
                    captureButton,
                    imageView,
                    rightAnswer,
                    frontDefault
                )
                disableButtons(optionButton1, optionButton2, optionButton3)
            }

            captureButton.setOnClickListener {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        val result = pokemonSearchViewModel.savePokemon(pokemon)
                        if (result) {
                            Log.i("repository", "Pokemon ${pokemon?.name} saved successfully!")
                            if (pokemonSearchViewModel.getPokedexSize() > 0) {
                                sendNotification(requireContext(), pokemon!!)
                            }
                            navigateBack()
                        } else {
                            Log.i("repository", "Fail to save Pokemon!")
                        }
                    }
                }
            }
        }
    }

    private fun checkAnswer(
        button: Button,
        saveButton: Button,
        image: ImageView,
        correctAnswer: String,
        imageUrl: String
    ) {
        if (button.text.equals(correctAnswer)) {
            loadPokemonImage(image, imageUrl)
            saveButton.isEnabled = true
            saveButton.text = resources.getString(R.string.capture_button)
            button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_400))
        } else {
            button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
            saveButton.text = getString(R.string.try_again)
            saveButton.isEnabled = true
            saveButton.setOnClickListener {
                findNavController().navigate(R.id.pokemonSearchFragment)
            }
        }
    }

    private fun navigateBack() {
        lifecycleScope.launch(Dispatchers.Main) {
            findNavController().navigate(R.id.mainFragment)
        }
    }

    private fun disableButtons(button1: Button, button2: Button, button3: Button) {
        button1.isEnabled = false
        button2.isEnabled = false
        button3.isEnabled = false
    }

    private fun loadPokemonImageWithBlack(image: ImageView, url: String) {
        Glide.with(requireContext())
            .load(url)
            .fitCenter()
            .apply(
                RequestOptions()
                    .transform(ColorFilterTransformation(Color.BLACK))
            )
            .into(image)
    }

    private fun loadPokemonImage(image: ImageView, url: String) {
        Glide.with(requireContext())
            .load(url)
            .fitCenter()
            .into(image)
    }
}