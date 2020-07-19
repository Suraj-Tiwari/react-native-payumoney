import {NativeEventEmitter, NativeModules} from 'react-native'
import HashGenerator from './hashGenerator';
import {DefaultValue, PayuProps} from './interfaces';

const RNPayumoney = NativeModules.RNPayumoney;
const RNEvent = new NativeEventEmitter(RNPayumoney);

const removeSubscriptions = () => {
    RNEvent.removeAllListeners('PAYU_PAYMENT_SUCCESS');
    RNEvent.removeAllListeners('PAYU_PAYMENT_FAILED');
};

const Payumoney = (paymentData: PayuProps) => {
    paymentData = {...DefaultValue, ...paymentData};
    if (paymentData.isDebug) {
        console.log(paymentData);
    }
    return new Promise((resolve, reject) => {
        RNEvent.addListener('PAYU_PAYMENT_SUCCESS', (data) => {
            if (typeof data !== 'object') {
                data = JSON.parse(data)
            }
            resolve({...data, success: true});
            removeSubscriptions();
        });
        RNEvent.addListener('PAYU_PAYMENT_FAILED', (data) => {
            if (typeof data !== 'object') {
                data = JSON.parse(data)
            }
            reject(data);
            removeSubscriptions();
        });
        RNPayumoney.pay(JSON.stringify(paymentData))
    });
};

export default Payumoney;
export {
    HashGenerator
}
