package com.pnj.vaksin_firebase.chat

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pnj.vaksin_firebase.R
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class ChatAdapter(private val chatList: ArrayList<Chat>):
    RecyclerView.Adapter<ChatAdapter.ChatViewHoler>(){

    class ChatViewHoler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var username: TextView = itemView.findViewById(R.id.TVLChatUsername)
        var time_chat: TextView = itemView.findViewById(R.id.TVLChatTime)
        var chat_content: TextView = itemView.findViewById(R.id.TVLChatContent)
        var img_user: ImageView = itemView.findViewById(R.id.IMGUserChat)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHoler {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_list_layout, parent,false)
        return ChatViewHoler(itemView)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: ChatViewHoler, position: Int) {
        val chat: Chat = chatList[position]
        holder.username.text = chat.username
        holder.time_chat.text = chat.time
        holder.chat_content.text = chat.message

        val storageRef = FirebaseStorage.getInstance().reference.child("img_pasien/${chat.username}.jpg")
        if (storageRef != null) {
            val localfile = File.createTempFile("tempImage", "jpg")
            storageRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                holder.img_user.setImageBitmap(bitmap)
            }.addOnFailureListener{
                Log.e("foto ?", "gagal")
            }
        }
    }
}
