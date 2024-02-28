package com.example.randomglassgame.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.randomglassgame.adapters.InventoryAdapter
import com.example.randomglassgame.contracts.router
import com.example.randomglassgame.databinding.FragmentInventoryBinding
import com.example.randomglassgame.entity.Profile
import com.example.randomglassgame.entity.Settings
import com.example.randomglassgame.entity.Skin

class InventoryFragment : Fragment() {

    private lateinit var profile: Profile
    private lateinit var settings: Settings

    private lateinit var adapter: InventoryAdapter

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settings = savedInstanceState?.getParcelable(Settings.EXTRA_SETTINGS, Settings::class.java)
            ?: getSettings()

        profile = savedInstanceState?.getParcelable(Profile.EXTRA_PROFILE, Profile::class.java)
            ?: getProfile()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentInventoryBinding.inflate(inflater, container, false)

        adapter = InventoryAdapter(settings, binding.rvInventory, profile.inventory, requireContext())

        with(binding) {
            rvInventory.adapter = adapter
            rvInventory.layoutManager = GridLayoutManager(requireContext(), 3, LinearLayoutManager.VERTICAL, false)
            rvInventory.setHasFixedSize(true)

            tvAvailableItemCount.text = profile.inventory.size.toString()
            tvAllItemCount.text = Skin.LIST.size.toString()

            btnBack.setOnClickListener { onBackClickListener() }
        }

        return binding.root
    }

    private fun onBackClickListener() {
        router().goBack()

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getProfile() : Profile = requireArguments().getParcelable(ARG_PROFILE, Profile::class.java)!!

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getSettings() : Settings = requireArguments().getParcelable(ARG_SETTINGS, Settings::class.java)!!

    companion object {

        @JvmStatic
        val ARG_PROFILE = "ARG_PROFILE"

        @JvmStatic
        private val ARG_SETTINGS = "ARG_SETTINGS"

        @JvmStatic
        fun newInstance(profile: Profile, settings: Settings): InventoryFragment {
            val args = Bundle().apply {
                putParcelable(ARG_PROFILE, profile)
                putParcelable(ARG_SETTINGS, settings)
            }
            val fragment = InventoryFragment()
            fragment.arguments = args
            return fragment
        }

    }

}