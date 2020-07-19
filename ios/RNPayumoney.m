//
//  RNPayumoney.m
//  RNPayumoney
//
//  Copyright © 2019 Suraj Tiwari. All rights reserved.
//

#import "RNPayumoney.h"

@implementation RNPayumoney

RCT_EXPORT_MODULE();

+ (void)hello {}

- (NSDictionary *)constantsToExport
{
  return @{ @"count": @1 };
}

+ (BOOL)requiresMainQueueSetup
{
  return YES;
}

@end
