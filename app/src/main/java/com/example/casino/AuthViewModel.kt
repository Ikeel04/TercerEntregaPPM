package com.example.casino

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthViewModel : ViewModel(){
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState : MutableLiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus(){
        if(auth.currentUser != null){
            _authState.value = AuthState.Unauthenticated
        }else{
            _authState.value = AuthState.Authenticated
        }
    }

    fun login(email : String, password : String){

        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email y contraseña son requeridos")
            return
        }
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    _authState.value = AuthState.Authenticated
                }else{
                    _authState.value = AuthState.Error(task.exception?.message ?: "Error desconocido")
                }
            }
    }

    fun registro(email : String, password : String){

        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email y contraseña son requeridos")
            return
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful) {
                    val usuario = Usuario(
                        email = email,
                        dineroGanado = 0.0,
                        dineroPerdido = 0.0,
                        juegoMasJugado = "",
                        victoriasDerrotas = mapOf("victorias" to 0, "derrotas" to 0)
                    )
                    firestore.collection("usuarios")
                        .document(auth.currentUser?.uid ?: "")
                        .set(usuario)
                        .addOnSuccessListener {
                            _authState.value = AuthState.Authenticated
                        }
                        .addOnFailureListener { e ->
                            _authState.value =
                                AuthState.Error(e.message ?: "Error al guardar datos en Firestore")
                        }
                }
                    else{
                        _authState.value =
                            AuthState.Error(task.exception?.message ?: "Error desconocido")
                    }
                }
    }

    fun logout(){
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }


}

sealed class AuthState {
    data object Authenticated : AuthState()
    data object Unauthenticated : AuthState()
    data object Loading : AuthState()
    data class Error(val message: String) : AuthState()

}