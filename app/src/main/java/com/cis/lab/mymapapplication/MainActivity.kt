package com.cis.lab.mymapapplication

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*

//import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    lateinit var locationClient : FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationClient = LocationServices.getFusedLocationProviderClient(this)
        //locationClient = locationService
        getLocation()

        button.setOnClickListener(){
            val i = Intent(this,MapsActivity::class.java)
            startActivity(i)
        }
    }

    private fun getLocation(){
        //check location
        if (checkPermission()){
            locationClient.lastLocation.addOnCompleteListener(this){ task ->
                var location: Location? = task.result
                if(location != null){
                    var lat = location.latitude.toString()
                    var lon = location.longitude.toString()
                    //Log.i("Location",lat +","+lon)
                    textView.text=lat.toString()+" : " +lon.toString()

                }else{
                    // requestNewLocationData()
                    Log.i("Location","null location")
                }
            }

        }else{
            requestPermissions()
        }

    }

    private fun checkPermission():Boolean{
        if(ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )==PackageManager.PERMISSION_GRANTED&&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )== PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    private fun requestPermissions(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION),
            101
        )
    }
}
