package com.example.firebasetemplate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.firebasetemplate.databinding.FragmentRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterFragment extends AppFragment {
    private FragmentRegisterBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentRegisterBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.createAccountButton.setOnClickListener(v -> {
            if (binding.passwordEditText.getText().toString().isEmpty()) {
                binding.passwordEditText.setError("Required");
                return;
            }
            FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(
                            binding.emailEditText.getText().toString(),
                            binding.passwordEditText.getText().toString()
                    ).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // upload photo i obtinc url de descarrega
                            //    successs: actualizte e perfil de l'usuari amb el nom i la foto
                            navController.navigate(R.id.action_registerFragment_to_postsHomeFragment);
                        } else {
                            Toast.makeText(requireContext(), task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}