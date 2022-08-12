package com.example.n_back_compose

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jamal.composeprefs.ui.GroupHeader
import com.jamal.composeprefs.ui.PrefsScreen
import com.jamal.composeprefs.ui.prefs.*



@OptIn(ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(datastore: DataStore<Preferences>, navController: NavController){
    val coroutineScope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {

            TopAppBar(
                title = {Text("Settings")},
                navigationIcon = {

                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }


            }
        )}


    ){
        Column(
            modifier = Modifier.scrollable(
                state = rememberScrollState(),
                orientation = Orientation.Vertical,
            )
        ){
            PrefsScreen(dataStore = datastore, content = {
                prefsItem { SwitchPref(
                    key = "sw4",
                    title = "Simple switch",
                    summary = "But with a leading icon and summary",
                    leadingIcon = { Icon(Icons.Filled.Home, "Home") }
                ) }
                prefsItem {SwitchPref(
                    key = "sw5",
                    title = "Simple switch",
                    summary = "But with a leading icon and summary",
                    leadingIcon = { Icon(Icons.Filled.Home, "Home") }
                )  }

                prefsItem { SliderPref(
                    key = "sp1",
                    title = "Time between delay",
                    valueRange = 1000f..2000f,
                    showValue = true
                )}

                prefsItem { ListPref(
                    key = "l1",
                    title = "N-Value",
                    useSelectedAsSummary = true,
                    entries = mapOf(
                        "0" to "2",
                        "1" to "3",
                        "2" to "4",
                        "3" to "5"
                    )
                ) }

                prefsItem { DropDownPref(
                    key = "dd1",
                    title = "Visual or auditory",
                    useSelectedAsSummary = true,
                    entries = mapOf(
                        "0" to "Auditory",
                        "1" to "Visual"
                    )
                ) }

            })


        }
    }
}