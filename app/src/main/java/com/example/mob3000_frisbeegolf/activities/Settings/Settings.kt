package com.example.mob3000_frisbeegolf.activities.Settings

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.mob3000_frisbeegolf.R
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*
import java.util.*
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mob3000_frisbeegolf.api.constants.ApiConstants
import com.example.mob3000_frisbeegolf.api.endpoints.APIUserInterface
import com.example.mob3000_frisbeegolf.api.model.User
import com.google.gson.Gson
import okhttp3.MultipartBody


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Settings : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var button: Button
    private lateinit var imageView: ImageView
    private val FILE_NAME = "photo.jpg"
    private lateinit var filePhoto: File

    //gallery request code
    private val PICK_IMAGE_FROM_GALLERY = 1000

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button = view.findViewById(R.id.settings_btn)
        imageView = view.findViewById(R.id.imageViewSettings)

        button.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                showImageChooser(this@Settings)
            } else {
                /*Requests permissions to be granted to this application. These permissions
                 must be requested in your manifest, they should not be granted to your app,
                 and they should have protection level*/
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    3
                )
            }

        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun showImageChooser(activity: Fragment?) {
        // An intent for launching the image selection of phone storage.
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        // Launches the image selection of phone storage using the constant code.
        activity?.startActivityForResult(galleryIntent, PICK_IMAGE_FROM_GALLERY)
    }

    private fun sendRequest(user: User, imageFile: File) {

        val json: String = Gson().toJson(user)

        val imagebody = RequestBody.create(MediaType.parse("image/jpeg"), imageFile)

        val finImage = MultipartBody.Part.createFormData("image", (0..10000).random().toString(), imagebody)

        val builder: Retrofit.Builder = Retrofit.Builder()
        builder.baseUrl(ApiConstants.APIHOST + ApiConstants.APIPORT + ApiConstants.APIUSERPREFIX)
            .addConverterFactory(GsonConverterFactory.create())

        val retrofit: Retrofit = builder.build()
        val feed: APIUserInterface = retrofit.create(APIUserInterface::class.java)
        val call: Call<User> = feed.createUser(user,finImage)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val res = response.body()
                print(res)
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@Settings.context, "Failed", Toast.LENGTH_LONG).show()
            }


        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE_FROM_GALLERY && resultCode == RESULT_OK && data != null && data.data != null) {


            try {
                val mSelectedImageUri = data.data!!

                Log.d("MainActivity", mSelectedImageUri.toString())

                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

                val cursor = this@Settings.activity?.contentResolver?.query(
                    mSelectedImageUri,
                    filePathColumn, null, null, null
                )

                cursor!!.moveToFirst()

                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                val picturePath = cursor.getString(columnIndex)
                cursor.close()

                val imageFile = File(picturePath)

                println(imageFile.extension)

                sendRequest(User(110), imageFile)

            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this@Settings.context, "Could not select image", Toast.LENGTH_LONG)
                    .show()
            }

        }
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

}