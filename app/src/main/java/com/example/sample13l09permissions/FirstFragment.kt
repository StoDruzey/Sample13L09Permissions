package com.example.sample13l09permissions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.sample13l09permissions.databinding.FragmentFirstBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = requireNotNull(_binding)
    private var currentRequest: Call<List<User>>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentFirstBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val githubInterface = retrofit.create<GithubInterface>()
        currentRequest = githubInterface
            .getUsers()
            .apply {
                enqueue(object : Callback<List<User>>{
                    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                        if (response.isSuccessful) {
                            val users = response.body() ?: return
                            binding.imageView.load(users[0].avatarUrl)
                        } else {
                            HttpException(response).message()
                        }
                    }

                    override fun onFailure(call: Call<List<User>>, t: Throwable) {
                        println()
                    }
                })
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        currentRequest?.cancel()
        _binding = null
    }
}