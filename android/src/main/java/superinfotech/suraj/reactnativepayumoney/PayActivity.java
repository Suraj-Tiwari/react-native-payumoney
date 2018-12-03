package superinfotech.suraj.reactnativepayumoney;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.react.bridge.Promise;


import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.ResultModel;

/**
 * Created by surajtiwari on 26/05/18.
 */

public class PayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.loader_layout);

        Double amount = extras.getDouble("amount");
        String txnId = extras.getString("txnId");
        String productName = extras.getString("productName");
        String firstName = extras.getString("firstName");
        String email = extras.getString("email");
        String phone = extras.getString("phone");
        String merchantId = extras.getString("merchantId");
        String merchantKey = extras.getString("merchantKey");
        String merchantSUrl = extras.getString("merchantSUrl");
        String merchantFUrl = extras.getString("merchantFUrl");
        Boolean merchantSandbox = extras.getBoolean("merchantSandbox");
        String hash = extras.getString("hash");
        makePayment(amount, txnId, productName, firstName, email, phone, merchantId, merchantKey, merchantSUrl, merchantFUrl, merchantSandbox, hash);
    }

    private void makePayment(final Double amount,
                             final String txnId,
                             final String productName,
                             final String firstName,
                             final String email,
                             final String phone,
                             final String merchantId,
                             final String merchantKey,
                             final String merchantSUrl,
                             final String merchantFUrl,
                             final Boolean merchantSandbox,
                             final String hash) {

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();
        builder.setAmount(amount)
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl(merchantSUrl)
                .setfUrl(merchantFUrl)
                .setIsDebug(merchantSandbox)
                .setKey(merchantKey)
                .setMerchantId(merchantId);
        PayUmoneySdkInitializer.PaymentParam paymentParam = builder.build();
        paymentParam.setMerchantHash(hash);
        PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, PayActivity.this, R.style.AppTheme_default, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Intent intent = new Intent();

        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE);
            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    intent.putExtra("success", true);
                } else {
                    intent.putExtra("success", false);
                }

                String payuResponse = transactionResponse.getPayuResponse();
                intent.putExtra("payuResponse", payuResponse);

            } else if (resultModel != null && resultModel.getError() != null) {
                intent.putExtra("success", false);
            } else {
                intent.putExtra("success", false);
            }
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}
