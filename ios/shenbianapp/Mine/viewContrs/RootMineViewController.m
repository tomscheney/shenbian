//
//  RootMineViewController.m
//  shenbianapp
//
//  Created by   on 17/7/12.
//  Copyright © 2017  . All rights reserved.
//

#import "RootMineViewController.h"
#import "MHCollectionViewFlowLayout.h"
#import "MHMineHeaderCollectionReusableView.h"
#import "MineCollectionViewCell.h"
#import "CellSPaceCollectionReusableView.h"

#import "CHArticleDetailsViewController.h"
#import "CHServiceDetailsViewController.h"

#import "CHPersonHomeViewController.h"
#import "CHStoreInfoViewController.h"
#import "CHMyOrdersViewController.h"
#import "CHMineModel.h"
#import "CHLoginViewController.h"
#import "CHFeedBackViewController.h"
@interface RootMineViewController ()<UICollectionViewDataSource, UICollectionViewDelegate,UICollectionViewDelegateFlowLayout>
@property (nonatomic,strong)UICollectionView * collectionView;
@property (nonatomic,strong)MHMineHeaderCollectionReusableView *headView;
@property (nonatomic,copy) ClickMyService clickMyService;
@property (nonatomic,copy) ClickMyArticle clickMyArticle;
@property (nonatomic,strong) CHMineModel *viewModel;
@property (nonatomic,strong) NSDictionary *userDataList;
@end

@implementation RootMineViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor lightGrayColor];
    self.automaticallyAdjustsScrollViewInsets = NO;
    MHCollectionViewFlowLayout * stretchyLayout = [[MHCollectionViewFlowLayout alloc] init];
    stretchyLayout.sectionInset = UIEdgeInsetsMake(0, 0, 0, 0);
    stretchyLayout.headerReferenceSize = CGSizeMake(kScreenWidth, 270);
    self.collectionView = [[UICollectionView alloc] initWithFrame:CGRectMake(0, 0, kScreenWidth, kScreenHeight-49) collectionViewLayout:stretchyLayout];
    self.collectionView.backgroundColor = [UIColor whiteColor];
    self.collectionView.alwaysBounceVertical = YES;
    self.collectionView.showsVerticalScrollIndicator = NO;
    self.collectionView.dataSource = self;
    self.collectionView.delegate = self;
    
    [self.view addSubview:self.collectionView];
    [self.collectionView registerNib:[UINib nibWithNibName:@"MHMineHeaderCollectionReusableView" bundle:nil] forSupplementaryViewOfKind:UICollectionElementKindSectionHeader withReuseIdentifier:@"Header"];
    [self.collectionView registerNib:[UINib nibWithNibName:@"CellSPaceCollectionReusableView" bundle:nil] forSupplementaryViewOfKind:UICollectionElementKindSectionHeader withReuseIdentifier:@"cellspace"];
    [self.collectionView registerNib:[UINib nibWithNibName:@"MineCollectionViewCell" bundle:nil] forCellWithReuseIdentifier:@"mine"];
    
    @weakify(self);
    self.clickMyArticle = ^(){
        //        @strongify(self);
        //        CHArticleAndServiceListViewController *articleVC = [[CHArticleAndServiceListViewController alloc]init];
        //        articleVC.title = @"我的文章";
        //        [self.navigationController pushViewController:articleVC animated:YES];
        UIAlertView *alertView = [[UIAlertView alloc]initWithTitle:@"温馨提示" message:@"该功能暂不提供，程序员小哥正在努力开发中，将在后续版本中为您亲情呈现！" delegate:nil cancelButtonTitle:@"知晓" otherButtonTitles:nil];
        [alertView show];
    };
    
    self.clickMyService = ^{
        @strongify(self);
        if (self.userDataList) {
            CHPersonHomeViewController *serviceDetail = [[CHPersonHomeViewController alloc]init];
            serviceDetail.userName = [self.userDataList objectForKey:@"userName"];
            serviceDetail.userIcon = [self.userDataList objectForKey:@"urserIcon"];
            serviceDetail.userId = [self.userDataList objectForKey:@"userId"];
            [self.navigationController pushViewController:serviceDetail animated:YES];
        }
    };
    
}

-(void)bindViewControllerModel{
    self.viewModel = [CHMineModel new];
    NSString *token = [[NSUserDefaults standardUserDefaults] objectForKey:@"token"];
    if (!token)
    {
        CHLoginViewController *login = [CHLoginViewController new];
        [self.tabBarController presentViewController:login animated:YES completion:nil];
    }
}

