package com.example.robot

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.io.*
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.robot.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        database = FirebaseDatabase.getInstance().reference
        database.child("score").child("highScore").get().addOnSuccessListener {
            Log.i("FFirebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("FFirebase", "Error getting data", it)
        }
    }

    //override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    //menuInflater.inflate(R.menu.menu_main, menu)
    //return true
    //}

    //override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    //return when (item.itemId) {
    //R.id.action_settings -> true
    //else -> super.onOptionsItemSelected(item)
    //}
    //}

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return super.onSupportNavigateUp()
    }
    fun sendData(s:Int){
        var socket: BluetoothSocket?
        val DEVICE_ADDRESS = "98:D3:37:00:93:96"

        // UUID pour la communication série avec le HC-05
        val SERIAL_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

        val adapter = BluetoothAdapter.getDefaultAdapter()
        val device = adapter.getRemoteDevice(DEVICE_ADDRESS)


        val REQUEST_ENABLE_BT = 1
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH), REQUEST_ENABLE_BT)
        }
        socket = device.createRfcommSocketToServiceRecord(SERIAL_UUID)
        socket.connect()
        Log.d("Tag",socket?.isConnected.toString())
        //println(1"test")


        socket?.outputStream?.write(s)
        socket?.outputStream?.flush()

        val inputStream = socket?.inputStream
        val buffer = ByteArray(1024)
        val bytes = inputStream?.read(buffer)

        // Temporisation
        Thread.sleep(100)

        // Fermeture de la connexion
        socket?.close()
    }
}