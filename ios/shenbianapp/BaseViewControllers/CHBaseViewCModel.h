//
//  CHBaseViewCModel.h
//  shenbianapp
//
//  Created by book on 2017/8/27.
//  Copyright © 2017 . All rights reserved.
//

#import <Foundation/Foundation.h>
#import <ReactiveCocoa.h>

@interface CHBaseViewCModel : NSObject<NSCoding>

@property(nonatomic,strong)RACCommand *loadPagedata;

@property(nonatomic,strong) NSDictionary *loadModels;

@end
