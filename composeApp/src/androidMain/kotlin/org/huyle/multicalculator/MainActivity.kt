package org.huyle.multicalculator

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

@Composable
fun CalcView() {
    val displayText = mutableStateOf("0")
    
    Column(Modifier.background(Color.LightGray)) {
        Row {
            CalcDisplay(displayText)
        }
        Row {
            Column {
                for(i in 7 downTo 1 step 3) {
                    CalcRow(displayText, i, i)
                }

                Row {
                    CalcNumericButton(0, displayText)
                    CalcEqualsButton(displayText)
                }
            }
            Column {
                CalcOperationButton("+", displayText)
                CalcOperationButton("-", displayText)
                CalcOperationButton("*", displayText)
                CalcOperationButton("/", displayText)
            }
        }
    }
}

@Composable
fun CalcRow(display: MutableState<String>, startNum: Int, numButtons: Int) {
    val endNum = startNum.plus(numButtons)

    Row(Modifier.padding(0.dp)) {
        for(num in startNum..<endNum) {
            CalcNumericButton(num, display)
    }
    }
}

@Composable
fun CalcDisplay(display: MutableState<String>) {
    Text(display.value,
        Modifier
            .height(50.dp)
            .padding(5.dp)
            .fillMaxWidth())
}

@Composable
fun CalcNumericButton(number: Int, display: MutableState<String>) {
    Button(onClick = {display.value = number.toString()} ,Modifier.padding(4.dp)) {
        Text("$number")
    }
}

@Composable
fun CalcOperationButton(operation: String, display: MutableState<String>) {
    Button(onClick = {} ,Modifier.padding(4.dp)) {
        Text(operation)
    }
}

@Composable
fun CalcEqualsButton(display: MutableState<String>) {
    Button(onClick = {display.value = "0"} ,Modifier.padding(4.dp)) {
        Text("=")
    }
}