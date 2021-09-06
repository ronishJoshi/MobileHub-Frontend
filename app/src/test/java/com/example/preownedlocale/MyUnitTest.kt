package com.example.mobilehub
////

////
import com.example.mobilehub.api.ServiceBuilder
import com.example.mobilehub.entity.User
import com.example.mobilehub.ui.LoginActivity
import com.example.mobilehub.userRepository.ProductRepository
import com.example.mobilehub.userRepository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class MyUnitTest {


    private lateinit var userRepository: UserRepository


    @Test
    fun logintest() = runBlocking {
        val userRepository = UserRepository()

        val user = User(
                username = "ronish@gmail.com",
                password = "ronish2345"

        )
        val response = userRepository.checkUser("ronish@gmail.com", "ronish2345")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)

    }


    @Test
    fun signuptest() = runBlocking {
        val userRepository = UserRepository()

        val user = User(
                fname = "jay",
                lname = "pandey",
                username = "prao",
                password = "prah12"
        )
        val response = userRepository.registerUser(user)
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)

    }

    @Test
    fun deleteproduct() = runBlocking {
        val userRepository = UserRepository()

        val user = User(
                username = "prakashsilwal",
                password = "prakash12"

        )
        val response = userRepository.checkUser("prakashsilwal", "prakash12")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)

    }

    @Test
    fun addProduct() = runBlocking {
        val userRepository = UserRepository()

        val user = User(
                username = "ronish@gmail.com",
                password = "ronish2345"

        )
        val response = userRepository.checkUser("prakashsilwal", "prakash12")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)

    }

    @Test
    fun delproduct() = runBlocking {
        val userRepository = UserRepository()

        val user = User(
                username = "prakashsilwal",
                password = "prakash12"

        )
        val response = userRepository.checkUser("", "prakash12")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)

    }










}





