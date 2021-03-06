//
//  CHPublishServiceModel.h
//  shenbianapp
//
//  Created by book on 2017/10/15.
//  Copyright © 2017 . All rights reserved.
//


typedef NS_ENUM(NSInteger,CHStageType){//多级分类
    CHStageTypeFirst,
    CHStageTypeSecond,
    CHStageTypeThird
};
#import "CHPublishServiceModel.h"
#import "CHBaseViewCModel.h"

@interface CHPublishServiceModel : CHBaseViewCModel

@property(nonatomic,strong,readonly)RACCommand *getClassifyCommand;


@property(nonatomic,strong,readonly)RACCommand *getTokenComand;

@property(nonatomic,strong,readonly)RACCommand *uploadComand;


@property (copy, nonatomic) NSArray<CHPublishServiceModel*> *dataArray;

@property (copy, nonatomic) NSString *name;

@property (copy, nonatomic) NSString *categoryId;

@property (assign, nonatomic) BOOL isOpen;

@property(assign,nonatomic)CHStageType stageType;

@end
