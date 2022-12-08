package sk.stu.fei.mobv2022.services

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and

class PasswordHash {

    companion object {

        fun getPassword(passwordToHash: String): String {

            val salt: String = "ypiOk!1sDxm"
            var generatedPassword: String
            try {
                val md: MessageDigest = MessageDigest.getInstance("SHA-512")
                md.update(salt.toByteArray())
                val bytes: ByteArray = md.digest(passwordToHash.toByteArray())
                val sb = StringBuilder()
                for (i in bytes.indices) {
                    sb.append(
                        Integer.toString((bytes[i] and 0xff.toByte()) + 0x100, 16)
                            .substring(1)
                    )
                }
                generatedPassword = sb.toString()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
                generatedPassword = passwordToHash
            }
            return generatedPassword
        }
    }
}