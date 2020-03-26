package com.test.pokedex.Activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import com.koushikdutta.ion.Ion
import com.test.pokedex.Adapters.AdapterData
import com.test.pokedex.R

import kotlinx.android.synthetic.main.activity_data.*
import kotlinx.android.synthetic.main.activity_data.fab
import kotlinx.android.synthetic.main.activity_data.toolbar

class ActivityData : AppCompatActivity() {

    private var context: Context = this

    private lateinit var data:JsonArray
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter:AdapterData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        val bundle: Bundle? = intent.extras
        val id = bundle?.get("id")

        initializeComponents()
        initializeListeners()
        initializeData(id as Int)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    fun initializeComponents(){

    }

    fun initializeListeners(){

    }

    fun initializeData(id: Int){
        Ion.with(context)
            .load("https://pokeapi.co/api/v2/pokemon/" + id + "/")
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


        adapter = AdapterData()
        adapter.AdapterData(context,data)

        recycler_view_data.layoutManager = linearLayoutManager
        recycler_view_data.adapter = adapter
        recycler_view_data.setHasFixedSize(true)
        recycler_view_data.itemAnimator = DefaultItemAnimator()

    }

}