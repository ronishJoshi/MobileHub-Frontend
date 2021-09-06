package com.example.mobilehub.ui
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mobilehub.R
import com.example.mobilehub.api.ServiceBuilder
import com.example.mobilehub.entity.User
import com.example.mobilehub.sensors.Shake
import com.example.mobilehub.userRepository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class RegisterActivity : AppCompatActivity() {
    private lateinit var etFname: EditText
    private lateinit var etLname: EditText
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnAddMobile: Button

    //sensors
    private var mShake: Shake? = null
    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        etFname = findViewById(R.id.etFname)
        etLname = findViewById(R.id.etLname)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnAddMobile = findViewById(R.id.btnAddProduct)

        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mShake = Shake {

        }

        btnAddMobile.setOnClickListener {
            val fname = etFname.text.toString()
            val lname = etLname.text.toString()
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()
            if (password != confirmPassword) {
                etPassword.error = "Password does not match"
                etPassword.requestFocus()
                return@setOnClickListener
            } else {
                val user =
                    User(fname = fname, lname = lname, username = username, password = password)
                CoroutineScope(Dispatchers.IO).launch {
                    val repository = UserRepository()
                    val response = repository.registerUser(user)
                    if(response.success == true){
                        ServiceBuilder.token = response.token
                        withContext(Main){
                            Toast.makeText(this@RegisterActivity, "Successfully Registred", Toast.LENGTH_LONG).show()
                            startActivity(
                                Intent(
                                    this@RegisterActivity,
                                    LoginActivity::class.java
                                )
                            )
                        }
                    }else{
                        withContext(Main){
                            Toast.makeText(this@RegisterActivity, "Error registering user", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            // Api code goes here
        }
    }
}