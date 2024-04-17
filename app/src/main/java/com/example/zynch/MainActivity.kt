package com.example.zynch

import ApiService
import DataResponse
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.zynch.ui.theme.ZynchTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Objects

private lateinit var  retrofit: Retrofit


val userName = mutableStateOf<Any?>(null)
val finded = mutableStateOf<Any?>(null)
val motos = mutableStateOf<Any?>(null)


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retrofit = getRetrofit()
        setContent {
            ZynchTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Title()
                    UserForm()
                    GottenUser()
                }
            }
        }
    }
}

@Composable
fun Title() {
    Text(
        text = "Buscador de usuarios Zynch",
        modifier = Modifier
            .padding(
            top = 20.dp,
            start = 15.dp
            ),
        fontSize = 20.sp
    )
}

@Composable
fun UserForm() {

    val fullNameState = remember { mutableStateOf("") }
    val phoneState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {

        TextField(
            value = fullNameState.value,
            onValueChange = { fullNameState.value = it },
            label = { Text("Ingresa Nombre completo") },
            modifier = Modifier.padding(start = 25.dp ,top = 70.dp)
        )

        TextField(
            value = phoneState.value,
            onValueChange = { phoneState.value = it },
            label = { Text("Ingresa el telefono") },
            modifier = Modifier.padding(start = 25.dp ,top = 30.dp)

        )

        TextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Ingresa el email") },
            modifier = Modifier.padding(start = 25.dp ,top = 30.dp)
        )

        Button(
            onClick = {
                val fullName = fullNameState.value
                val phone = phoneState.value
                val email = emailState.value
                sendQuery(fullName, phone, email)
            },
            modifier = Modifier
                .padding(top = 20.dp, start = 220.dp)
        ){
            Text("Buscar")
        }
    }

}

@Composable
fun GottenUser(){
    if(finded.value == "true"){
        Text(
            text = "Usuario encontrado: ${finded.value}",
            modifier = Modifier
                .padding(
                    top = 400.dp,
                    start = 50.dp
                ),
            fontSize = 20.sp
        )
        Text(
            text = "Nombre ${userName.value}",
            modifier = Modifier
                .padding(
                    top = 435.dp,
                    start = 50.dp
                ),
            fontSize = 20.sp
        )
        Text(
            text = "Motos ${motos.value}",
            modifier = Modifier
                .padding(
                    top = 465.dp,
                    start = 50.dp
                ),
            fontSize = 20.sp
        )
    }
}

private fun getRetrofit(): Retrofit{
    return Retrofit
        .Builder()
        .baseUrl("http://10.0.2.2:3000/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}

private fun sendQuery(fullName: String, phone: String, email: String){
    CoroutineScope(Dispatchers.IO).launch {
        val myResponse: Response<DataResponse> = retrofit.create(ApiService::class.java).getUser(fullName, phone, email)
        if(myResponse.isSuccessful){
            val response: DataResponse? = myResponse.body()
            if(response != null){
                finded.value = response.ok
                userName.value = response.usuario
                motos.value = response.motos
                println(motos.value)
            }
        }else{
            println("no funciona")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ZynchTheme {
        Title()
    }
}