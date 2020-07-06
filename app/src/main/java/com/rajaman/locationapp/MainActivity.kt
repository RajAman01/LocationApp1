package com.rajaman.locationapp

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.main_locbtn)
        val long = findViewById<TextView>(R.id.longitude)
        val lati = findViewById<TextView>(R.id.latitude)

        val f1 = LocationServices.getFusedLocationProviderClient(this)

        btn.setOnClickListener {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                   ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), 505)
                }else {
                    f1.lastLocation.addOnSuccessListener { location: Location? ->
                        if (location == null) {
                            Toast.makeText(this, "Location Not Found", Toast.LENGTH_LONG).show()
                        } else {
                            val l: Double = location.longitude
                            val la: Double = location.latitude
                            long.text = l.toString()
                            lati.text = la.toString()
                            val intent = Intent(this,DetailsAct::class.java)
                            intent.putExtra("Longitude",l)
                            intent.putExtra("Latitude",la)
                            startActivity(intent)
                        }
                    }
                }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.isNotEmpty() && requestCode == 505 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show()
        }
    }
}