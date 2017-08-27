//
//  RootHeaderViewController.m
//  shenbianapp
//
//  Created by 杨绍智 on 17/7/12.
//  Copyright © 2017年 杨绍智. All rights reserved.
//

#import "RootHeaderViewController.h"
#import "HomeNavView.h"
#import "RootObjectModel.h"
#import "CustomDiviceView.h"
#import <MAMapKit/MAMapKit.h>
#import <AMapFoundationKit/AMapFoundationKit.h>
@interface RootHeaderViewController ()<HomeNavViewdelegate,CustomDiviceViewdelegate>

@property (nonatomic,strong)HomeNavView *NavView;
@property (nonatomic,strong)CustomDiviceView * headItemView;
@property (nonatomic,strong)NSMutableArray * headItemArray;
@property (nonatomic,copy)NSString * selectItemOneName;
@property (nonatomic,copy)NSString * selectItemTwoName;
@property (nonatomic,assign)NSInteger selectIndex;
@property (nonatomic,strong)UIView * mapView;
@property (nonatomic,strong)UIView * infoCar;

@property (nonatomic,strong) RootObjectModel *viewCModel;

@end

@implementation RootHeaderViewController
@dynamic viewCModel;
- (void)viewDidLoad {
    [super viewDidLoad];
    
//    self.navBarView.hidden = YES;
//    [AMapServices sharedServices].enableHTTPS = YES;
//    [self.view addSubview:self.mapView];
//     MAMapView *apimapView = [[MAMapView alloc] initWithFrame:self.mapView.bounds];
//    [apimapView setZoomLevel:15 atPivot:self.mapView.center animated:YES];
//    [self.mapView addSubview:apimapView];
//    
//    apimapView.showsUserLocation = YES;
//    apimapView.userTrackingMode = MAUserTrackingModeFollow;
    
}

-(void)bindViewControllerModel{
    [super bindViewControllerModel];
    
    self.viewCModel = [[RootObjectModel alloc]init];
    
    NSDictionary *param = @{@"latitude":@"23.1230",@"longitude":@"36.023"};
   
    [self.viewCModel.loadPagedata execute:param];

    [RACObserve(self.viewCModel, loadModels) subscribeNext:^(id x) {
        self.NavView.quikSearchList = x;
    }];
    
}

-(void)setupViews{

    [self.view addSubview:self.NavView];

}

- (void)HomeNavViewClickIndex:(NSInteger)index{
    self.selectIndex = index;
    if (index==0) {
        [self.headItemView setCustomDiviceViewByArray:self.headItemArray[index] andCureentObject:self.selectItemOneName];
    }else{
       [self.headItemView setCustomDiviceViewByArray:self.headItemArray[index] andCureentObject:self.selectItemTwoName];
    }
}

- (void)CustomDiviceViewClickIndex:(NSInteger)index{
    

}

- (UIView*)infoCar{
    if (!_infoCar) {
        _infoCar = [[UIView alloc]initWithFrame:CGRectMake(0, 64+70, kScreenWidth, kScreenHeight-64-70-49)];
        _infoCar.backgroundColor = [UIColor whiteColor];
    }
    return _infoCar;
}

- (UIView*)mapView{
    if (!_mapView) {
        _mapView = [[UIView alloc]initWithFrame:CGRectMake(0, 64, kScreenWidth, kScreenHeight-64-49)];
        _mapView.backgroundColor = [UIColor lightGrayColor];
    }
    return _mapView;
}

- (CustomDiviceView*)headItemView{
    if (!_headItemView) {
        _headItemView = [[CustomDiviceView alloc]initWithFrame:CGRectMake(0, 64, kScreenWidth, 70)];
        _headItemView.delegate = self;
    }
    return _headItemView;
}

- (HomeNavView*)NavView{
    if (!_NavView) {
        _NavView = [[HomeNavView alloc]initWithFrame:CGRectMake(0, 24, kScreenWidth, 109)];
        _NavView.delegate = self;
    }
    return _NavView;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
