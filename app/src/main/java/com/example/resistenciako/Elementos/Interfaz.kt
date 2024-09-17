package com.example.resistenciako

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Interfaz() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffffeb46))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Resistencia",fontSize=50.sp, color = Color.Blue)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Banda 1", fontSize = 20.sp, color = Color.Red)
        Spacer(modifier = Modifier.height(16.dp))
        val band1Colors = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo", "Verde", "Azul", "Violeta", "Gris", "Blanco")
        var band1 by remember { mutableStateOf(band1Colors[0]) }
        Dropdown("Seleccionar", band1Colors) { band1 = it }

        Spacer(modifier = Modifier.height(18.dp))

        Text("Banda 2", fontSize = 21.sp, color = Color.Red)
        Spacer(modifier = Modifier.height(10.dp))
        var band2 by remember { mutableStateOf(band1Colors[0]) }
        Dropdown("Seleccionar", band1Colors) { band2 = it }

        Spacer(modifier = Modifier.height(18.dp))

        Text("Banda 3", fontSize = 21.sp, color = Color.Red)
        Spacer(modifier = Modifier.height(8.dp))
        val band3Colors = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo", "Verde", "Azul", "Violeta", "Gris", "Blanco", "Dorado", "Plateado")
        var band3 by remember { mutableStateOf(band3Colors[0]) }
        Dropdown("Seleccionar", band3Colors) { band3 = it }

        Spacer(modifier = Modifier.height(18.dp))

        Text("Banda 4", fontSize = 21.sp, color = Color.Red)
        Spacer(modifier = Modifier.height(8.dp))
        val band4Colors = listOf("Ninguno", "Dorado", "Plateado")
        var band4 by remember { mutableStateOf(band4Colors[0]) }
        Dropdown("Seleccionar", band4Colors) { band4 = it }

        Spacer(modifier = Modifier.height(30.dp))

        var resultado by remember { mutableStateOf(TextFieldValue("")) }
        TextField(
            value = resultado,
            onValueChange = {},
            label = { Text("Resultado") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.LightGray
            )
        )


        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {

            resultado = TextFieldValue(calcularResistencia(band1, band2, band3, band4))
        }) {
            Text("Calcular")
        }
    }
}

@Composable
fun Dropdown(defaultText: String, options: List<String>, onSelectionChanged: (String) -> Unit) {
    var desplegar by remember { mutableStateOf(false) }
    var seleccionar by remember { mutableStateOf(defaultText) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)
            .clickable { desplegar = !desplegar }
    ) {
        Text(seleccionar, fontSize = 18.sp)
        DropdownMenu(
            expanded = desplegar,
            onDismissRequest = { desplegar = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        seleccionar = option
                        desplegar = false
                        onSelectionChanged(option)
                    }
                )
            }
        }
    }
}

fun calcularResistencia(band1: String, band2: String, band3: String, band4: String): String {
    val colorValores = mapOf(
        "Negro" to 0,
        "Marrón" to 1,
        "Rojo" to 2,
        "Naranja" to 3,
        "Amarillo" to 4,
        "Verde" to 5,
        "Azul" to 6,
        "Violeta" to 7,
        "Gris" to 8,
        "Blanco" to 9
    )

    val multiplicarValores = mapOf(
        "Negro" to 1.0,
        "Marrón" to 10.0,
        "Rojo" to 100.0,
        "Naranja" to 1000.0,
        "Amarillo" to 10000.0,
        "Verde" to 100000.0,
        "Azul" to 1000000.0,
        "Violeta" to 10000000.0,
        "Gris" to 100000000.0,
        "Blanco" to 1000000000.0,
    )

    val valorTol = mapOf(
        "Ninguno" to 20,
        "Dorado" to 5,
        "Plateado" to 10
    )

    val valorBanda1 = colorValores[band1] ?: 0
    val valorBanda2 = colorValores[band2] ?: 0
    val valorBanda4 = valorTol[band4] ?: 0

    val baseValue = (valorBanda1 * 10) + valorBanda2
    val multiplicador = multiplicarValores[band3] ?: 1.0
    val resistencia = baseValue.toDouble() * multiplicador

    return "$resistencia ohmios (Ω) con tolerancia de $valorBanda4%"
}
