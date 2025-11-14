package com.example.test04

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    private val actionMap = mapOf(
        "createRandom" to CreateRandomActivity::class.java,
        "CreateAccount" to CreateAccountActivity::class.java,
        "ImportAccountFromMnemonic" to ImportAccountFromMnemonicActivity::class.java,
        "trxTransfer" to TransferActivity::class.java,
        "trc20Transfer" to TransferActivity::class.java,
        "getTRC20TokenBalance" to GetBalanceActivity::class.java,
        "getTRXBalance" to GetBalanceActivity::class.java,
        "getAccount" to GetAccountActivity::class.java,
        "getAccountResource" to GetAccountResourceActivity::class.java,
        "getChainParameters" to GetChainParametersActivity::class.java,
        "estimateTRC20TransferFee" to FeeEstimateActivity::class.java,
        "estimateTRXTransferFee" to FeeEstimateActivity::class.java,
        "ImportAccountFromPrivateKey" to ImportAccountFromPrivateKeyActivity::class.java,
        "SignMessageV2" to SignMessageV2Activity::class.java,
        "VerifyMessageV2" to VerifyMessageV2Activity::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setContent {
            MaterialTheme {
                val listData = viewModel.listData.observeAsState(emptyList())
                WalletMainScreen(listData.value) { position, action ->
                    navigateToAction(position, action)
                }
            }
        }
    }

    private fun navigateToAction(position: Int, action: String) {
        val targetClass = actionMap[action] ?: ResetTronWebPrivateKeyActivity::class.java
        val intent = Intent(this, targetClass)
        intent.putExtra("position", position)
        intent.putExtra("action", action)
        startActivity(intent)
    }
}

@Composable
fun WalletMainScreen(listData: List<TestData>, onActionClick: (Int, String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Верхняя карточка баланса
        item {
            BalanceCard(balance = "123.45 TRX")
        }

        // Действия
        itemsIndexed(listData) { index, testData ->
            ActionSectionCard(section = testData, position = index, onActionClick = onActionClick)
        }
    }
}

@Composable
fun BalanceCard(balance: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        colors = CardDefaults.cardColors(
            containerColor = Brush.horizontalGradient(
                colors = listOf(Color(0xFF3A1C71), Color(0xFFD76D77))
            ).toColor()
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Wallet Balance",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = balance,
                color = Color.White,
                fontSize = 28.sp
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { /* Send action */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f))
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Send", color = Color.White)
                }
                Button(
                    onClick = { /* Receive action */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f))
                ) {
                    Icon(Icons.Default.AccountBalanceWallet, contentDescription = "Receive", tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Receive", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ActionSectionCard(section: TestData, position: Int, onActionClick: (Int, String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = section.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            val actions = listOf(
                section.action1, section.action2, section.action3, section.action4, section.action5,
                section.action6, section.action7, section.action8, section.action9, section.action10,
                section.action11, section.action12, section.action13, section.action14, section.action15,
                section.action16, section.action17
            )
            actions.forEach { action ->
                if (action.isNotEmpty()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onActionClick(position, action) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFEFEF))
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = action, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}