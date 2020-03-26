package com.test.pokedex.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.koushikdutta.ion.Ion
import com.test.pokedex.R

class AdapterData:RecyclerView.Adapter<AdapterData.ViewHolder>() {

    private lateinit var data:JsonArray
    private lateinit var context: Context

    fun AdapterData(context:Context,data:JsonArray){
        this.context = context
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterData.ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_list,parent,false))
    }

    override fun getItemCount(): Int {
        return data.size()
    }

    override fun onBindViewHolder(holder: AdapterData.ViewHolder, position: Int) {
        var item:JsonObject = data.get(position).asJsonObject

        holder.bind(item,context)
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        private var imagePokemon: ImageView = view.findViewById(R.id.pokemon_image)
        private var namePokemon:TextView  = view.findViewById(R.id.pokemon_name)
        private var numberPokemon:TextView = view.findViewById(R.id.pokemon_number)
        private var typesPokemon:TextView = view.findViewById(R.id.pokemon_types)
        private var statsPokemon:TextView = view.findViewById(R.id.pokemon_stats)
        private var movesPokemon:TextView = view.findViewById(R.id.pokemon_moves)

        fun bind(item:JsonObject,context:Context){
            namePokemon.setText(item.get("name").asString)
            numberPokemon.setText(item.get("number").asString)
            typesPokemon.setText(item.get("types").asString)
            statsPokemon.setText(item.get("stats").asString)
            movesPokemon.setText(item.get("moves").asString)

            Ion.with(context)
                .load(item.get("url").asString)
                .asJsonObject()
                .done { e, result ->
                    if(e == null){
                        if(!result.get("sprites").isJsonNull){
                            if(result.get("sprites").asJsonObject.get("front_default") != null){
                                //Pintar
                                Glide
                                    .with(context)
                                    .load(result.get("sprites").asJsonObject.get("front_default").asString)
                                    .placeholder(R.drawable.pokemon_logo_min)
                                    .error(R.drawable.pokemon_logo_min)
                                    .into(imagePokemon);
                            }else{
                                imagePokemon.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.pokemon_logo_min))
                            }
                        }else{
                            imagePokemon.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.pokemon_logo_min))
                        }
                    }
                }
        }

    }
}