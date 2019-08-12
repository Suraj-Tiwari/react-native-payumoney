# react-native-payumoney

<img src='https://img.shields.io/badge/license-MIT-blue.svg' />  <a href="https://www.npmjs.com/package/react-native-payumoney"><img alt="npm dowloads" src="https://img.shields.io/npm/dm/react-native-payumoney.svg"/></a> <a href="https://www.npmjs.com/package/react-native-payumoney"><img alt="npm version" src="https://badge.fury.io/js/react-native-payumoney.svg"/></a> [![Build Status](https://travis-ci.org/Suraj-Tiwari/react-native-payumoney.svg?branch=master)](https://travis-ci.org/Suraj-Tiwari/react-native-payumoney) [![Greenkeeper badge](https://badges.greenkeeper.io/Suraj-Tiwari/react-native-payumoney.svg)](https://greenkeeper.io/)
___
### Installation

Run the following on terminal from your project directory:

```bash
$ npm i react-native-payumoney --save
```

### Automatic installation

```bash
$ react-native link react-native-payumoney
```

### Steps

1. Import PayuMoney module to your component:
    ```js
    import PayuMoney from 'react-native-payumoney';
    ```

2. Call `PayuMoney.pay()` method with the payment `options`. The method
returns a **JS Promise** where `then` part corresponds to a successful payment
and the `catch` part corresponds to payment failure.
```js
let amount = 99.9;
let txid = new Date().getTime()+"";
let productId = "product101";
let name = "asdf";
let email = "hello@world.com";
let phone = "1231231231";
let surl = "https://www.example.com/payu-validate.php"; //can be diffrennt for Succes
let furl = "https://www.example.com/payu-validate.php"; //can be diffrennt for Failed
let id = "XXXXX"; //Your Merchant ID here
let key = "XXXXX"; //Your Key Here
let sandbox = true; //Make sure to set false on production or you will get error
fetch('https://www.example.com/payu-hash.php', {
    method: 'POST',
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
    },
    body: JSON.stringify({
        key: key,
        txnid: txid,
        amount: amount,
        productinfo: productId,
        firstname: name,
        email: email
    }),
})
    .then((response) => response.text())
    .then((hash) => {
        let options = {
            amount: amount,
            txid: txid ,
            productId: productId,
            name: name,
            email: email,
            phone: phone,
            id: id,
            key: key,
            surl: surl,
            furl: furl,
            sandbox: sandbox,
            hash: hash
        };
        console.log(options);
        PayuMoney.pay(options).then((d) => {
            console.log(d); // WIll get a Success response with verification hash
        }).catch(e => {
            console.log(e); //In case of failture
        });
    })
```

Server side function to get Hash Key

```php
function makeHash($key, $txnid, $amount, $productinfo, $firstname, $email){
    $salt = "XXXXXX"; //Please change the value with the live salt for production environment

    $payhash_str = $key . '|' . checkNull($txnid) . '|' . checkNull($amount) . '|' . checkNull($productinfo) . '|' . checkNull($firstname) . '|' . checkNull($email) . '|||||||||||' . $salt;

    $hash = strtolower(hash('sha512', $payhash_str));
    return $hash;
}

function checkNull($value)
{
    if ($value == null) {
        return '';
    } else {
        return $value;
    }
}

```

### Troubleshooting
> Known Issue

TypeError:Cannot read property 'makePayment' of undefined question

Make sure you have linked library `react-native link react-native-payumoney`
See [Issue #2](https://github.com/Suraj-Tiwari/react-native-payumoney/issues/2#issuecomment-409661804)


```json
{ "success": error }
```

This is very common error, when your server side hash is calculated in-correctly or
when trying to use **Web Merchant KEY + SALT** on sandbox in Android  
Please use Following KEY, SALT, MERCHANT ID for sandbox usage

```js
  MID : 4934580
  Key : rjQUPktU
  Salt : e5iIg1jwi8
```

Below is the test card details for doing a test transaction in the testing mode.

```js
  Card No - 5123456789012346
  Expiry - 05/2020
  CVV - 123
  Name - Test
```

## Running example

### 1. Install dependencies

```bash
$ cd ./example && npm install
```

### 2. Run it on Android

```bash
$ cd ./example && npm run android
```
[version-badge]: https://img.shields.io/npm/v/react-native-payumoney.svg?style=flat-square
[package]: https://www.npmjs.com/package/react-native-payumoney

<!--<a href="https://circleci.com/gh/Suraj-Tiwari/react-native-payumoney"><img src="https://circleci.com/gh/Suraj-Tiwari/react-native-payumoney.svg?style=shield" alt="build"></a>-->
