package com.p4r4d0x.hegemonytaxes.presenter.roles.compose

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole
import com.p4r4d0x.hegemonytaxes.domain_data.model.InputValidation
import com.p4r4d0x.hegemonytaxes.ui.data.RoleUiData
import com.p4r4d0x.hegemonytaxes.ui.theme.Orange
import com.p4r4d0x.hegemonytaxes.ui.utils.Utils
import com.p4r4d0x.hegemonytaxes.ui.utils.Utils.checkValidRange
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleInputText(
    context: Context,
    roleUi: RoleUiData,
    labelText: String,
    inputText: String,
    maxValue: Int,
    onValueChanged: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var inputError by rememberSaveable { mutableStateOf(InputValidation.Valid) }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
                errorBorderColor = Orange,
                errorLabelColor = Orange,
                cursorColor = roleUi.mainColor,
                selectionColors = TextSelectionColors(roleUi.mainColor, roleUi.mainColor),
                textColor = White,
                focusedLabelColor = White,
                unfocusedLabelColor = White,
                focusedBorderColor = White,
                unfocusedBorderColor = White
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            value = inputText,
            onValueChange = { newValue ->
                inputError = checkValidRange(newValue, maxValue).also{
                    if(it ==InputValidation.Valid){
                        onValueChanged(newValue)
                    }
                }
            },
            label = {
                Text(
                    text = labelText,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center
                )
            },
            isError = inputError != InputValidation.Valid,
            supportingText = {
                if (inputError != InputValidation.Valid) {
                    Text(
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.fillMaxWidth(),
                        text = getInputValidationError(inputError),
                        color = Orange
                    )
                }
            },
            trailingIcon = {
                if (inputError != InputValidation.Valid)
                    Icon(Icons.Filled.Info, "error", tint = Orange)
            },
        )
    }
}

fun getInputValidationError(inputError: InputValidation) = when (inputError) {
    InputValidation.NotANumber -> "Symbols not allowed"
    InputValidation.WrongRange -> "The number is not on the range"
    InputValidation.Valid -> ""

}


@Composable
fun RoleTitleSection(roleUi: RoleUiData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Divider(thickness = 30.dp, color = Color.Transparent)
        RoleTitleCard(roleUi)
    }
}

@Composable
fun RoleTitleCard(roleUi: RoleUiData) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(roleUi.backgroundColor)
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(15.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .width(40.dp)
                .width(40.dp),
            painter = painterResource(id = roleUi.avatar),
            contentDescription = null
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                color = roleUi.mainColor,
                text = roleUi.title,
                fontSize = 18.sp,
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 25.dp)
            )
            Divider(thickness = 10.dp, color = Color.Transparent)
            Text(
                color = roleUi.mainColor,
                text = roleUi.description,
                fontSize = 14.sp,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            )
        }
    }
}
