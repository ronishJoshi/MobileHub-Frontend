package com.example.mobilehub.ui

import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mobilehub.R
import com.example.mobilehub.api.ServiceBuilder
import com.example.mobilehub.notification.NotificationChannels


class DashboardActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var btnAddproduct : ImageView
    private lateinit var btnDisplayproduct : ImageView
    private lateinit var btnLogout : ImageView
    private lateinit var leaveanim : ImageView
    private lateinit var sensorManager: SensorManager
    private var proximitysensor : Sensor? = null
    private var lightsensor : Sensor? = null
    private var accelerometersensor : Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        Toast.makeText(this, ServiceBuilder.token.toString(), Toast.LENGTH_SHORT).show()
        btnAddproduct = findViewById(R.id.btnAddproduct)
        btnDisplayproduct = findViewById(R.id.btnDisplayproduct)
        btnLogout = findViewById(R.id.btnLogout)
        leaveanim = findViewById(R.id.leaveanim)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if(!checkSensor())
            return
        else{
            lightsensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            proximitysensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
            accelerometersensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            sensorManager.registerListener(this, proximitysensor, SensorManager.SENSOR_DELAY_NORMAL)
            sensorManager.registerListener(this, lightsensor, SensorManager.SENSOR_DELAY_NORMAL)
            sensorManager.registerListener(this, accelerometersensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        btnAddproduct.setOnClickListener{
            startActivity(Intent(this, AddProduct::class.java))
                    leaveanim.setOnClickListener{
                startActivity(Intent(this, MapsActivity::class.java))
            }

        }
        btnDisplayproduct.setOnClickListener{
            startActivity(Intent(this, DisplayProductActivity::class.java))
        }

        btnLogout.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
}
    private fun checkSensor(): Boolean {
        var flag =true
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) == null){
            flag = false
        }
        return flag
    }
    override fun onSensorChanged(event: SensorEvent?) {

        if(event!!.sensor.type == Sensor.TYPE_PROXIMITY)
        {
            val values = event.values[0]
            if(values < 4)
            {
                val notificationManager = NotificationManagerCompat.from(this)
                val notificationChannels = NotificationChannels(this)
                notificationChannels.createNotificationChannels()
                val notification = NotificationCompat.Builder(this, notificationChannels.CHANNEL_1)
                        .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                        .setContentTitle("Alert notification")
                        .setContentText("Proximity Sensor Alert")
                        .setColor(Color.BLUE)
                        .build()
                notificationManager.notify(1, notification)
            }
        }
        if(event.sensor.type == Sensor.TYPE_LIGHT)
        {
            val values = event.values[0]
            if(values > 20000)
            {
                val notificationManager = NotificationManagerCompat.from(this)
                val notificationChannels = NotificationChannels(this)
                notificationChannels.createNotificationChannels()
                val notification = NotificationCompat.Builder(this, notificationChannels.CHANNEL_1)
                        .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                        .setContentTitle("Light notification")
                        .setContentText("Lower Your Brightness")
                        .setColor(Color.BLUE)
                        .build()
                notificationManager.notify(1, notification)
            }
        }
        if(event.sensor.type == Sensor.TYPE_ACCELEROMETER){
            val values = event.values
            val xAxis = values[0]
            if(xAxis<(-4)) {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}