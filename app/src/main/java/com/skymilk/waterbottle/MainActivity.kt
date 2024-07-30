package com.skymilk.waterbottle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.skymilk.waterbottle.ui.theme.WaterBottleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaterBottleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    var drinkWaterAmount by remember {
                        mutableIntStateOf(500)
                    }

                    val totalWaterAmount = remember {
                        3800
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(Color.Gray),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        //물병 그리기
                        WaterBottle(
                            totalWaterAmount = totalWaterAmount,
                            drinkWaterAmount = drinkWaterAmount,
                            unit = "ml",
                            modifier = Modifier.width(300.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(text = "물통 용량 : ${totalWaterAmount}ml")

                        Button(onClick = { drinkWaterAmount += 200 }) {
                            Text(text = "물 마시기")
                        }
                    }
                }
            }
        }
    }
}