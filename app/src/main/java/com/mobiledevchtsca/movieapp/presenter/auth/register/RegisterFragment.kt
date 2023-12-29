package com.mobiledevchtsca.movieapp.presenter.auth.register

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
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import com.mobiledevchtsca.movieapp.R
import com.mobiledevchtsca.movieapp.databinding.FragmentRegisterBinding
import com.mobiledevchtsca.movieapp.util.StateView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
        binding.btnRegister.setOnClickListener { validateData() }

        Glide
            .with(requireContext())
            .load(R.drawable.loading)
            .into(binding.progressLoading)
    }

    private fun validateData() {
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()

        iniciaEditTexts()

        if (email.isNotEmpty()) {
            val regex = "^(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)$".toRegex()
            val matcher = regex.find(email)
            if (matcher != null && matcher.value == email) {
                if (password.isNotEmpty()) {
                    if (password.length >= 6) {
                        // Verifica se a senha contém pelo menos 1 caractere especial, 1 número e 1 letra
                        val senhaRegex = "^(?=.*[!@#$%^&*()_+\\-=\\\\[\\\\]{};':\\\"\\\\\\\\|,.<>\\\\/?])(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$".toRegex()
                        if (password.matches(senhaRegex)) {

                            ocultarTeclado()

                            register(email, password)

                        } else {
                            binding.editPassword.requestFocus()
                            //binding.editPassword.error = "A senha deve conter pelo menos 1 caractere especial, 1 número, 1 letra, e 1 letra maiúscula."
                            binding.edtSenhaLayout.error = "A senha deve conter pelo menos 1 caractere especial, 1 número, 1 letra, e 1 letra maiúscula."
                        }
                    } else {
                        binding.editPassword.requestFocus()
                        //binding.editPassword.error = "A senha deve ter pelo menos 6 caracteres"
                        binding.edtSenhaLayout.error = "A senha deve ter pelo menos 6 caracteres."
                    }
                } else {
                    binding.editPassword.requestFocus()
                    //binding.editPassword.error = "Informe sua senha."
                    binding.edtSenhaLayout.error = "Informe sua senha."
                }
            } else {
                binding.editEmail.requestFocus()
                binding.edtEmailLayout.error = "Informe um email válido."
            }
        } else {
            binding.editEmail.requestFocus()
            binding.edtEmailLayout.error = "Informe seu email."
        }
    }

    private fun register(email: String, password: String) {
        viewModel.register(email, password).observe(viewLifecycleOwner) { stateView ->
            when(stateView){
                is StateView.Loading -> {
                    binding.progressLoading.isVisible = true
                }
                is StateView.Success -> {
                    Toast.makeText(requireContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                }
                is StateView.Error -> {
                    binding.progressLoading.isVisible = false
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /*
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    */

    private fun ocultarTeclado() {
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            binding.btnRegister.windowToken, 0
        )
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

        binding.editPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Implementar antes da mudança de texto
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Implementar durante a mudança de texto
                binding.edtSenhaLayout.error = ""
                binding.edtSenhaLayout.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {
                // Implementar após a edição do texto
            }
        })
    }

}