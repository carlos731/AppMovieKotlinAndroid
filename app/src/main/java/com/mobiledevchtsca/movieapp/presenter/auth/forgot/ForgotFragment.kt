package com.mobiledevchtsca.movieapp.presenter.auth.forgot

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mobiledevchtsca.movieapp.R
import com.mobiledevchtsca.movieapp.databinding.FragmentForgotBinding
import com.mobiledevchtsca.movieapp.util.FirebaseHelper
import com.mobiledevchtsca.movieapp.util.StateView
import com.mobiledevchtsca.movieapp.util.hideKeyboard
import com.mobiledevchtsca.movieapp.util.initToolbar
import com.mobiledevchtsca.movieapp.util.isEmailValid
import com.mobiledevchtsca.movieapp.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotFragment : Fragment() {

    private var _binding: FragmentForgotBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ForgotViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(toolbar = binding.toolbar)

        initListeners()
    }

    private fun initListeners() {
        binding.btnForgot.setOnClickListener { validateData() }

        Glide
            .with(requireContext())
            .load(R.drawable.loading)
            .into(binding.progressLoading)
    }

    private fun validateData() {
        val email = binding.editEmail.text.toString()

        iniciaEditTexts()

        if (email.isNotEmpty()) {
            if (email.isEmailValid()) {

                hideKeyboard()

                forgot(email)

            } else {
                binding.editEmail.requestFocus()
                binding.edtEmailLayout.error = "Informe um email válido."
                //showSnackBar(message = R.string.text_email_empty_forgot_fragment)
            }
        } else {
            binding.editEmail.requestFocus()
            binding.edtEmailLayout.error = "Informe seu email."
        }
    }

    private fun forgot(email: String) {
        viewModel.forgot(email).observe(viewLifecycleOwner) { stateView ->
            when(stateView){
                is StateView.Loading -> {
                    binding.progressLoading.isVisible = true
                }
                is StateView.Success -> {
                    binding.progressLoading.isVisible = false

                   //showSnackBar(message = R.string.text_send_email_success_empty_forgot_fragment)

                    Toast.makeText(requireContext(), "Email enviado com sucesso!", Toast.LENGTH_SHORT).show()
                }
                is StateView.Error -> {
                    binding.progressLoading.isVisible = false

                    //showSnackBar(message = FirebaseHelper.validError(error = stateView.message ?: ""))

                    Toast.makeText(requireContext(), FirebaseHelper.validError(error = stateView.message ?: ""), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun iniciaEditTexts() {
        binding.editEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Implementar antes da mudança de texto
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Implementar durante a mudança de texto
                binding.edtEmailLayout.error = ""
                binding.edtEmailLayout.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {
                // Implementar após a edição do texto
            }
        })
    }

}