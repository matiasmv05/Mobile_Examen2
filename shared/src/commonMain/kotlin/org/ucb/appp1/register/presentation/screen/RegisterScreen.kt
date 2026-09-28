package org.ucb.appp1.register.presentation.screen

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
import org.ucb.appp1.register.presentation.viewmodel.RegisterEffect
import org.ucb.appp1.register.presentation.viewmodel.RegisterEvent
import org.ucb.appp1.register.presentation.viewmodel.RegisterState
import org.ucb.appp1.register.presentation.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    onNavigateToCatalog: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                RegisterEffect.NavigateToCatalog -> onNavigateToCatalog()
                RegisterEffect.NavigateToLogin -> onNavigateToLogin()
                is RegisterEffect.ShowMessage -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    RegisterContent(
        state = state,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::emitEvent
    )
}

@Composable
private fun RegisterContent(
    state: RegisterState,
    snackbarHostState: SnackbarHostState,
    onEvent: (RegisterEvent) -> Unit
) {
    val darkBackground = Color(0xFF0D0B14)
    val purpleAccent = Color(0xFF6B21A8).copy(alpha = 0.3f)
    val fieldBackground = Color(0xFF201D2A)
    val buttonPurple = Color(0xFF8E24AA)

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
                        colors = listOf(darkBackground, purpleAccent, darkBackground)
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
                    text = "REGISTRO",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Nombre Completo
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Nombre Completo",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = state.fullName,
                        onValueChange = { onEvent(RegisterEvent.OnFullNameChanged(it)) },
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

                Spacer(modifier = Modifier.height(14.dp))

                // Email
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Email",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = state.email,
                        onValueChange = { onEvent(RegisterEvent.OnEmailChanged(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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

                Spacer(modifier = Modifier.height(14.dp))

                // Contraseña
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
                        onValueChange = { onEvent(RegisterEvent.OnPasswordChanged(it)) },
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
                                    .clickable { onEvent(RegisterEvent.OnTogglePasswordVisibility) }
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

                Spacer(modifier = Modifier.height(14.dp))

                // Confirmar Contraseña
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Confirmar Contraseña",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = state.confirmPassword,
                        onValueChange = { onEvent(RegisterEvent.OnConfirmPasswordChanged(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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

                Spacer(modifier = Modifier.height(28.dp))

                // CREAR CUENTA Button
                Button(
                    onClick = { onEvent(RegisterEvent.OnSubmitRegister) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonPurple),
                    enabled = !state.isLoading
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
                    } else {
                        Text(
                            text = "CREAR CUENTA",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            letterSpacing = 1.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Ya tengo cuenta
                Text(
                    text = "Ya tengo cuenta",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { onEvent(RegisterEvent.OnNavigateToLogin) }
                )
            }
        }
    }
}
