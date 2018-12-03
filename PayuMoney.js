'use strict';
import {NativeModules, NativeEventEmitter} from 'react-native';

const PayuPay = NativeModules.payumoney;
const PayuEvent = new NativeEventEmitter(PayuPay);

const removeSubscriptions = () => {
    PayuEvent.removeAllListeners('PAYU_PAYMENT_SUCCESS');
    PayuEvent.removeAllListeners('PAYU_PAYMENT_FAILED');
};

export default class PayuMoney {
    static pay(options) {
        let data = {
            amount: options.amount,
            txid: options.txid,
            productId: options.productId,
            name: options.name,
            email: options.email,
            phone: options.phone,
            id: options.id,
            key: options.key,
            surl: options.surl,
            furl: options.furl,
            sandbox: options.sandbox,
            hash: options.hash
        };
        return new Promise(function (resolve, reject) {
            PayuEvent.addListener('PAYU_PAYMENT_SUCCESS', (data) => {
                resolve(data);
                removeSubscriptions();
            });
            PayuEvent.addListener('PAYU_PAYMENT_FAILED', (data) => {
                reject({success: false});
                removeSubscriptions();
            });
            PayuPay.makePayment(data.amount, data.txid, data.productId, data.name, data.email, data.phone, data.id, data.key, data.surl, data.furl, data.sandbox, data.hash);
        });
    }
}


