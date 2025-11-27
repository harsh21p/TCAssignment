package com.assignment.tcimageapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.assignment.tcimageapp.presentation.navigation.AppNavGraph
import com.assignment.tcimageapp.ui.theme.TCImageAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TCImageAppTheme {
                AppNavGraph()
            }
        }
    }
}
