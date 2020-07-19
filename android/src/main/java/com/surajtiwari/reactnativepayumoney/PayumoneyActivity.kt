package com.surajtiwari.reactnativepayumoney

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.payumoney.core.PayUmoneySdkInitializer.PaymentParam
import com.payumoney.core.entity.TransactionResponse
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager
import com.payumoney.sdkui.ui.utils.ResultModel

class PayumoneyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.loader_layout)

        val data =  intent.extras!!.getString("data")
        val payuData = Gson().fromJson(data, PayumoneyModal::class.java)
        val builder = PaymentParam.Builder()

        builder.setAmount(payuData.amount)
                .setTxnId(payuData.txnId)
                .setPhone(payuData.phone)
                .setProductName(payuData.productName)
                .setFirstName(payuData.firstName)
                .setEmail(payuData.email)
                .setsUrl(payuData.successUrl)
                .setfUrl(payuData.failedUrl)
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
                .setIsDebug(payuData.isDebug)
                .setKey(payuData.key)
                .setMerchantId(payuData.merchantId)
        val paymentParam = builder.build()
        paymentParam.setMerchantHash(payuData.hash)
        PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, this, R.style.AppTheme_default, false)
    }

    fun onActivityResult(activity: Activity?, requestCode: Int, resultCode: Int, data: Intent?) {
                                                                                                                                                                        //        super.onActivityResult(requestCode, resultCode, data)
        val intent = Intent()
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == Activity.RESULT_OK && data != null) {
            val transactionResponse: TransactionResponse = data.getParcelableExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE)
            val resultModel: ResultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT)
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if (transactionResponse.transactionStatus == TransactionResponse.TransactionStatus.SUCCESSFUL) {
                    intent.putExtra("success", true)
                } else {
                    intent.putExtra("success", false)
                }
                val payuResponse = transactionResponse.getPayuResponse()
                intent.putExtra("payuResponse", payuResponse)
            } else if (resultModel.error != null) {
                intent.putExtra("success", false)
            } else {
                intent.putExtra("success", false)
            }
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}