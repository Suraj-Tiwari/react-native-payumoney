//
//  RNPayumoney.m
//  RNPayumoney
//
//  Copyright © 2019 Suraj Tiwari. All rights reserved.
//

#import "RNPayumoney.h"
#import <PlugNPlay/PlugNPlay.h>
#import <React/RCTUtils.h>

@implementation RNPayumoney

RCT_EXPORT_MODULE();

- (NSArray<NSString *> *)supportedEvents
{
    return @[@"PAYU_PAYMENT_SUCCESS",@"PAYU_PAYMENT_FAILED"];
}


RCT_EXPORT_METHOD(pay :(NSString *)data) {
    
    NSData *jsonData = [data dataUsingEncoding:NSUTF8StringEncoding];
    NSError *error;
    NSDictionary *payuData = (NSDictionary *)[NSJSONSerialization JSONObjectWithData:jsonData options:0 error:&error];
    
    PUMTxnParam *txnParam= [[PUMTxnParam alloc] init];
    txnParam.phone = payuData[@"phone"];
    txnParam.email = payuData[@"email"];
    txnParam.amount = payuData[@"amount"];
    txnParam.environment = [payuData[@"isDebug"] boolValue]?PUMEnvironmentTest:PUMEnvironmentProduction;
    txnParam.firstname = payuData[@"firstName"];
    txnParam.key = payuData[@"key"];
    txnParam.merchantid = payuData[@"merchantId"];
    txnParam.txnID = payuData[@"txnId"];
    txnParam.surl = payuData[@"successUrl"];
    txnParam.furl = payuData[@"failedUrl"];
    txnParam.productInfo = payuData[@"productName"];
    txnParam.hashValue = payuData[@"hash"];
    txnParam.udf1 = payuData[@"udf1"];
    txnParam.udf2 = payuData[@"udf2"];
    txnParam.udf3 = payuData[@"udf3"];
    txnParam.udf4 = payuData[@"udf4"];
    txnParam.udf5 = payuData[@"udf5"];
    txnParam.udf6 = payuData[@"udf6"];
    txnParam.udf7 = payuData[@"udf7"];
    txnParam.udf8 = payuData[@"udf8"];
    txnParam.udf9 = payuData[@"udf9"];
    txnParam.udf10 = payuData[@"udf10"];
    
    dispatch_sync(dispatch_get_main_queue(), ^{
        [PlugNPlay setDisableCompletionScreen:YES];
        [PlugNPlay presentPaymentViewControllerWithTxnParams:txnParam
                                            onViewController:RCTPresentedViewController()
                                         withCompletionBlock:^(NSDictionary *paymentResponse, NSError *error, id extraParam) {
            if (error) {
                [self sendEventWithName:@"PAYU_PAYMENT_FAILED" body:@{@"success": @false,@"code":@0}];
            } else {
                NSString *message;
                if ([paymentResponse objectForKey:@"result"] && [[paymentResponse objectForKey:@"result"] isKindOfClass:[NSDictionary class]] ) {
                    message = [[paymentResponse objectForKey:@"result"] valueForKey:@"error_Message"];
                    if ([message isEqual:[NSNull null]] || [message length] == 0 || [message isEqualToString:@"No Error"]) {
                        [self sendEventWithName:@"PAYU_PAYMENT_SUCCESS" body:@{@"response":paymentResponse,@"code":@1}];
                    }else {
                        [self sendEventWithName:@"PAYU_PAYMENT_FAILED" body:@{@"success":@false,@"code":@-1}];
                    }
                }
                else {
                    [self sendEventWithName:@"PAYU_PAYMENT_FAILED" body:@{@"success":@false,@"code":@-1}];
                }
            }
        }];
    });
}

@end
