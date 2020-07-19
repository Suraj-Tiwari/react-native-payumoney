import {sha512} from 'js-sha512';
import {ValidatorInterface} from './interfaces'

export default ({
                    key, amount, email, txnId, productName, firstName, salt,
                    udf1 = "", udf2 = "", udf3 = "", udf4 = "", udf5 = "",
                    udf6 = "", udf7 = "", udf8 = "", udf9 = "", udf10 = ""
                }: ValidatorInterface) => {
    const hashString = `${key}|${txnId}|${amount}|${productName}|${firstName}|${email}|${udf1}|${udf2}|${udf3}|${udf4}|${udf5}|${udf6}|${udf7}|${udf8}|${udf9}|${udf10}|${salt}`
    return sha512(hashString);
}
