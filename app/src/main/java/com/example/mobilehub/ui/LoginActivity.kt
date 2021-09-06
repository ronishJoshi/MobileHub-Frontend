package com.example.mobilehub.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mobilehub.notification.NotificationChannels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.example.mobilehub.R
import com.example.mobilehub.api.ServiceBuilder
import com.example.mobilehub.userRepository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {


    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var tvRegister: TextView
    private lateinit var btnLogin: Button
    private lateinit var linearLayout: LinearLayout
    private lateinit var chkRememberMe: CheckBox

    var sensorManager: SensorManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegister = findViewById(R.id.tvRegister)
        chkRememberMe = findViewById(R.id.chkRememberMe)
        linearLayout = findViewById(R.id.linearLayout)

        checkRunTimePermission()

        btnLogin.setOnClickListener {
            login()
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    //light sensor
    private fun sensorLight() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
        val sensorEventListener: SensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_LIGHT) {
                    Toast.makeText(this@LoginActivity,"Lights on:" + event.values[0], Toast.LENGTH_SHORT).show();
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
        }
        sensorManager!!.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }



    private fun checkRunTimePermission() {
        if (!hasPermission()) {
            requestPermission()
        }
    }

    private fun hasPermission(): Boolean {
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
                break
            }
        }
        return hasPermission
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this@LoginActivity, permissions, 1)
    }


        private fun login() {
            ///sensors
            var vibrator: Vibrator? = null
            var notificationManagerCompat: NotificationManagerCompat? = null


            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val repository = UserRepository()
                    val response = repository.checkUser(username, password)
                    if (response.success == true) {
                        //notification
                        val notificationManager = NotificationManagerCompat.from(this@LoginActivity)

                        val notificationChannels = NotificationChannels(this@LoginActivity)
                        notificationChannels.createNotificationChannels()

                        val notification = NotificationCompat.Builder(this@LoginActivity, notificationChannels.CHANNEL_1)
                                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                                .setContentTitle("Login notification")
                                .setContentText("Successfully Logged IN")
                                .setColor(Color.BLUE)
                                .build()

                        notificationManager.notify(1, notification)
                        ServiceBuilder.token = "Bearer " + response.token
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                DashboardActivity::class.java
                            )
                        )
                        finish()
                    } else {
                        withContext(Dispatchers.Main) {
                            val snack =
                                Snackbar.make(
                                    linearLayout,
                                    "Invalid credentials",
                                    Snackbar.LENGTH_LONG
                                )
                            snack.setAction("OK", View.OnClickListener {
                                snack.dismiss()
                            })
                            snack.show()
                        }
                    }

                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Login error", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
