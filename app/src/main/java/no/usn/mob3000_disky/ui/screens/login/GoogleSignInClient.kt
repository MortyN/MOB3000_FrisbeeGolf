package no.usn.mob3000_disky.ui.screens.login

import android.content.Context
import android.provider.Settings.Global.getString
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import no.usn.mob3000_disky.R

class GoogleSignInClient {
    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("331251032035-6dffqhl846rpejle2a5lfduv2dps6gsb.apps.googleusercontent.com")
            .requestEmail()
            .requestProfile()
            .build()

        return GoogleSignIn.getClient(context, signInOptions)
    }
}