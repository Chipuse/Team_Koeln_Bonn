package com.example.team_koeln_bonn

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.team_koeln_bonn.composables.MapScreen
import com.example.team_koeln_bonn.composables.OurBottomBar
import com.example.team_koeln_bonn.composables.OurTopBar
import com.example.team_koeln_bonn.ui.screens.MenuScreen
import com.example.team_koeln_bonn.ui.screens.SosScreen
import com.example.team_koeln_bonn.ui.theme.Team_Koeln_BonnTheme
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        setContent {
            Team_Koeln_BonnTheme {
                Scaffold(
                    modifier = Modifier,
                    //top app bar content
                    topBar = {
                        OurTopBar()
                    },
                    floatingActionButton = {

                    },
                    content = {
                            paddingValues ->
                        Box(
                            modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxSize()
                                .consumeWindowInsets(paddingValues)
                        ) {
                            MapScreen()
                        }
                    },
                    bottomBar = {2
                        OurBottomBar()
                    }
                )

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello Ceyda & Eylem & Mai! Welcome to $name",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Team_Koeln_BonnTheme {
        Greeting("Android")
    }
}