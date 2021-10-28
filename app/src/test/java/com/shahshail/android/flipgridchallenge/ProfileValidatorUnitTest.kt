package com.shahshail.android.flipgridchallenge

import com.shahshail.android.flipgridchallenge.utils.CreateProfileValidator
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Unit test coverage for CreateProfileValidator
 */
class ProfileValidatorUnitTest {

    private lateinit var profileValidator: CreateProfileValidator

    @Before
    fun setup() {
        profileValidator = CreateProfileValidator()
    }

    @Test
    fun testValidFirstName() {
        val flags = profileValidator.validateFirstName("shail")
        assertTrue(flags.isEmpty())
    }

    @Test
    fun testFirstNameNotRequired() {
        val flags = profileValidator.validateFirstName("")
        assertTrue(flags.isEmpty())
    }

    @Test
    fun testInvalidFirstName() {
        val flags = profileValidator.validateFirstName("28978u10@")
        assertEquals(flags.size, 1)
        assertTrue(flags.contains(CreateProfileValidator.CreateProfileErrorFlag.INVALID_FIRST_NAME))
    }

    @Test
    fun testEmailAddressRequired() {
        val emptyEmailFlags = profileValidator.validateEmailAddress("")
        val nullEmailFlags = profileValidator.validateEmailAddress(null)
        // empty
        assertEquals(emptyEmailFlags.size, 1)
        assertTrue(emptyEmailFlags.first() == CreateProfileValidator.CreateProfileErrorFlag.MISSING_EMAIL_ADDRESS)

        //null
        assertEquals(nullEmailFlags.size, 1)
        assertTrue(emptyEmailFlags.first() == CreateProfileValidator.CreateProfileErrorFlag.MISSING_EMAIL_ADDRESS)
    }

    @Test
    fun testValidEmailAddress() {
        val errorFlags = profileValidator.validateEmailAddress("shahshail9898@gmail.com")
        assertTrue(errorFlags.isEmpty())
    }

    @Test
    fun testInvalidEmailAddress() {
        val errorFlags = profileValidator.validateEmailAddress("shahshail9898")
        assertEquals(errorFlags.size, 1)
        assertTrue(errorFlags.first() == CreateProfileValidator.CreateProfileErrorFlag.INVALID_EMAIL_ADDRESS)
    }

    // password

    @Test
    fun testPasswordRequired() {
        val emptyErrorFlags = profileValidator.validatePassword("")
        val nullErrorFlags = profileValidator.validatePassword(null)

        assertEquals(emptyErrorFlags.size, 1)
        assertEquals(emptyErrorFlags.first(), CreateProfileValidator.CreateProfileErrorFlag.MISSING_PASSWORD)

        assertEquals(nullErrorFlags.size, 1)
        assertEquals(nullErrorFlags.first(), CreateProfileValidator.CreateProfileErrorFlag.MISSING_PASSWORD)
    }

    @Test
    fun testValidPassword() {
        val errorFlags = profileValidator.validatePassword("Shail9999@")
        assertTrue(errorFlags.isEmpty())
    }

    @Test
    fun testPasswordMissingUppercase() {
        val errorFlags = profileValidator.validatePassword("shail9999@")
        assertEquals(errorFlags.size, 1)
        assertTrue(errorFlags.first() == CreateProfileValidator.CreateProfileErrorFlag.PASSWORD_MISSING_CAPITAL_LETTER)
    }

    @Test
    fun testPasswordMissingSpecialCharacter() {
        val errorFlags = profileValidator.validatePassword("Shail9999")
        assertEquals(errorFlags.size, 1)
        assertTrue(errorFlags.first() == CreateProfileValidator.CreateProfileErrorFlag.PASSWORD_MISSING_SPECIAL_CHARACTER)
    }

    @Test
    fun testPasswordMissingDigit() {
        val errorFlags = profileValidator.validatePassword("Shail@")
        assertEquals(errorFlags.size, 1)
        assertTrue(errorFlags.first() == CreateProfileValidator.CreateProfileErrorFlag.PASSWORD_MISSING_DIGITS)
    }

    // website
    @Test
    fun testInvalidWebsiteUrl() {
        val errorFlags = profileValidator.validateWebsite("shahshail9898")
        assertEquals(errorFlags.size, 1)
        assertTrue(errorFlags.first() == CreateProfileValidator.CreateProfileErrorFlag.INVALID_WEB_URL)
    }

    @Test
    fun testValidWebsiteUrl() {
        val errorFlags = profileValidator.validateWebsite("www.shahshail9898.com")
        assertTrue(errorFlags.isEmpty())
    }

    @Test
    fun testWebsiteNotRequired() {
        val emptyErrorFlag = profileValidator.validateWebsite("")
        val nullErrorFlag = profileValidator.validateWebsite(null)

        assertTrue(emptyErrorFlag.isEmpty())
        assertTrue(nullErrorFlag.isEmpty())
    }

}