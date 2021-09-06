package com.example.mobilehubwear

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import android.support.wearable.activity.WearableActivity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import com.example.mobilehubwear.api.ServiceBuilder
import com.example.mobilehubwear.userrepository.UserRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : Activity(), View.OnClickListener {

    private lateinit var etWearusername: TextView
    private lateinit var etWearPassword: TextView
    private lateinit var btnWearLogin: Button
    private lateinit var linearLayout : LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etWearusername = findViewById(R.id.etWearusername)
        etWearPassword = findViewById(R.id.etWearPassword)
        btnWearLogin = findViewById(R.id.btnWearLogin)



        btnWearLogin.setOnClickListener {
            login()
        }

    }



    private fun login() {
        ///sensors
        var vibrator: Vibrator? = null
        var notificationManagerCompat: NotificationManagerCompat? = null


        val username = etWearusername.text.toString()
        val password = etWearPassword.text.toString()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = UserRepository()
                val response = repository.checkUser(username, password)
                if (response.success == true) {


                    ServiceBuilder.token = "Bearer " + response.token
                    startActivity(
                        Intent(
                            this@MainActivity,
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
                        this@MainActivity,
                        "Login error", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onClick(v: View?) {

    }
}
