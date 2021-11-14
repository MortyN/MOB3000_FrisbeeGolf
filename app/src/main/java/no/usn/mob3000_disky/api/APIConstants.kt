package no.usn.mob3000_disky.api

import android.provider.Settings.Global.getString
import no.usn.mob3000_disky.BuildConfig
import no.usn.mob3000_disky.R

class APIConstants {
    companion object {
        const val APIVERSIONPREFIX = "/api/v1"
        const val APIPOSTPREFIX = "$APIVERSIONPREFIX/post/"
        const val APIARENAPREFIX = "$APIVERSIONPREFIX/arena/"
        const val APIUSERPREFIX = "$APIVERSIONPREFIX/user/"
        const val APIUSERLINKPREFIX = "$APIVERSIONPREFIX/userLink/"
        const val APIPORT = ":8080"
        val APIHOST = "http://${BuildConfig.API_IP_ADDRESS}"
    }
}