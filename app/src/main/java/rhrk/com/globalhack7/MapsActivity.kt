package rhrk.com.globalhack7

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import com.google.android.gms.maps.model.CameraPosition
import android.location.Criteria
import android.content.Context.LOCATION_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.location.LocationManager



class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {
    private lateinit var lastLocation: Location

    override fun onMyLocationClick(p0: Location) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }

    override fun onInfoWindowClick(p0: Marker?) {
        val a = p0?.position
        val gmmUri = Uri.parse("google.navigation:q=${a?.latitude},${a?.longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val br = BufferedReader(InputStreamReader(this.resources.openRawResource(R.raw.places)))
        var tempString = br.readLine()
        var jsonString = ""
        while(tempString != null){
            jsonString += tempString
            tempString = br.readLine()
        }
        val places = JSONArray(jsonString)
        for(i in 0..(places.length()-1)){
            val tempObj = places.getJSONObject(i)
            val pos = LatLng(tempObj.getDouble("Lat"), tempObj.getDouble("Long"))
            mMap.addMarker(MarkerOptions().position(pos).title(tempObj.getString("Name")).snippet(tempObj.getString("Number")))
        }
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()

        val location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false))
        if (location != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 11f))
        }
        mMap.isMyLocationEnabled = true
        mMap.setInfoWindowAdapter(CustomInfoWindowAdapter(this))
        mMap.setOnInfoWindowClickListener(this)
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
    }
}
