package no.usn.mob3000_disky.api

class APIUtils {
    companion object{
        fun s3LinkParser(imgKey: String?): String{
            return "https://prod-diskyapi-s3.s3.eu-north-1.amazonaws.com/${imgKey}.jpeg"
        }
    }
}