/*
 * Copyright 2021 Open source license
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * created by shailshah, 10/27/21
 */
package com.shahshail.android.flipgridchallenge.utils

import android.util.Patterns
import com.shahshail.android.flipgridchallenge.R
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CreateProfileValidator @Inject constructor() {

    enum class CreateProfileErrorFlag(val resId: Int) {
        // first name
        INVALID_FIRST_NAME(R.string.first_name_invalid),

        // email address
        MISSING_EMAIL_ADDRESS(R.string.email_address_required),
        INVALID_EMAIL_ADDRESS(R.string.email_address_invalid),

        // password
        MISSING_PASSWORD(R.string.password_required),
        PASSWORD_MISSING_CAPITAL_LETTER(R.string.password_missing_capital),
        PASSWORD_MISSING_DIGITS(R.string.password_missing_digit),
        PASSWORD_MISSING_SPECIAL_CHARACTER(R.string.password_missing_special_character),

        // web url
        INVALID_WEB_URL(R.string.website_invalid)
    }

    companion object {
        private const val FIRST_NAME_REGEX = "[ .a-zA-Z]+"
        private const val PASSWORD_CAPITAL_PATTERN = "[A-Z]"
        private const val PASSWORD_DIGIT_PATTERN = "[0-9]"
        private const val PASSWORD_SPECIAL_CHAR_PATTER = "[^a-zA-Z0-9 ]"
    }

    fun validateFirstName(string: String?): List<CreateProfileErrorFlag> {
        val list = mutableListOf<CreateProfileErrorFlag>()
        // first name is not required but if the user enter first name then it has to pass regex
        if (string.isNullOrBlank()) {
            return list
        }
        val isValid = string.matches(Regex(FIRST_NAME_REGEX))
        return if (isValid) {
            list
        } else {
            list.add(CreateProfileErrorFlag.INVALID_FIRST_NAME)
            list
        }
    }

    fun validateEmailAddress(string: String?): List<CreateProfileErrorFlag> {
        val flags = mutableListOf<CreateProfileErrorFlag>()
        when {
            string.isNullOrBlank() -> flags.add(CreateProfileErrorFlag.MISSING_EMAIL_ADDRESS)
            !Patterns.EMAIL_ADDRESS.matcher(string).matches() -> flags.add(CreateProfileErrorFlag.INVALID_EMAIL_ADDRESS)
        }
      return flags
    }

    fun validatePassword(string: String?): List<CreateProfileErrorFlag> {
        val flags = mutableListOf<CreateProfileErrorFlag>()
        if (string.isNullOrBlank()) {
            flags.add(CreateProfileErrorFlag.MISSING_PASSWORD)
            return flags
        }

        //Password must contain one capital letter
        if (!string.contains(Regex(PASSWORD_CAPITAL_PATTERN))) {
            flags.add(CreateProfileErrorFlag.PASSWORD_MISSING_CAPITAL_LETTER)
        }

        //Password must contain one digit
        if (!string.contains(Regex(PASSWORD_SPECIAL_CHAR_PATTER))) {
            flags.add(CreateProfileErrorFlag.PASSWORD_MISSING_SPECIAL_CHARACTER)
        }

        //Password must contain one special character
        if (!string.contains(Regex(PASSWORD_DIGIT_PATTERN))) {
            flags.add(CreateProfileErrorFlag.PASSWORD_MISSING_DIGITS)
        }

        return flags
    }

    fun validateWebsite(string: String?): List<CreateProfileErrorFlag> {
        val flag = mutableListOf<CreateProfileErrorFlag>()
        // web url is not required but if the user enter first name then it has to pass regex
        if (string.isNullOrBlank()) {
            return flag
        }
        val isValid = Patterns.WEB_URL.matcher(string).matches()
        if (!isValid) {
            flag.add(CreateProfileErrorFlag.INVALID_WEB_URL)
        }
        return flag
    }
}