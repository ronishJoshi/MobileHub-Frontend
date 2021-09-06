package com.example.mobilehub.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import com.example.mobilehub.R
import com.example.mobilehub.db.ProductDB
import com.example.mobilehub.entity.Product
import com.example.mobilehub.userRepository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class AddProduct : AppCompatActivity() {
    lateinit var etProductname: EditText
    lateinit var etDescription: EditText
    lateinit var etPrice: EditText
    lateinit var rbGents: RadioButton
    lateinit var rbLadies: RadioButton
    lateinit var rbOthers: RadioButton
    lateinit var btnAdd: Button
    lateinit var imgAddImage: ImageView
    var producttype = ""
    private var REQUEST_GALLEY_CODE = 0
    private var REQUEST_CAMERA_CODE = 1
    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        etProductname = findViewById(R.id.etProductname)
        etDescription = findViewById(R.id.etDescription)
        etPrice = findViewById(R.id.etPrice)
        rbGents = findViewById(R.id.rbGents)
        imgAddImage = findViewById(R.id.imgAddImage)
        imgAddImage.setOnClickListener {
            loadPopUpMenu()
        }
        rbLadies = findViewById(R.id.rbLadies)
        rbOthers = findViewById(R.id.rbOthers)
        btnAdd = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener {
            when {
                rbGents.isChecked -> producttype = rbGents.text.toString()
                rbLadies.isChecked -> producttype = rbLadies.text.toString()
                rbOthers.isChecked -> producttype = rbOthers.text.toString()
            }

            val product =
                    Product(
                            productname = etProductname.text.toString(),
                            description = etDescription.text.toString(),
                            price = etPrice.text.toString(),
                            producttype = producttype
                    )
            CoroutineScope(Dispatchers.IO).launch {
                val repository = ProductRepository()
                val response = repository.addProduct(product)
                if (response.success == true) {

                    if (imageUrl !=null){
                        uploadImage(response.data!!._id!!)
                    }

                    withContext(Dispatchers.Main) {
                        //room database add data
                        startActivity(
                                Intent(
                                        this@AddProduct,
                                        DashboardActivity::class.java
                                )
                        )
                        ProductDB.getInstance(this@AddProduct).getProductDAO().insertProduct(product)
                        Toast.makeText(this@AddProduct, "Product Added Sucessfully!!", Toast.LENGTH_LONG).show()

                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@AddProduct, "Error adding product", Toast.LENGTH_SHORT)
                                .show()
                    }
                }
            }
        }
    }



    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLEY_CODE)
    }

    private fun openCamera(){
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }

    // Load pop up menu
    private fun loadPopUpMenu() {
        val popupMenu = PopupMenu(this@AddProduct, imgAddImage)
        popupMenu.menuInflater.inflate(R.menu.gallery_camera, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuCamera -> {
                    Toast.makeText(this, "Camera", Toast.LENGTH_SHORT).show()
                    openCamera()
                }

                R.id.menuGallery -> {
                    Toast.makeText(this, "Gallery", Toast.LENGTH_SHORT).show()
                    openGallery()
                }
            }
            true
        }
        popupMenu.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLEY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = contentResolver
                val cursor =
                        contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                imgAddImage.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            }
            else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                imgAddImage.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
            }
        }
    }

    private fun bitmapToFile(
            bitmap: Bitmap,
            fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                            .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }

    private fun uploadImage(productId: String) {
        if (imageUrl != null) {
            val file = File(imageUrl!!)
            val reqFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body =
                    MultipartBody.Part.createFormData("file", file.name, reqFile)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val productRepository = ProductRepository()
                    val response = productRepository.uploadImage(productId, body)
                    if (response.success == true) {
                        withContext(Main) {
                            Toast.makeText(this@AddProduct, "Uploaded", Toast.LENGTH_SHORT)
                                    .show()
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Main) {
                        Log.d("Error ", ex.localizedMessage)
                        Toast.makeText(
                                this@AddProduct,
                                ex.localizedMessage,
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}
