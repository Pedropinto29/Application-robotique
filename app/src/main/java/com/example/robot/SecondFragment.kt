package com.example.robot

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.annotation.NonNull
import androidx.navigation.fragment.findNavController
import com.example.robot.databinding.FragmentSecondBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.core.SnapshotHolder
import java.util.*
class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    var speedStartPoint = 0
    var speedEndPoint = 0
    var rotationAngle = 0
    var elevationAngle = 0
    var speed = 0

    //Values to send the data to arduino
    val shootValue = 7
    val reloadValue = 10 //pas sur de celle ci
    val turnLeft = 6
    val turnRight = 5
    val upValue = 3
    val downValue = 4
    val upSpeed = 1
    val downSpeed = 2
    val reset = 11

    private lateinit var database: DatabaseReference

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance().reference

        database.child("score").child("highScore").get().addOnSuccessListener {
            val highScore = it.value
            Log.i("test", "$highScore")
            Log.i("FFirebase", "${it.value}")
        }.addOnFailureListener{
            Log.e("FFirebase", "Error getting data", it)
        }

        //SPEED
//        binding.speed.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
//            }
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//                if (seekBar != null) {
//                    speedStartPoint = seekBar.progress
//                }
//            }
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                if (seekBar != null) {
//                    speedEndPoint = seekBar.progress
//                    var speedChange = (speedEndPoint - speedStartPoint)/10
//                    if(speedChange > 0) {
//                        speedChange = speedChange * 10 + 1
//                    }
//                    else {
//                        speedChange = Math.abs(speedChange * 10) + 2
//                    }
//                    Log.d("SpeedStart", speedStartPoint.toString())
//                    Log.d("SpeedEnd", speedEndPoint.toString())
//                    Log.d("SpeedChange", speedChange.toString())
//                    (activity as MainActivity).sendData(speedChange)
//                }
//            }
//        })

        //SHOOT
        binding.button.setOnClickListener{
            Log.d("Shoot button", "Shooting")
            (activity as MainActivity).sendData(7)
            Thread.sleep(500)
            Log.d("Reload", "Reloading")
            (activity as MainActivity).sendData(10)
        }

        //Reload
//        binding.reload.setOnClickListener{
//            Log.d("Reload", "Reloading")
//            (activity as MainActivity).sendData(10)
//        }

        //RESET
        binding.back?.setOnClickListener{
            Log.d("BACK", "Going back and reseting all the values")
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            //(activity as MainActivity).sendData(reset)
        }

        //TURN LEFT
        binding.leftArrow.setOnClickListener{
            if (rotationAngle > -90) {
                rotationAngle -= 5
                binding.rotationText.text = "Turn $rotationAngle °"
                Log.d("Left", "Turning left 10°")
                (activity as MainActivity).sendData(turnLeft)
            }
        }

        //TURN RIGHT
        binding.rightArrow.setOnClickListener{
            if (rotationAngle < 90) {
                rotationAngle += 5
                binding.rotationText.text = "Turn $rotationAngle °"
                Log.d("Right", "Turning right 10°")
                (activity as MainActivity).sendData(turnRight)
            }
        }

        //GO UP
        binding.upArrow.setOnClickListener{
            if (elevationAngle < 180) {
                elevationAngle += 20
                Log.d("UP", "Going up 20° you are at $elevationAngle")
                (activity as MainActivity).sendData(upValue)
            }
        }

        //GO DOWN
        binding.downArrow.setOnClickListener{
            if (elevationAngle > 0) {
                elevationAngle -= 20
                Log.d("Down", "Going down 20° you are at $elevationAngle")
                (activity as MainActivity).sendData(downValue)
            }
        }

        //SPEED UP
        binding.upSpeed?.setOnClickListener{
            if (speed < 255){
                speed += 50
                Log.d("Speed Up", "Incresing speed by 50, you are at $speed")
                (activity as MainActivity).sendData(upSpeed)
            }
        }

        //SPEED DOWN
        binding.downSpeed?.setOnClickListener{
            if (speed > 0) {
                speed -= 50
                Log.d("Speed down", "Decreasing speed by 50, you are at $speed")
                (activity as MainActivity).sendData(downSpeed)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



