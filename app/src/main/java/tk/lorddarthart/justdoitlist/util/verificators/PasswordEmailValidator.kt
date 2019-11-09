package tk.lorddarthart.justdoitlist.util.verificators

import android.text.TextUtils
import java.util.regex.Pattern

object PasswordEmailValidator {

    fun isValidEmailAddress(email: String): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        var result = false
        var capitalletters = 0
        if (password.length<8) { return result }
        else if (password.contains(" ")||password.contains("\n")) { return result }
        else {
            val digit = Pattern.compile("[0-9]")
            val special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]")
            for (i in password.indices) {
                if (password[i]==password[i].toUpperCase()) {
                    capitalletters++
                }
            }
            val hasCapitalLetters = capitalletters>=2
            val hasDigit = digit.matcher(password)
            val hasSpecial = special.matcher(password)
            result = hasCapitalLetters && hasDigit.find() && hasSpecial.find()
        }
        return result
    }
}