package com.example.android_simple_apps

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path




class CustomAdapter(private val dataSet: Array<Comments>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var element: TextView
        var name: TextView
        var id: TextView
        init {
            element = view.findViewById(R.id.rest_recycler_textfield)
            name = view.findViewById(R.id.rest_recycler_name)
            id = view.findViewById(R.id.rest_recycler_id)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.restreq_recycler_element, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        holder.element.text = dataSet[position].body
        holder.name.text = dataSet[position].name
        holder.id.text = dataSet[position].id.toString()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}





data class Comments (
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int,
)


interface MyApi {
    @GET("comments")
    fun getComments(): retrofit2.Call<List<Comments>>
    @GET("comments/{id}")
    fun getComment(@Path("id") id : Int): Call<Comments>
}

class Restreq : Fragment() {
    private var commentsArray : Array<Comments> = arrayOf<Comments>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val view = inflater.inflate(R.layout.fragment_restreq, container, false)

        val api = Retrofit.Builder() .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)
        api.getComments().enqueue(object : Callback<List<Comments>> {
            override fun onResponse( call: retrofit2.Call<List<Comments>>, response: Response<List<Comments>> ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        for (comment in it) {
                            commentsArray = commentsArray.plus(comment)
                        }
                    }
                }
            }
            override fun onFailure(call: retrofit2.Call<List<Comments>>, t: Throwable) {}
        })

        var adapter = CustomAdapter(commentsArray)
        var cycler = view.findViewById<RecyclerView>(R.id.rest_recycler_main)
        cycler.layoutManager = LinearLayoutManager(context)
        cycler.adapter = adapter


        view.findViewById<Button>(R.id.rest_search_btn).setOnClickListener {
            //view.findViewById<TextView>(R.id.what).text = field
            adapter = CustomAdapter(commentsArray)
            cycler = view.findViewById<RecyclerView>(R.id.rest_recycler_main)
            cycler.layoutManager = LinearLayoutManager(context)
            cycler.adapter = adapter
        }
        view.findViewById<Button>(R.id.btn_go_restreq_menu).setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.mainframe, Menu()).commit()
            
        }




        return view
    }
}