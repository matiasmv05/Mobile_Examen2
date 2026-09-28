package org.ucb.appp1.login.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.appp1.login.presentation.viewmodel.LoginEffect
import org.ucb.appp1.login.presentation.viewmodel.LoginEvent
import org.ucb.appp1.login.presentation.viewmodel.LoginState
import org.ucb.appp1.login.presentation.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onNavigateToCatalog: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                LoginEffect.NavigateToCatalog -> onNavigateToCatalog()
                LoginEffect.NavigateToRegister -> onNavigateToRegister()
                is LoginEffect.ShowMessage -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    LoginContent(
        state = state,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::emitEvent
    )
}

@Composable
private fun LoginContent(
    state: LoginState,
    snackbarHostState: SnackbarHostState,
    onEvent: (LoginEvent) -> Unit
) {
    val darkBackground = Color(0xFF0F0E13)
    val goldAccent = Color(0xFFC5954B).copy(alpha = 0.25f)
    val fieldBackground = Color(0xFF23222B)
    val buttonBlue = Color(0xFF007AFF)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = darkBackground
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(darkBackground, goldAccent, darkBackground)
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "LOG IN",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(36.dp))

                // Username Label & Field
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Nombre de usuario",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = state.username,
                        onValueChange = { onEvent(LoginEvent.OnUsernameChanged(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = fieldBackground,
                            unfocusedContainerColor = fieldBackground,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Password Label & Field
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Contraseña",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = state.password,
                        onValueChange = { onEvent(LoginEvent.OnPasswordChanged(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            Text(
                                text = if (state.isPasswordVisible) "👁" else "🙈",
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .clickable { onEvent(LoginEvent.OnTogglePasswordVisibility) }
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = fieldBackground,
                            unfocusedContainerColor = fieldBackground,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // ENTRAR Button
                Button(
                    onClick = { onEvent(LoginEvent.OnSubmitLogin) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonBlue),
                    enabled = !state.isLoading
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
                    } else {
                        Text(
                            text = "ENTRAR",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            letterSpacing = 1.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Olvidé mi contraseña
                Text(
                    text = "Olvidé mi contraseña",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { onEvent(LoginEvent.OnForgotPasswordClicked) }
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Ir a Registrarse
                TextButton(onClick = { onEvent(LoginEvent.OnNavigateToRegister) }) {
                    Text(
                        text = "¿No tienes cuenta? Regístrate",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }
    }
}
