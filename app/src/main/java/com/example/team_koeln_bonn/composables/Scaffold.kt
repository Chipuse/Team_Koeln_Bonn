package com.example.team_koeln_bonn.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar

/*
TopAppBar(
                title = { Text("Title")},
                navigationIcon = {
                    IconButton(
                        onClick = {}) {
                        Icon(Icons.Filled.Menu, contentDescription = "Icon 1")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary)
            )

 */

@Composable
@Preview
fun DisplayScaffold() {
    MainScaffold(mainContent =
        {

    })
}

@Composable
fun OurTopBar(modifier : Modifier = Modifier){
    BottomAppBar(
        modifier = Modifier,
        containerColor = MaterialTheme.colorScheme.primary
    ){
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.Left,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {}) {
                Icon(Icons.Filled.Menu, contentDescription = "Icon 1")
            }
            Text(
                "Title",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun OurBottomBar(modifier : Modifier = Modifier){

    BottomAppBar(
        modifier = Modifier,
        containerColor = MaterialTheme.colorScheme.primary
    ){
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = {}) {
                Icon(Icons.Filled.Person, contentDescription = "Icon 1")
            }
            IconButton(onClick = {}) {
                Icon(Icons.Filled.LocationOn, contentDescription = "Icon 1")
            }
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Call, contentDescription = "Icon 1")
            }
            IconButton(onClick = {}) {
                Icon(Icons.Filled.MailOutline, contentDescription = "Icon 1")
            }
        }
    }
}


@Composable
fun MainScaffold(modifier : Modifier = Modifier, mainContent : @Composable () -> Unit){
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
                IconButton(
                    onClick = {}) {
                    Icon(Icons.Filled.Menu, contentDescription = "Icon 1")
                }
                Text(
                    "Title",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        },
        bottomBar = {
            OurBottomBar()
        }
        )
}
