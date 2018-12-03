//
//  payumoney.m
//  payumoney
//
//  Created by Suraj Tiwari  on 03/12/18.
//  Copyright © 2018 Suraj Tiwari . All rights reserved.
//

#import "payumoney.h"
#import <CommonCrypto/CommonDigest.h>
#import <React/RCTUtils.h>
#import <React/RCTLog.h>

@implementation payumoney

RCT_EXPORT_MODULE();

- (NSArray<NSString *> *)supportedEvents
{
    return @[@"PAYU_PAYMENT_SUCCESS",@"PAYU_PAYMENT_FAILED"];
}


RCT_EXPORT_METHOD(makePayment
                  :(NSString *)amount
                  :(NSString *)txid
                  :(NSString *)productId
                  :(NSString *)name
                  :(NSString *)email
                  :(NSString *)phone
                  :(NSString *)merchantId
                  :(NSString *)key
                  :(NSString *)surl
                  :(NSString *)furl
                  :(BOOL *)sandbox
                  :(NSString *)hash
                  ) {
    
    PUMTxnParam *txnParam= [[PUMTxnParam alloc] init];
    txnParam.phone = phone;
    txnParam.email = email;
    txnParam.amount = amount;
    txnParam.environment = sandbox?PUMEnvironmentTest:PUMEnvironmentTest;
    txnParam.firstname = name;
    txnParam.key = key;
    txnParam.merchantid = merchantId;
    txnParam.txnID = txid;
    txnParam.surl = surl;
    txnParam.furl = furl;
    txnParam.productInfo = productId;
    txnParam.hashValue =  [self getHashForPaymentParams:txnParam];
    
    dispatch_sync(dispatch_get_main_queue(), ^{
        [PlugNPlay presentPaymentViewControllerWithTxnParams:txnParam
                   onViewController:RCTPresentedViewController()
                   withCompletionBlock:^(NSDictionary *paymentResponse, NSError *error, id extraParam) {
                       if (error) {
                           [self sendEventWithName:@"PAYU_PAYMENT_FAILED" body:@{@"success": @false}];
                       } else {
                           NSString *message;
                           if ([paymentResponse objectForKey:@"result"] && [[paymentResponse objectForKey:@"result"] isKindOfClass:[NSDictionary class]] ) {
                               message = [[paymentResponse objectForKey:@"result"] valueForKey:@"error_Message"];
                               if ([message isEqual:[NSNull null]] || [message length] == 0 || [message isEqualToString:@"No Error"]) {
                                   message = [[paymentResponse objectForKey:@"result"] valueForKey:@"status"];
                               }
                           }
                           else {
                               message = [paymentResponse valueForKey:@"status"];
                           }
                           [self sendEventWithName:@"PAYU_PAYMENT_SUCCESS" body:@{@"success": @true,@"data":message}];
                       }
                   }];
    });
}

//TODO: REMOVE LOCAL HASH GENERATOR
-(NSString*)getHashForPaymentParams:(PUMTxnParam*)txnParam {
    NSString *salt = @"Je7q3652";
    NSString *hashSequence = [NSString stringWithFormat:@"%@|%@|%@|%@|%@|%@|%@|%@|%@|%@|%@|%@|%@|%@|%@|%@|%@",txnParam.key,txnParam.txnID,txnParam.amount,txnParam.productInfo,txnParam.firstname,txnParam.email,txnParam.udf1,txnParam.udf2,txnParam.udf3,txnParam.udf4,txnParam.udf5,txnParam.udf6,txnParam.udf7,txnParam.udf8,txnParam.udf9,txnParam.udf10, salt];
    NSString *hash = [[[[[self createSHA512:hashSequence] description]stringByReplacingOccurrencesOfString:@"<" withString:@""]stringByReplacingOccurrencesOfString:@">" withString:@""]stringByReplacingOccurrencesOfString:@" " withString:@""];
    return hash;
}

- (NSString*) createSHA512:(NSString *)source {
    const char *s = [source cStringUsingEncoding:NSASCIIStringEncoding];
    NSData *keyData = [NSData dataWithBytes:s length:strlen(s)];
    uint8_t digest[CC_SHA512_DIGEST_LENGTH] = {0};
    CC_SHA512(keyData.bytes, (CC_LONG)keyData.length, digest);
    NSData *output = [NSData dataWithBytes:digest length:CC_SHA512_DIGEST_LENGTH];
    NSString *hash =  [[[[output description]stringByReplacingOccurrencesOfString:@"<" withString:@""]stringByReplacingOccurrencesOfString:@">" withString:@""]stringByReplacingOccurrencesOfString:@" " withString:@""];
    return hash;
}
@end
