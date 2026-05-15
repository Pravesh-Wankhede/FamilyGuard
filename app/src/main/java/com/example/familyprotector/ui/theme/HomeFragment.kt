package com.example.familyprotector.ui.theme

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.familyprotector.R
import com.example.familyprotector.ui.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var inviteAdapter: InviteAdapter
    private val listContacts = ArrayList<ContactModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ✅ Members list (dummy)
        val listMember = listOf(
            MemberModel("Pravesh", "Gandhi ward", "97%", "9 Km"),
            MemberModel("Steve", "Queens", "17%", "1285 Km"),
            MemberModel("Hamza", "Layari", "100%", "500 Km")
        )

        val recycler = view.findViewById<RecyclerView>(R.id.family_list)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = MemberAdapter(listMember)

        // ✅ Invite Recycler
        inviteAdapter = InviteAdapter(listContacts)

        val inviteRecycler = view.findViewById<RecyclerView>(R.id.recycler_invite)
        inviteRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        inviteRecycler.adapter = inviteAdapter

        // ✅ Load contacts safely (NO LAG)
        loadContacts()

        // ✅ Logout (fixed)
        val threeDots = view.findViewById<ImageView>(R.id.menu_dots)
        threeDots.setOnClickListener {

            SharedPreference.setLogin(false)
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    // 🔥 SAFE CONTACT LOADER
    private fun loadContacts() {

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {

                val contacts = fetchContacts()

                requireActivity().runOnUiThread {
                    listContacts.clear()
                    listContacts.addAll(contacts)
                    inviteAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    // 🔥 CONTACT FETCH (SAFE)
    private fun fetchContacts(): ArrayList<ContactModel> {

        val list = ArrayList<ContactModel>()
        val cr = requireActivity().contentResolver

        val cursor = cr.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.use {
            while (it.moveToNext()) {

                val name = it.getString(
                    it.getColumnIndexOrThrow(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                    )
                )

                val phone = it.getString(
                    it.getColumnIndexOrThrow(
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    )
                )

                if (!phone.isNullOrEmpty()) {
                    list.add(ContactModel(name, phone))
                }
            }
        }

        return list
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}