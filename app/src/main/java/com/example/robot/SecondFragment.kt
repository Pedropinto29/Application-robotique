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
    var rotationAngle = 0
    var elevationAngle = 0
    var speed = 0

    //Values to send the data to arduino
    val shootValue = 7
    val reloadValue = 10
    val turnLeft = 6
    val turnRight = 5
    val upValue = 3
    val downValue = 4
    val upSpeed = 1
    val downSpeed = 2
    val reset = 11

    //Initialize database values
    private var highScore : Int = 0
    private var score : Int = 0
    private var shots : Int = 10
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        //Get database reference
        database = FirebaseDatabase.getInstance().reference

        //Get High Score from database
        database.child("score").child("highScore").get().addOnSuccessListener {
            highScore = Integer.parseInt(it.value.toString())
            binding.highScore.text = "High Score : $highScore"
        }.addOnFailureListener{
            Log.e("FFirebase", "Error getting data", it)
        }


        //SHOOT
        binding.button.setOnClickListener{
            Log.d("Shoot button", "Shooting")
            (activity as MainActivity).sendData(shootValue)
            Thread.sleep(500)
            //Automatic RELOAD
            Log.d("Reload", "Reloading")
            (activity as MainActivity).sendData(reloadValue)

            //Check if we haven't done the 10 shots left
            if (shots > 1) {
                shots -= 1
                binding.shots?.text = "Shots left: $shots"

                //Get score from database
                database.child("score").child("currentScore").get().addOnSuccessListener {
                    score = Integer.parseInt(it.value.toString())
                    binding.score.text = "Score : $score"
                }.addOnFailureListener{
                    Log.e("FFirebase", "Error getting data", it)
                }

            //If 10 shots done, the game has finished.
            } else {
                shots -= 1
                binding.shots?.text = "Shots left : $shots"

                //Get score from database
                database.child("score").child("currentScore").get().addOnSuccessListener {
                    score = Integer.parseInt(it.value.toString())
                    binding.score.text = "Score : $score"

                    //Check if score is bigger than high score
                    if (score > highScore){
                        //set new highscore
                        highScore = score
                        binding.highScore.text = "High Score : $highScore"

                        //send new high score to the database
                        database.child("score").child("highScore").setValue(highScore)
                    }
                }.addOnFailureListener{
                    Log.e("FFirebase", "Error getting data", it)
                }

                //Set the score to 0 and send it to the database
                database.child("score").child("currentScore").setValue(0)
                score = 0
                shots = 10
            }
        }

        //RESET
        binding.back?.setOnClickListener{
            Log.d("BACK", "Going back and reseting all the values")
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            (activity as MainActivity).sendData(reset)
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



