package com.surajtiwari.reactnativepayumoney

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import com.facebook.react.bridge.ActivityEventListener
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.google.gson.Gson
import com.payumoney.core.PayUmoneySdkInitializer.PaymentParam
import com.payumoney.core.entity.TransactionResponse
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager
import com.payumoney.sdkui.ui.utils.ResultModel


class RNPayumoneyModule internal constructor(private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), ActivityEventListener {
    init {
        reactApplicationContext.addActivityEventListener(this)
    }

    private val PAYU_PAYMENT_SUCCESS = "PAYU_PAYMENT_SUCCESS";
    private val PAYU_PAYMENT_FAILED = "PAYU_PAYMENT_FAILED";

    override fun getName(): String {
        return "RNPayumoney"
    }

    @ReactMethod
    fun pay(data: String) {

        val payuData = Gson().fromJson(data, PayumoneyModal::class.java)

        val builder = PaymentParam.Builder()

        builder.setAmount(payuData.amount)
                .setKey(payuData.key)
                .setMerchantId(payuData.merchantId)
                .setTxnId(payuData.txnId)
                .setPhone(payuData.phone)
                .setProductName(payuData.productName)
                .setFirstName(payuData.firstName)
                .setEmail(payuData.email)
                .setsUrl(payuData.successUrl)
                .setfUrl(payuData.failedUrl)
                .setIsDebug(payuData.isDebug)
                .setUdf1(payuData.udf1)
                .setUdf2(payuData.udf2)
                .setUdf3(payuData.udf3)
                .setUdf4(payuData.udf4)
                .setUdf5(payuData.udf5)
                .setUdf6(payuData.udf6)
                .setUdf7(payuData.udf7)
                .setUdf8(payuData.udf8)
                .setUdf9(payuData.udf9)
                .setUdf10(payuData.udf10)

        val paymentParam = builder.build()
        paymentParam.setMerchantHash(payuData.hash)
        dispatchInAppropriateThread(Runnable {
            PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, currentActivity, R.style.AppTheme_default, true);
        })
    }

    override fun onNewIntent(intent: Intent) {
    }

    override fun onActivityResult(activity: Activity?, requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
            val transactionResponse: TransactionResponse? = data.getParcelableExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE)
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                val payuResponse: String = transactionResponse.getPayuResponse()
                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    sendEvent(PAYU_PAYMENT_SUCCESS, "{\"response\":$payuResponse,\"code\":1}")
                } else {
                    sendEvent(PAYU_PAYMENT_FAILED, "{\"success\":false,\"code\":-1}")
                }
            } else {
                sendEvent(PAYU_PAYMENT_FAILED, "{\"success\":false,\"code\":-1}")
            }
        } else {
            sendEvent(PAYU_PAYMENT_FAILED, "{\"success\":false,\"code\":0}")
        }
    }

    private fun sendEvent(eventName: String, params: String) {
        val context = reactApplicationContext
        context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java).emit(eventName, params)
    }

    protected fun dispatchInAppropriateThread(runnable: Runnable?) {
        if (runnable == null) {
            return
        }
        reactContext.runOnUiQueueThread(runnable)
    }

}