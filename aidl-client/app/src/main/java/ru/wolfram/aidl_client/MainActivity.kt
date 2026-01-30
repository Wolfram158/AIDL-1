package ru.wolfram.aidl_client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import ru.wolfram.aidl_app.presentation.theme.AidlAppTheme

class MainActivity : ComponentActivity() {
    private lateinit var appModel: AppModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        appModel = AppModel()
        appModel.bindService(this)

        setContent {
            AidlAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    MainScreen(paddingValues, appModel)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        appModel.unbindService(this)
    }
}