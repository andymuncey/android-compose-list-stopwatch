package com.tinyappco.composeliststopwatchdemo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tinyappco.composeliststopwatchdemo.ui.theme.ComposeListStopwatchDemoTheme
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeListStopwatchDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Stopwatch()
                }
            }
        }
    }


    private val listItems = mutableStateListOf<String>()

    private var startTime by mutableStateOf(0L)


    private fun reset() {
        startTime = 0
        listItems.clear()
    }


    @Composable
    fun Stopwatch() {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Stopwatch") },
                    actions = {
                        IconButton(onClick = { reset() }) {
                            Icon(Icons.Filled.Refresh, contentDescription = "Reset")
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    if (startTime == 0L) {
                        Toast.makeText(this, "Timer started", Toast.LENGTH_SHORT).show()
                        startTime = System.currentTimeMillis()
                    } else {
                        val elapsedTime = (System.currentTimeMillis() - startTime)
                        val ms = elapsedTime % 1000
                        val s = elapsedTime / 1000
                        val formatter = DecimalFormat("0000")
                        val formattedMillis = formatter.format(ms)
                        listItems.add("$s:$formattedMillis")

                    }
                }) {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
                }
            },
            content = { paddingValues ->
                TimeList()
                Spacer(modifier = Modifier.padding(paddingValues))
            }
        )
    }

    @Composable
    fun TimeList() {
        LazyColumn() {
            items(listItems) { time ->
                Text(text = time,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 20.sp)
            }
        }
    }

}






