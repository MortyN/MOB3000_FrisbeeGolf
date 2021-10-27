package com.example.mob3000_frisbeegolf.api.util

class APIUtils {
    companion object{
        fun s3LinkParser(imgKey: String?): String{
            return "https://prod-disky-images.s3.eu-north-1.amazonaws.com/${imgKey}.jpeg"
        }
    }
}