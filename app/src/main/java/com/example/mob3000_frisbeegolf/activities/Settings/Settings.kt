package com.example.mob3000_frisbeegolf.activities.Settings

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
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
import java.io.File
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
    //gallery intent code
    private val PICK_IMAGE_FROM_GALLERY = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button = view.findViewById(R.id.settings_btn)

        button.setOnClickListener {
//            val intent: Intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT

            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, PICK_IMAGE_FROM_GALLERY)


        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == PICK_IMAGE_FROM_GALLERY && resultCode == RESULT_OK && data != null && data.data != null){
            val uri: Uri = data.data as Uri
            uploadFile(uri)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun uploadFile(uri: Uri) {



        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri



        // create RequestBody instance from file
        val requestFile: RequestBody = RequestBody.create(
            MediaType.parse(context?.contentResolver?.getType(uri) as String),
            uri.toFile()
        )

        // MultipartBody.Part is used to send also the actual file name
        val body = MultipartBody.Part.createFormData("picture", uri.toFile().name, requestFile)

        // add another part within the multipart request
        val descriptionString = "hello, this is description speaking"
        val description = RequestBody.create(
            MultipartBody.FORM, descriptionString
        )

        val builder: Retrofit.Builder = Retrofit.Builder()
        builder.baseUrl("localhost:8080/api/").addConverterFactory(GsonConverterFactory.create())
        val retrofit: Retrofit = builder.build()
        // create upload service client
        val client: UploadClient = retrofit.create(UploadClient::class.java)

        // finally, execute the request
        val call: Call<ResponseBody> = client.uploadsImage(description, body)
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                Log.v("Upload", "success")
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                t.message?.let { Log.e("Upload error:", it) }
            }
        })
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