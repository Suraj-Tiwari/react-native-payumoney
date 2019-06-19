import React, {Component} from 'react';
import {StyleSheet, Text, View, TouchableOpacity, Dimensions} from 'react-native';
import PayuMoney from 'react-native-payumoney';

let {width, height} = Dimensions.get('window');

type Props = {};
export default class App extends Component<Props> {
    render() {
        return (
            <View style={styles.mainContainer}>
                <View style={styles.instruction}>
                    <Text style={styles.buttonText}>React Native PayuMoney</Text>
                    <Text style={styles.instructionText}>Install Instruction</Text>
                    <Text style={styles.instructionText}>npm install Suraj-Tiwari/react-native-payumoney --save</Text>
                    <Text style={styles.instructionText}>react-native link react-native-payumoney</Text>

                </View>
                <View style={styles.container}>
                    <TouchableOpacity style={styles.button} onPress={() => this._makePay()}>
                        <Text style={styles.buttonText}>Make Payment</Text>
                    </TouchableOpacity>
                </View>
            </View>
        );
    }

    _makePay() {
        let options = {
            amount: "10.0",
            txid: "123123123" ,
            productId: "test",
            name: "Name",
            email: "test@gmail.com",
            phone: "8826343434",
            id: "4914106",
            key: "mdyCKV",
            surl: "https://www.payumoney.com/mobileapp/payumoney/success.php",
            furl: "https://www.payumoney.com/mobileapp/payumoney/failure.php",
            sandbox: true,
            hash: "f1c2830db04e388433db0857ca00a9f918ee64b070e45a4c2525a9f4b7df602d6d82f2b187bca7a595c5c7f15ee29349c50f11c5a99d0c29a735423486852b7b"
        };
        PayuMoney.pay(options).then((d) => {
            console.log(d);
        }).catch(e => {
            console.log(e);
        });
    }

    // Below is the test card details for doing a test transaction:
    // Card No - 5123456789012346
    // Expiry - 05/20
    // CVV - 123
    // Name - Test
}

const styles = StyleSheet.create({
    mainContainer: {
        flex: 1,
        alignItems: 'center',
        backgroundColor: '#d3dcdd',
    },
    container: {
        flex: 1,
        justifyContent: 'flex-end',
        alignItems: 'center',
        paddingBottom: 50
    },
    instruction: {
        justifyContent: 'center',
        alignItems: 'center',
        flex: 1
    },
    instructionText: {
        color: "#000000",
        fontSize: 14
    },
    button: {
        width: width - 50,
        borderRadius: 5,
        backgroundColor: "#fff",
        elevation: 2,
        justifyContent: 'center',
        alignItems: 'center',
        margin: 2,
        padding: 8
    },
    buttonText: {
        color: "#ff0006",
        fontSize: 20
    },
});
