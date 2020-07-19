package com.surajtiwari.reactnativepayumoney

data class PayumoneyModal(
        val key: String,
        val merchantId: String,
        val amount: String,
        val txnId: String,
        val phone: String,
        val productName: String,
        val firstName: String,
        val email: String,
        val successUrl: String,
        val failedUrl: String,
        val hash: String,
        val isDebug: Boolean = false,
        val udf1: String,
        val udf2: String,
        val udf3: String,
        val udf4: String,
        val udf5: String,
        val udf6: String,
        val udf7: String,
        val udf8: String,
        val udf9: String,
        val udf10: String
)