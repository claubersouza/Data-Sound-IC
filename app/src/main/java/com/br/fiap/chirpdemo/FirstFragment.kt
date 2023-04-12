package com.br.fiap.chirpdemo

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.br.fiap.chirpdemo.chirp.ChirpSDK
import com.br.fiap.chirpdemo.chirp.ShortCode
import com.br.fiap.chirpdemo.databinding.FragmentFirstBinding
import io.chirp.chirpengine.ChirpEngine
import io.chirp.chirpengine.ChirpEngineObserver
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    val KEY = "chirp_android_1_4_0"
    val PWD = "Yur87CLY9Hg4hdebqYGAJjVhlRMAb7SPhXjiV1U7Qo5cx6D16W"
    private val handler = Handler()
    private var _binding: FragmentFirstBinding? = null
    private val chirpEngineListener: ChirpEngineObserver = object : ChirpEngineObserver {
        // from class: io.chirp.sdk.ChirpSDK.5
        // io.chirp.chirpengine.ChirpEngineObserver
        override fun onChirpReceived(shortCode: ShortCode?) {
            notifyObserversShortCodeReceived(shortCode)
        }
    }

    fun notifyObserversShortCodeReceived(shortCode: ShortCode?) {
        activity?.runOnUiThread {
            binding.msgRecebido.text = "Valor recebido:" + shortCode?.getShortCode().toString()
        }
    }
    @Inject
    var chirpSDK: ChirpSDK? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chirpSDK = activity?.baseContext?.let { it1 -> ChirpSDK(it1, KEY, PWD) }
        ChirpEngine().setListener(this.chirpEngineListener)
        binding.sendMsg.setOnClickListener {
            chirpSDK?.play(ShortCode(binding.edtMsg.text.toString()))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}