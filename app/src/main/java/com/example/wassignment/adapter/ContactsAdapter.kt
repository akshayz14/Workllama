package com.example.wassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.wassignment.ContactListFragmentDirections
import com.example.wassignment.R
import com.example.wassignment.models.Content
import com.example.wassignment.remote.ContactsClient
import com.example.wassignment.remote.ContactsService
import com.example.wassignment.utils.Config
import com.example.wassignment.utils.DiffUtilCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ContactsAdapter() :
    PagingDataAdapter<Content, ContactsAdapter.ContactsViewHolder>(DiffUtilCallback()) {


    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        getItem(position)?.let { holder.bindPost(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return ContactsViewHolder(view)
    }


    class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
        private val ivSmallImage: ImageView = itemView.findViewById(R.id.ivSmallImage)
        private val ivFavouriteGray: ImageView = itemView.findViewById(R.id.ivFavouriteGray)
        private val ivFavouriteRed: ImageView = itemView.findViewById(R.id.ivFavouriteRed)
        private val contactsService = ContactsClient.getClient().create(ContactsService::class.java)

        fun bindPost(content: Content) {

            with(content) {
                tvName.text = name
                tvPhone.text = phone

                if (content.isStarred == 1) {
                    ivFavouriteGray.visibility = View.GONE
                    ivFavouriteRed.visibility = View.VISIBLE
                }

                if (content.isStarred == 0) {
                    ivFavouriteGray.visibility = View.VISIBLE
                    ivFavouriteRed.visibility = View.GONE
                }

                ivFavouriteGray.setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        contactsService.postStar(content.id)
                    }
                    content.isStarred = 1
                    ivFavouriteGray.visibility = View.GONE
                    ivFavouriteRed.visibility = View.VISIBLE
                }

                ivFavouriteRed.setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        contactsService.postUnStar(content.id)
                    }
                    content.isStarred = 0
                    ivFavouriteGray.visibility = View.VISIBLE
                    ivFavouriteRed.visibility = View.GONE
                }

                Glide.with(ivSmallImage.context)
                    .load(Config.BASE_URL + content.thumbnail)
                    .error(R.drawable.ic_error)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivSmallImage)

                itemView.setOnClickListener {
                    val action =
                        ContactListFragmentDirections.actionContactListFragmentToContactDetailsFragment(
                            content
                        )
                    Navigation.findNavController(itemView).navigate(action);
                }
            }
        }
    }


}