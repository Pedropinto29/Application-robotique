package com.example.robot

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.robot.databinding.FragmentSecondBinding
import java.util.*
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    var rotationStartPoint = 0
    var rotationEndPoint = 0
    var elevationStartPoint = 0
    var elevationEndPoint = 0
    var speedStartPoint = 0
    var speedEndPoint = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        //ROTATION
        binding.rotationBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, p2: Boolean) {
                binding.rotationText.text = "Turn " + (progress - 90).toString() + " °"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    rotationStartPoint = seekBar.progress - 90
                }
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    rotationEndPoint = seekBar.progress - 90
                    var rotationAngle = (rotationEndPoint - rotationStartPoint) / 10
                    /*val rotationString = StringBuilder()
                    for (j in 1..rotationAngle){
                        rotationString.append(rotatonLetter)
                    }*/
                    if (rotationAngle >= 0) {
                        rotationAngle = rotationAngle * 10 + 5
                    } else {
                        rotationAngle = Math.abs(rotationAngle * 10) + 6
                    }
                    Log.d("rotaionAngle", rotationAngle.toString())
                    Log.d("rotationStar", rotationStartPoint.toString())
                    //(activity as MainActivity).sendData(rotationAngle)

                    //var rotationNum = 0
                    /*for (j in 1..rotationAngle){
                        rotationNum = rotationNum*10 + rotationValue
                    }*/
                    //Log.d("Endpoint", rotationNum.toString())

                }
            }
        })

        //ELEVATION BAR
        binding.elevation.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    elevationStartPoint = seekBar.progress
                }
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    elevationEndPoint = seekBar.progress
                    var elevationAngle = (elevationEndPoint - elevationStartPoint)/10
                    if (elevationAngle > 0){
                        elevationAngle = elevationAngle * 10 + 3
                    }
                    else {
                        elevationAngle = Math.abs(elevationAngle *10) + 4
                    }

                    Log.d("ElevationStart", elevationStartPoint.toString())
                    Log.d("ElevationEnd", elevationEndPoint.toString())
                    Log.d("ElevationAngle", elevationAngle.toString())
                    //(activity as MainActivity).sendData(elevationAngle)
                }
            }
        })

        //SPEED
        binding.speed.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    speedStartPoint = seekBar.progress
                }
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    speedEndPoint = seekBar.progress
                    var speedChange = (speedEndPoint - speedStartPoint)/10
                    if(speedChange > 0) {
                        speedChange = speedChange * 10 + 1
                    }
                    else {
                        speedChange = Math.abs(speedChange * 10) + 2
                    }
                    Log.d("SpeedStart", speedStartPoint.toString())
                    Log.d("SpeedEnd", speedEndPoint.toString())
                    Log.d("SpeedChange", speedChange.toString())
                    //(activity as MainActivity).sendData(speedChange)
                }
            }
        })

        //Shoot
        binding.button.setOnClickListener{
            var shootValue = 7
            //(activity as MainActivity).sendData(shootValue)
            Log.d("Shoot button", "Shooting")
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



