package superinfotech.suraj.reactnativepayumoney;

/**
 * Created by surajtiwari on 25/05/18.
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.ActivityEventListener;

import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.core.PayUmoneySdkInitializer;

import java.util.HashMap;
import java.util.Map;

public class PayumoneyModule extends ReactContextBaseJavaModule implements ActivityEventListener {
    public PayumoneyModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        reactApplicationContext.addActivityEventListener(this);
    }

    private static final int PAYUMONEY_RESULT = 0;

    @Override
    public String getName() {
        return "payumoney";
    }

    @ReactMethod
    public void makePayment(final String amount,
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
        ReactApplicationContext context = getReactApplicationContext();
        Activity currentActivity = getCurrentActivity();
        Intent intent = new Intent(currentActivity, PayActivity.class);
        intent.putExtra("amount", Double.parseDouble(amount));
        intent.putExtra("txnId", txnId);
        intent.putExtra("productName", productName);
        intent.putExtra("firstName", firstName);
        intent.putExtra("email", email);
        intent.putExtra("phone", phone);
        intent.putExtra("merchantId", merchantId);
        intent.putExtra("merchantKey", merchantKey);
        intent.putExtra("merchantSUrl", merchantSUrl);
        intent.putExtra("merchantFUrl", merchantFUrl);
        intent.putExtra("merchantSandbox", merchantSandbox);
        intent.putExtra("hash", hash);
        currentActivity.startActivityForResult(intent, PAYUMONEY_RESULT);
    }

    public void onNewIntent(Intent intent) {
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        onActivityResult(requestCode, resultCode, data);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYUMONEY_RESULT) {
            Boolean success = data.getBooleanExtra("success", false);
            if (success) {
                String payuResponse = data.getStringExtra("payuResponse");
                sendEvent("PAYU_PAYMENT_SUCCESS", payuResponse);
            } else {
                sendEvent("PAYU_PAYMENT_FAILED", "{error:true}");
            }
        }
    }

    private void sendEvent(String eventName, String params) {
        ReactApplicationContext context = getReactApplicationContext();
        context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

}
