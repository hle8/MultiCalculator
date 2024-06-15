package org.huyle.multicalculator

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalcView()
        }
    }
}

@Preview
@Composable
fun CalcView() {
    var leftNumber by rememberSaveable { mutableIntStateOf(0) }
    var rightNumber by rememberSaveable { mutableIntStateOf(0) }
    var operation by rememberSaveable { mutableStateOf("") }
    var complete by rememberSaveable { mutableStateOf(false) }

    val displayText = remember { mutableStateOf("0") }

    if (complete && operation.isNotEmpty()) {
        var answer by remember { mutableIntStateOf(0) }

        when (operation) {
            "+" -> answer = leftNumber.plus(rightNumber)
            "-" -> answer = leftNumber.minus(rightNumber)
            "*" -> answer = leftNumber.times(rightNumber)
            "/" -> answer = leftNumber.div(rightNumber)
        }

        displayText.value = answer.toString()
    }
    else if (!complete && operation.isNotEmpty()) {
        displayText.value = rightNumber.toString()
    }
    else {
        displayText.value = leftNumber.toString()
    }

    fun numberPress(btnNum: Int) {
        if (complete) {
            leftNumber = 0
            rightNumber = 0
            operation = ""
            complete = false
        }

        if (operation.isNotEmpty() && !complete) {
            rightNumber = rightNumber * 10 + btnNum
        }

        if (operation.isEmpty() && !complete) {
            leftNumber = leftNumber * 10 + btnNum
        }
    }

    fun operationPress(op: String) {
        if (!complete)
            operation = op
    }

    fun equalsPress() { complete = true }

    Column(Modifier.background(Color.LightGray)) {
        Row {
            CalcDisplay(displayText)
        }
        Row {
            Column {
                for(i in 7 downTo 1 step 3) {
                    CalcRow({ number: Int -> numberPress(number) }, i, 3)
                }

                Row {
                    CalcNumericButton(0) { number: Int -> numberPress(number) }
                    CalcEqualsButton { equalsPress() }
                }
            }
            Column {
                CalcOperationButton("+") { op: String -> operationPress(op) }
                CalcOperationButton("-") { op: String -> operationPress(op) }
                CalcOperationButton("*") { op: String -> operationPress(op) }
                CalcOperationButton("/") { op: String -> operationPress(op) }
            }
        }
    }
}

@Composable
fun CalcRow(onPress: (number: Int) -> Unit, startNum: Int, numButtons: Int) {
    val endNum = startNum.plus(numButtons)

    Row(Modifier.padding(0.dp)) {
        for(num in startNum..<endNum) {
            CalcNumericButton(num, onPress)
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
fun CalcNumericButton(number: Int, onPress: (number: Int) -> Unit) {
    Button(onClick = { onPress(number) }, Modifier.padding(4.dp)) {
        Text("$number")
    }
}

@Composable
fun CalcOperationButton(operation: String, onPress: (operation: String) -> Unit) {
    Button(onClick = { onPress(operation) }, Modifier.padding(4.dp)) {
        Text(operation)
    }
}

@Composable
fun CalcEqualsButton(onPress: () -> Unit) {
    Button(onClick = { onPress() }, Modifier.padding(4.dp)) {
        Text("=")
    }
}