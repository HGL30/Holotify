package com.example.holotify

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils

class CloudinaryHelper {
    private val cloudinary = Cloudinary(
        ObjectUtils.asMap(
        "cloud_name", "dzdo6ax6z",
        "api_key", "816444427644827",
        "api_secret", "qfEeorgme1MT4KkVV1ADVSTVyB0"
    ))
    fun uploadMusic(filePath: String, callback: (String?) -> Unit) {
        Thread {
            try {
                val uploadResult = cloudinary.uploader().upload(filePath, ObjectUtils.emptyMap())
                val url = uploadResult["url"] as String
                callback(url)
            } catch (e: Exception) {
                e.printStackTrace()
                callback(null)
            }
        }.start()
    }
}