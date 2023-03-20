package com.example.robot

import android.annotation.SuppressLint
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
    var speedStartPoint = 0
    var speedEndPoint = 0
    var rotationAngle = 0
    var elevationAngle = 0

    //Values to send the data to arduino
    val shootValue = 7
    val reloadValue = 10 //pas sur de celle ci
    val turnLeft = 6
    val turnRight = 5
    val upValue = 3
    val downValue = 4

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)

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

        //SHOOT
        binding.button.setOnClickListener{
            //(activity as MainActivity).sendData(shootValue)
            Log.d("Shoot button", "Shooting")
        }

        //Reload
        binding.reload.setOnClickListener{
            //(activity as MainActivity).sendData(reloadValue)
            Log.d("Reload", "Reloading")
        }

        //TURN LEFT
        binding.leftArrow.setOnClickListener{
            if (rotationAngle > -90) {
                rotationAngle -= 10
                binding.rotationText.text = "Turn $rotationAngle °"
                Log.d("Left", "Turning left 10°")
                //(activity as MainActivity).sendData(turnLeft)
            }
        }

        //TURN RIGHT
        binding.rightArrow.setOnClickListener{
            if (rotationAngle < 90) {
                rotationAngle += 10
                binding.rotationText.text = "Turn $rotationAngle °"
                Log.d("Right", "Turning right 10°")
                //(activity as MainActivity).sendData(turnRight)
            }
        }

        //GO UP
        binding.upArrow.setOnClickListener{
            if (elevationAngle < 180) {
                elevationAngle += 10
                Log.d("UP", "Going up 10° you are at $elevationAngle")
                //(activity as MainActivity).sendData(upValue)
            }
        }

        //GO DOWN
        binding.downArrow.setOnClickListener{
            if (elevationAngle > 0) {
                elevationAngle -= 10
                Log.d("Down", "Going down 10° you are at $elevationAngle")
                //(activity as MainActivity).sendData(downValue)
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



