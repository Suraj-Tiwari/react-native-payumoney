interface PayuProps {
    amount: string;
    txnId: string;
    productName: string;
    firstName: string;
    email: string;
    phone: string;
    merchantId: string;
    key: string;
    successUrl: string;
    failedUrl: string;
    hash: string;
    isDebug?: boolean;
    udf1?: string;
    udf2?: string;
    udf3?: string;
    udf4?: string;
    udf5?: string;
    udf6?: string;
    udf7?: string;
    udf8?: string;
    udf9?: string;
    udf10?: string;
    salt?: string;
}

const DefaultValue = {
    isDebug: false,
    udf1: '',
    udf2: '',
    udf3: '',
    udf4: '',
    udf5: '',
    udf6: '',
    udf7: '',
    udf8: '',
    udf9: '',
    udf10: '',
};

interface ValidatorInterface {
    amount: string;
    txnId: string;
    productName: string;
    firstName: string;
    email: string;
    key: string;
    salt: string;
    udf1?: string;
    udf2?: string;
    udf3?: string;
    udf4?: string;
    udf5?: string;
    udf6?: string;
    udf7?: string;
    udf8?: string;
    udf9?: string;
    udf10?: string;
}

export {
    DefaultValue,
    PayuProps,
    ValidatorInterface
}
