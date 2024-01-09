package com.example.android_simple_apps

import android.os.Bundle
import android.telecom.Call
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class Comments (
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int,
)


interface MyApi {
    @GET("comments")
    fun getComments(): Call<List<Comments>>
    @GET("comments/{id}")
    fun getComment(@Path("id") id : Int): Call<Comments>
}

class Restreq : Fragment() {
    private var field : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_restreq, container, false)
        view.findViewById<Button>(R.id.rest_search_btn).setOnClickListener {
            view.findViewById<TextView>(R.id.rest_search_field).text = "what"
        }
        return view
    }


    private fun getAllComments() {
        val api = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)

        api.getComments().enqueue(object : Callback<List<Comments>> {
            override fun onResponse(
                call: retrofit2.Call<List<Comments>>,
                response: Response<List<Comments>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        for (comment in it) {
                            Log.i("ZywOo", "onResponse ${comment.body}")
                            field += comment.body
                        }
                    }
                }
            }
            override fun onFailure(call: retrofit2.Call<List<Comments>>, t: Throwable) {
                //Log.i("ZywOo", "OnFailure: ${t.message}")
            }
        })

    }
}