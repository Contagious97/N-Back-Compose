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
                    key = "sw4",
                    title = "Simple switch",
                    summary = "But with a leading icon and summary",
                    leadingIcon = { Icon(Icons.Filled.Home, "Home") }
                )  }

                prefsItem { SliderPref(
                    key = "sp1",
                    title = "Slider example with custom range and value shown on side",
                    valueRange = 50f..200f,
                    showValue = true
                )}

                prefsItem { ListPref(
                    key = "l1",
                    title = "ListPref example",
                    summary = "Opens up a dialog of options",
                    entries = mapOf(
                        "0" to "Entry 1",
                        "1" to "Entry 2",
                        "2" to "Entry 3",
                        "3" to "Entry 4",
                        "4" to "Entry 5"
                    )
                ) }

                prefsItem { DropDownPref(
                    key = "dd1",
                    title = "Dropdown with currently selected item as summary",
                    useSelectedAsSummary = true,
                    entries = mapOf(
                        "0" to "Entry 1",
                        "1" to "Entry 2",
                        "2" to "Entry 3"
                    )
                ) }

            })


        }
    }
}