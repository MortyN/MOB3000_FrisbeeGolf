package com.example.mob3000_frisbeegolf.activities.Settings

import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.FileUtils
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.example.mob3000_frisbeegolf.R
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*
import java.lang.Exception
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Settings.newInstance] factory method to
 * create an instance of this fragment.
 */
class Settings : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var button: Button
    private lateinit var imageView: ImageView
    private val FILE_NAME = "photo.jpg"
    private lateinit var filePhoto: File

    //gallery intent code
    private val PICK_IMAGE_FROM_GALLERY = 1000

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button = view.findViewById(R.id.settings_btn)
        imageView = view.findViewById(R.id.imageViewSettings)
        button.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_FROM_GALLERY)

        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun savebitmap(bmp: Bitmap): File? {
        val extStorageDirectory = Environment.getExternalStorageDirectory().toString()
        val outStream: OutputStream?
        // String temp = null;
        var file = File(extStorageDirectory, "temp.png")
        if (file.exists()) {
            file.delete()
            file = File(extStorageDirectory, "temp.png")
        }
        try {
            outStream = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            outStream.flush()
            outStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return file
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_FROM_GALLERY && resultCode == RESULT_OK && data != null && data.data != null) {


//            imageView.setImageURI(data.data)

//            val bitmap = data.extras?.get("data")
//            val file: File? = savebitmap(bitmap)

            uploadFile(data.data!!)
        }

    }


    private fun uploadFile(uri: Uri) {

        val file = File(uri.path!!)

        val body = MultipartBody.Part.createFormData("photo",
            file.name, RequestBody.create(MediaType.parse("image/*"), file))

                val builder: Retrofit.Builder = Retrofit.Builder()
        builder.baseUrl("http://192.168.50.240:8080/")
            .addConverterFactory(GsonConverterFactory.create())
        val retrofit: Retrofit = builder.build()
        // create upload service client
        val client: UploadClient = retrofit.create(UploadClient::class.java)

        val call: Call<ResponseBody> = client.uploadsImage(body)

        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                print(response.code())
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                print(t.cause)
            }

        })


//        // MultipartBody.Part is used to send also the actual file name
//        val body = MultipartBody.Part.createFormData("photo",
//            file?.name, RequestBody.create(MediaType.parse("image/*"), file!!))
//
//        // add another part within the multipart request
//        val descriptionString = "hello, this is description speaking"
//        val description = RequestBody.create(MultipartBody.FORM, descriptionString)
//
//        val builder: Retrofit.Builder = Retrofit.Builder()
//        builder.baseUrl("http://192.168.50.240:8080/")
//            .addConverterFactory(GsonConverterFactory.create())
//        val retrofit: Retrofit = builder.build()
//        // create upload service client
//        val client: UploadClient = retrofit.create(UploadClient::class.java)
//
//        // finally, execute the request
//        val call: Call<ResponseBody> = client.uploadsImage(description, body)
//        call.enqueue(object : Callback<ResponseBody?> {
//            override fun onResponse(
//                call: Call<ResponseBody?>,
//                response: Response<ResponseBody?>
//            ) {
//                Log.v("Upload", "success")
//            }
//
//            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
//                t.message?.let { Log.e("Upload error:", it) }
//            }
//        })
//        val builder: Retrofit.Builder = Retrofit.Builder()
//
//        var descPart: RequestBody = RequestBody.create(MultipartBody.FORM, "NOE GREIER")
//
//        var filePart: RequestBody = RequestBody.create(
//            MediaType.parse(ContentResolver().getType(uri) as String),
//            FileUtils.copy(this, uri)
//
//        builder.baseUrl("localhost:8080/api/").addConverterFactory(GsonConverterFactory.create())
//
//        val retrofit: Retrofit = builder.build()
//
//        val client: UploadClient = retrofit.create(UploadClient::class.java)
//
//        val call: Call<ResponseBody>? = null
//
//        call?.enqueue(object : Callback<ResponseBody>{
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                Toast.makeText(context, "yeah!!",Toast.LENGTH_LONG).show()
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                Toast.makeText(context, "NO!!",Toast.LENGTH_LONG).show()
//            }
//
//        })

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

}