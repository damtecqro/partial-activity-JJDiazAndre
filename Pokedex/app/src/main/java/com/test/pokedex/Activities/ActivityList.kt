package com.test.pokedex.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import com.koushikdutta.ion.Ion
import com.test.pokedex.Adapters.AdapterList
import com.test.pokedex.Adapters.Pokemon
import com.test.pokedex.R

import kotlinx.android.synthetic.main.activity_list.*

class ActivityList : AppCompatActivity() {

    private var context: Context = this

    private lateinit var data:JsonArray
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter:AdapterList


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        initializeComponents()
        initializeListeners()
        initializeData()


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    fun initializeComponents(){

    }

    fun initializeListeners(){

    }

    fun initializeData(){
        Ion.with(context)
            .load("https://pokeapi.co/api/v2/pokemon/?offset=0&limit=964")
            .asJsonObject()
            .done { e, result ->
                if(e == null){
                    Log.i("Output", result.getAsJsonArray("results").size().toString())

                    data = result.getAsJsonArray("results")

                    initializeList()
                }
            }
    }

    fun initializeList(){
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.scrollToPosition(0)

        adapter = AdapterList (data, { pokemon: Pokemon -> pokemonClicked(pokemon)})
        adapter.AdapterList(context,data)

        recycler_view_list.layoutManager = linearLayoutManager
        recycler_view_list.adapter = AdapterList (data ,{pokemon: Pokemon -> pokemonClicked(pokemon)})
        recycler_view_list.setHasFixedSize(true)
        recycler_view_list.itemAnimator = DefaultItemAnimator()

    }

    fun pokemonClicked(pokemon: Pokemon){
        val showDetailActivityIntent = Intent(this, ActivityData::class.java)
        showDetailActivityIntent.putExtra(Intent.EXTRA_TEXT, pokemon.id.toString())
        startActivity(showDetailActivityIntent)
    }

}
