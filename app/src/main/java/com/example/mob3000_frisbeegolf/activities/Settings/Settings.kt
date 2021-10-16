package com.example.mob3000_frisbeegolf.activities.Settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.mob3000_frisbeegolf.R
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

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
            uploadChatImage("./drawable/cena.jpg")
        }

        super.onViewCreated(view, savedInstanceState)
    }


    private fun uploadChatImage(uri: String) {
        val picture = File(uri)
        val requestFile = RequestBody.create(MediaType.parse("image/*"), picture)
        val body = MultipartBody.Part.createFormData("photo", picture.name, requestFile)
        println("done?")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

}