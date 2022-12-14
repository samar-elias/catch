package com.hudhudit.catchapp.retrofit.data

import com.hudhudit.catchapp.apputils.modules.registration.UserSignIn
import com.hudhudit.catchapp.apputils.modules.registration.catcheeregistration.CatcheeUserSignUp
import com.hudhudit.catchapp.apputils.modules.registration.CheckPhone
import com.hudhudit.catchapp.apputils.modules.registration.catcherregistration.CatcherUserSignUp
import com.hudhudit.catchapp.apputils.modules.registration.catcherregistration.CountryId
import com.hudhudit.catchapp.retrofit.services.NetworkService
import javax.inject.Inject

class RegistrationDataSource @Inject constructor(
    private val networkService: NetworkService
): BaseDataSource() {

    suspend fun getIntro() = getResult{
        networkService.getIntro()
    }


    suspend fun getCountries() = getResult{
        networkService.getCountries()
    }

    suspend fun checkCatcheePhone(checkPhone: CheckPhone) = getResult{
        networkService.checkCatcheePhone(checkPhone)
    }

    suspend fun catcheeCreateAccount(catcheeUserSignUp: CatcheeUserSignUp) = getResult{
        networkService.catcheeCreateAccount(catcheeUserSignUp)
    }

    suspend fun catcheeLogin(catcheeUserSignIn: UserSignIn) = getResult{
        networkService.catcheeSignIn(catcheeUserSignIn)
    }

    suspend fun checkCatcherPhone(checkPhone: CheckPhone) = getResult{
        networkService.checkCatcherPhone(checkPhone)
    }

    suspend fun catcherLogin(catcherUserSignIn: UserSignIn) = getResult{
        networkService.catcherSignIn(catcherUserSignIn)
    }

    suspend fun getCarTypes(countryId: CountryId) = getResult{
        networkService.getCarTypes(countryId)
    }

    suspend fun getCarBrands() = getResult{
        networkService.getCarBrands()
    }

    suspend fun getCarModels(id: String) = getResult{
        networkService.getCarModels(id)
    }

    suspend fun catcherCreateAccount(catcherUserSignUp: CatcherUserSignUp) = getResult{
        networkService.catcherCreateAccount(catcherUserSignUp)
    }
}