-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    self.navigationController.navigationBarHidden = YES;
    NSString *token = [[NSUserDefaults standardUserDefaults] objectForKey:@"token"];
    if (token) {
        RACSignal *signal = [self.viewModel.loadPagedata execute:@{@"token":token}];
        [signal subscribeNext:^(id x) {
            NSLog(@"mine:%@",x);
            self.userDataList = [x objectForKey:@"data"];
            self.headView.userDataList = self.userDataList;
            NSIndexPath *indexPath = [NSIndexPath indexPathForRow:0 inSection:0];
            [self.collectionView reloadItemsAtIndexPaths:@[indexPath]];
        } error:^(NSError *error) {
            NSLog(@"mine -error:%@",error);
        }];
    }
    
}

-(void)viewWillDisappear:(BOOL)animated{
    [super viewWillDisappear:animated];
    self.navigationController.navigationBarHidden = NO;
    
}

- (NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView{
    return 2;
}

- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section{
    if (section==0) {
        return 3;
    }else{
        return 2;
    }
    
}

- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout referenceSizeForHeaderInSection:(NSInteger)section{
    if (section== 0) {
        
        return  CGSizeMake(kScreenWidth, 260);
        
    } else {
        
        return CGSizeMake(0, 10);
    }
}

- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout referenceSizeForFooterInSection:(NSInteger)section{
    return CGSizeMake(0, 0);
}

- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath{
    return CGSizeMake(kScreenWidth, 50);
}

- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath{
    MineCollectionViewCell * setCell = [collectionView dequeueReusableCellWithReuseIdentifier:@"mine" forIndexPath:indexPath];
    if (indexPath.section == 0){
        if (indexPath.row == 0){
            setCell.titleName.text = @"我的收入";
            setCell.iconImageView.image = [UIImage imageNamed:@"wd_sr"];
            if (_userDataList) {
                
                setCell.priceLabel.hidden = NO;
                setCell.priceLabel.text = [NSString stringWithFormat:@"￥%@",[self.userDataList objectForKey:@"income"]];
            }
            
        }else if (indexPath.row == 1){
            setCell.titleName.text = @"我的订单";
            setCell.iconImageView.image = [UIImage imageNamed:@"wd_dd"];
            
        }  else if (indexPath.row == 2){
            setCell.titleName.text = @"安全与隐私";
            setCell.iconImageView.image = [UIImage imageNamed:@"wd_aq"];
            
        }
        
    } else if (indexPath.section == 1){
        if (indexPath.row == 0) {
            setCell.titleName.text = @"反馈";
            setCell.iconImageView.image = [UIImage imageNamed:@"wd_fk"];
            
        }else{
            setCell.titleName.text = @"帮助";
            setCell.iconImageView.image = [UIImage imageNamed:@"wd_bz"];
            
        }
    }
    return setCell;
}

- (UICollectionReusableView *)collectionView:(UICollectionView *)collectionView viewForSupplementaryElementOfKind:(NSString *)kind atIndexPath:(NSIndexPath *)indexPath{
    if (kind == UICollectionElementKindSectionHeader) {
        if (indexPath.section == 0) {
            self.headView = [collectionView dequeueReusableSupplementaryViewOfKind:kind withReuseIdentifier:@"Header" forIndexPath:indexPath];
            self.headView.clickMyService = self.clickMyService;
            self.headView.clickMyArticle = self.clickMyArticle;
            //如果是登录用户的默认
            return self.headView;
        }else{
            CellSPaceCollectionReusableView * view = [collectionView dequeueReusableSupplementaryViewOfKind:kind withReuseIdentifier:@"cellspace" forIndexPath:indexPath];
            
            return view;
        }
    }
    return nil;
}

-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath{
    
    if (indexPath.section == 0 && indexPath.row == 2) {
        CHStoreInfoViewController *storeInfoVC = [[CHStoreInfoViewController alloc]init];
        [self.navigationController pushViewController:storeInfoVC animated:YES];
    } else if (indexPath.section == 0 && indexPath.row == 1){
        CHMyOrdersViewController *myOrder = [CHMyOrdersViewController new];
        [self.navigationController pushViewController:myOrder animated:YES];
    } else if (indexPath.section == 1&& indexPath.row==0){
        CHFeedBackViewController *FeedBack = [CHFeedBackViewController new];
        [self.navigationController pushViewController:FeedBack animated:YES];
    } else if (indexPath.section == 1 && indexPath.row == 1){
        UIViewController *agreeVC = [[UIViewController alloc]initWithNibName:@"CHHelpViewController" bundle:nil];
        agreeVC.title = @"用户服务协议";
        [self.navigationController pushViewController:agreeVC animated:YES];
    }
    
}

- (UIStatusBarStyle)preferredStatusBarStyle{
    return UIStatusBarStyleLightContent;
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

@end
