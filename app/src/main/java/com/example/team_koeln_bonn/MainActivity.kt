package com.example.team_koeln_bonn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.team_koeln_bonn.composables.Message
import com.example.team_koeln_bonn.composables.MessageCard
import com.example.team_koeln_bonn.composables.OurBottomBar
import com.example.team_koeln_bonn.composables.OurTopBar
import com.example.team_koeln_bonn.ui.theme.Team_Koeln_BonnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Team_Koeln_BonnTheme {
                MainActivityScaffold()
            }
        }
    }
}

@Composable
fun MainActivityScaffold(){
    Scaffold(
        modifier = Modifier,
        //top app bar content
        topBar = {
            OurTopBar()
        },
        floatingActionButton = {

        },

        //here belong the contents of the main window in the scaffold
        content = {
                paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .consumeWindowInsets(paddingValues)
            ) {
                Greeting("Android Studio")
            }
        },
        bottomBar = {
            OurBottomBar()
        }
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column{

        Text(
            text = "Hello Ceyda & Eylem & Mai! Welcome to $name",
            modifier = modifier
        )
        MessageCard((Message(author = "Teil 1", body = "Teil 2")))
    }
}