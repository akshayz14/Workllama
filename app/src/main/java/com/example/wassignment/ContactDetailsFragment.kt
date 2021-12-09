package com.example.wassignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.wassignment.databinding.FragmentContactDetailsBinding
import com.example.wassignment.remote.ContactsClient
import com.example.wassignment.remote.ContactsService
import com.example.wassignment.utils.Config
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ContactDetailsFragment : Fragment() {

    private val args: ContactDetailsFragmentArgs by navArgs()
    private var _binding: FragmentContactDetailsBinding? = null
    private val binding: FragmentContactDetailsBinding
        get() = _binding!!
    private val contactsService = ContactsClient.getClient().create(ContactsService::class.java)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentContactDetailsBinding.inflate(
            inflater, container, false
        )
        renderView()
        return _binding?.root
    }

    private fun renderView() {
        val itemArgument = args.itemContent
        with(itemArgument) {
            if (itemArgument.isStarred == 1) {
                binding.ivHeartRed.visibility = View.VISIBLE
                binding.ivHeartGray.visibility = View.GONE
            }

            if (itemArgument.isStarred == 0) {
                binding.ivHeartRed.visibility = View.GONE
                binding.ivHeartGray.visibility = View.VISIBLE
            }

            binding.ivHeartGray.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    contactsService.postStar(itemArgument.id)
                }
                binding.ivHeartGray.visibility = View.GONE
                binding.ivHeartRed.visibility = View.VISIBLE
            }

            binding.ivHeartRed.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    contactsService.postUnStar(itemArgument.id)
                }
                binding.ivHeartGray.visibility = View.VISIBLE
                binding.ivHeartRed.visibility = View.GONE
            }

            binding.tvEmail.text = email
            binding.tvName.text = name
            binding.tvPhone.text = phone

            Glide.with(binding.ivProfileImage.context)
                .load(Config.BASE_URL + itemArgument.thumbnail)
                .error(R.drawable.ic_launcher_background)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivProfileImage)
        }
    }

}