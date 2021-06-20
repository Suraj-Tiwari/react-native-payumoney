import React, {Fragment, useState} from 'react'
import Payumoney, {HashGenerator} from 'react-native-payumoney'
import {View, Button, Text} from 'react-native'

const App = () => {
    const [paymentStatus, setPaymentStatus] = useState("");

    console.log("genetaredHash", HashGenerator({
        key: "QylhKRVd",
        amount: "10.0",
        email: "xyz@gmail.com",
        txnId: "1594976828726",
        productName: "product_info",
        firstName: "firstname",
        salt: "seVTUgzrgE",
    }));

    const pay = async () => {
        setPaymentStatus("");
        const payData = {
            amount: '10.0',
            txnId: '1594976828726',
            productName: 'product_info',
            firstName: 'firstname',
            email: 'xyz@gmail.com',
            phone: '9639999999',
            merchantId: '5960507',
            key: 'QylhKRVd',
            successUrl: 'https://www.payumoney.com/mobileapp/payumoney/success.php',
            failedUrl: 'https://www.payumoney.com/mobileapp/payumoney/failure.php',
            isDebug: true,
            hash:
                '461d4002c1432b3393cf2bfaae7acc4c50601c66568fb49a4a125e060c3bfc0e489290e7c902750d5db3fc8be2f180daf4d534d7b9bef46fa0158a4c8a057b61',
        };
        Payumoney(payData).then((data) => {
            setPaymentStatus(`Success, code: ${data.code}`);
            console.log(data)
        }).catch((e) => {
            setPaymentStatus(`Failed, code: ${e.code}`);
            console.log(e)
        })
    };

    return (
        <Fragment>
            <View
                style={{
                    flex: 1,
                    alignItems: 'center',
                    justifyContent: 'center',
                    height: 200,
                }}
            >
                <Button
                    onPress={() => pay()}
                    title='Pay me :P'
                />
                <Text>{paymentStatus}</Text>
            </View>
        </Fragment>
    )
}

export default App
