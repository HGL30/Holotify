package com.example.holotify.utils

import com.example.holotify.models.Song
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseUtils {
    fun uploadSong(song: Song, callback: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("songs").document(song.id)
            .set(song)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }
}
