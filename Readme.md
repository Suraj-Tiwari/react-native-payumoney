# react-native-payumoney
___


```sh
$ npm install https://github.com/Suraj-Tiwari/react-native-payumoney --save
```
## Installation

### Add to project

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


