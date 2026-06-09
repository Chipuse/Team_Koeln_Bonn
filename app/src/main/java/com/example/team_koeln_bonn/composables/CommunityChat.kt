package com.example.team_koeln_bonn.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun DisplayCommunityChat(){
    CommunityChat()
}

//https://developer.android.com/develop/sensors-and-location/location/maps-and-places

@Composable
fun CommunityChat(modifier : Modifier = Modifier){
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
                Conversation( CreateMessageList())
            }
        },
        bottomBar = {
            OurBottomBar()
        }
    )
}

fun CreateMessageList() : List<Message>{
    var messages : MutableList<Message> = mutableListOf<Message>()
    for (i in 1..23) {
        messages.add(Message(author = "Maria", body = "Text $i"))
        if(i%3 == 0)
            messages.add(Message(left = false, author = "Robert", body = "Answer to $i"))
    }
    return messages
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn(modifier = Modifier.fillMaxWidth(), ) {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Composable
fun MessageCard(msg: Message) {
    var arrangment = Arrangement.End
    if(msg.left)
        arrangment = Arrangement.Start
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = arrangment)
    {
        Row(
            modifier = Modifier.padding(all = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            /*
    Image(
                painter = painterResource((res.drawable.chat_pb)),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )

             */

            Spacer(modifier = Modifier.width(8.dp))

            // We keep track if the message is expanded or not in this
            // variable
            var isExpanded by remember { mutableStateOf(false) }

            // We toggle the isExpanded variable when we click on this Column
            Column(modifier = Modifier
                .clickable{ isExpanded = !isExpanded }
            ) {
                Text(
                    text = msg.author,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall
                )

                Spacer(modifier = Modifier.height(4.dp))

                Surface(
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 1.dp,
                ) {
                    Text(
                        text = msg.body,
                        modifier = Modifier.padding(all = 4.dp),
                        // If the message is expanded, we display all its content
                        // otherwise we only display the first line
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

data class Message(val left : Boolean = true, val author: String, val body: String)