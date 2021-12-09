package com.example.wassignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.wassignment.adapter.ContactsAdapter
import com.example.wassignment.adapter.ContactsLoadingAdapter
import com.example.wassignment.databinding.FragmentContactListBinding
import com.example.wassignment.viewmodel.ContactsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ContactListFragment : Fragment() {

    private val contactsViewModel: ContactsViewModel by lazy {
        ViewModelProvider(this).get(ContactsViewModel::class.java)
    }
    private var _binding: FragmentContactListBinding? = null
    private val binding: FragmentContactListBinding
        get() = _binding!!

    @Inject lateinit var contactsAdapter: ContactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentContactListBinding.inflate(
            inflater, container, false
        )

        renderView(contactsAdapter)
        fetchPosts(contactsAdapter)
        return _binding?.root
    }

    private fun fetchPosts(contactsAdapter: ContactsAdapter) {
        lifecycleScope.launch {
            contactsViewModel.fetchPosts().collectLatest { pagingData ->
                contactsAdapter.submitData(pagingData)
            }
        }
    }

    private fun renderView(contactsAdapter: ContactsAdapter) {
        val rvContactsList = binding.rvContactsList

        rvContactsList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )


        rvContactsList.adapter = contactsAdapter
        rvContactsList.adapter = contactsAdapter.withLoadStateHeaderAndFooter(
            header = ContactsLoadingAdapter { contactsAdapter.retry() },
            footer = ContactsLoadingAdapter { contactsAdapter.retry() }
        )
    }
}