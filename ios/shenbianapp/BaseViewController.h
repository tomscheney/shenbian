//
//  BaseViewController.h
//  shenbianapp
//
//  Created by 杨绍智 on 17/7/12.
//  Copyright © 2017年 杨绍智. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MHBaseNavigationBar.h"
#import "CHBaseViewCModel.h"
@interface BaseViewController : UIViewController
@property (nonatomic,strong)MHBaseNavigationBar *navBarView;

@property(nonatomic,strong) CHBaseViewCModel *viewCModel;

- (void)bindViewControllerModel;

- (void)setupViews;

@end
