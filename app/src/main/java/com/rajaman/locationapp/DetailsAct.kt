package com.rajaman.locationapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_details.*
import org.json.JSONObject
import java.lang.reflect.Method

class DetailsAct : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val txt = findViewById<TextView>(R.id.details_txt)
        val temp = findViewById<TextView>(R.id.details_temp)
        val pres = findViewById<TextView>(R.id.details_press)
        val hum = findViewById<TextView>(R.id.details_hum)

        val s = intent.extras
        val long = s?.getDouble("Longitude")
        val lang = s?.getDouble("Latitude")

        val url =
            "https://api.openweathermap.org/data/2.5/weather?lat=$lang&lon=$long&appid=206004f784e605d4b88f4442a9132405"
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest: JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val main: JSONObject = response.getJSONObject("main")
                val weather:JSONObject= response.getJSONObject("wind")
                temp.text = main.get("temp").toString() + "K^"
                pres.text = main.get("pressure").toString()+ "Psc"
                hum.text = main.get("humidity").toString()
                txt.text = weather.get("speed").toString()+ "KM/Hr"
            },
            Response.ErrorListener {
                txt.text = long.toString()
                temp.text = lang.toString()
            })
        queue.add(jsonObjectRequest)
    }
}