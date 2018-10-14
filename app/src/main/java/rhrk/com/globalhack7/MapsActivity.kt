package rhrk.com.globalhack7

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import com.google.android.gms.maps.model.CameraPosition
import android.location.Criteria
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat.getSystemService
import android.location.LocationManager
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.maps.*
import com.google.android.gms.maps.SupportMapFragment




class MapsActivity : Fragment(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mapFragment = fragmentManager?.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        return inflater.inflate(R.layout.activity_maps, null)
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        mapFragment?.getMapAsync(this)
    }

    private lateinit var mMap: GoogleMap

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
        val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()

        val location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false))
        if (location != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 11f))
        }
        mMap.isMyLocationEnabled = true
        mMap.setInfoWindowAdapter(CustomInfoWindowAdapter(context))
        mMap.setOnInfoWindowClickListener(this)
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
    }


}
