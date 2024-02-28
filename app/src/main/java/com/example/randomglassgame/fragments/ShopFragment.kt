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
import com.example.randomglassgame.adapters.ShopAdapter
import com.example.randomglassgame.contracts.balanceUpdater
import com.example.randomglassgame.contracts.router
import com.example.randomglassgame.databinding.FragmentShopBinding
import com.example.randomglassgame.entity.Profile
import com.example.randomglassgame.entity.Skin

class ShopFragment : Fragment() {

    private lateinit var profile: Profile
    private lateinit var binding: FragmentShopBinding

    private lateinit var adapter: ShopAdapter

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        profile = savedInstanceState?.getParcelable(Profile.EXTRA_PROFILE, Profile::class.java)
            ?: getProfile()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentShopBinding.inflate(inflater, container, false)

        adapter = ShopAdapter(profile, Skin.LIST, balanceUpdater(), requireContext())

        with(binding) {
            rvShop.adapter = adapter
            rvShop.layoutManager = GridLayoutManager(requireContext(), 3, LinearLayoutManager.VERTICAL, false)
            rvShop.setHasFixedSize(true)

            tvMaxScoreVal.text = profile.maxScore.toString()

            btnBack.setOnClickListener { onBackClickListener() }
        }

        return binding.root
    }

    private fun onBackClickListener() {
        router().goBack()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getProfile() : Profile = requireArguments().getParcelable(ARG_PROFILE, Profile::class.java)!!

    companion object {

        @JvmStatic
        val ARG_PROFILE = "ARG_PROFILE"

        @JvmStatic
        fun newInstance(profile: Profile): ShopFragment {
            val args = Bundle().apply {
                putParcelable(ARG_PROFILE, profile)
            }
            val fragment = ShopFragment()
            fragment.arguments = args
            return fragment
        }

    }
}