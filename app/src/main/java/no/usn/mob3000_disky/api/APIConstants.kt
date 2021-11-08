package no.usn.mob3000_disky.api

class APIConstants {
    companion object {
        const val APIVERSIONPREFIX = "/api/v1"
        const val APIPOSTPREFIX = "$APIVERSIONPREFIX/post/"
        const val APIARENAPREFIX = "$APIVERSIONPREFIX/arena/"
        const val APIUSERPREFIX = "$APIVERSIONPREFIX/user/"
        const val APIPORT = ":8080"
        const val APIHOST = "http://192.168.50.240"
    }
}