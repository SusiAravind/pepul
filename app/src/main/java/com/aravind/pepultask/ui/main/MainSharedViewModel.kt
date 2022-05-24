package com.aravind.pepultask.ui.main

import com.aravind.pepultask.ui.base.BaseViewModel
import com.aravind.pepultask.utils.network.NetworkHelper
import com.aravind.pepultask.utils.rx.CoroutineDispatchers

class MainSharedViewModel(
    coroutineDispatchers: CoroutineDispatchers,
    networkHelper: NetworkHelper
) : BaseViewModel(coroutineDispatchers, networkHelper) {

    override fun onCreate() {


    }



}