//
//  HeaderView.m
//  Extand TableView
//
//  Created by shenliping on 16/4/14.
//  Copyright © 2016 shenliping. All rights reserved.
//

#import "HeaderView.h"

@interface HeaderView ()


@end;

@implementation HeaderView

-(instancetype)initWithReuseIdentifier:(NSString *)reuseIdentifier{
    
    if ([super initWithReuseIdentifier:reuseIdentifier]) {
        self.contentView.backgroundColor = [UIColor whiteColor];
        [self addSubview:self.nameLabel];
        [self addSubview:self.imageView];
        [self addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapOpen)]];
        self.isOpen = NO;
        if (self.isOpen) {
            _imageView.transform = CGAffineTransformRotate(_imageView.transform, M_PI / 2);
        }
        UIView *line = [UIView new];
        line.backgroundColor = [UIColor colorWithHexColor:@"#ebebeb"];
        [self addSubview:line];
        [line mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(self).offset(15);
            make.right.equalTo(self);
            make.height.mas_equalTo(1);
            make.width.equalTo(self);
            make.bottom.equalTo(self);
        }];
        [self addSubview:self.tailLabel];
        [self.tailLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.right.equalTo(self).offset(-35);
            make.centerY.equalTo(self).offset(-5);
            make.width.mas_equalTo(150);
            make.height.mas_equalTo(40);
        }];
    }
    return self;
}

- (instancetype)initWithFrame:(CGRect)frame IsOpen:(BOOL)isOpen {
    if (self = [super initWithFrame:frame]) {
        
        
    }
    return self;
}

-(void)setSection:(NSInteger)section{
    _section = section;
    self.imageView.image = nil;
    
    if (_section == 0) {
        self.imageView.image = [UIImage imageNamed:@"xiajiantou"];
        
    } else if (section == 1){
        
        
        for (UIView *view in self.subviews) {
            if ([view isKindOfClass:[UITextField class]]) {
                self.priceTextF = (UITextField*)view;
            }
        }
        if (self.priceTextF == nil) {
            
            self.priceTextF = [UITextField new];
            self.priceTextF.placeholder = @"请输入价格";
            self.priceTextF.font = [UIFont systemFontOfSize:13];
            self.priceTextF.textColor = [UIColor colorWithHexColor:@"#8f959c"];
            self.priceTextF.textAlignment = NSTextAlignmentRight;
            self.priceTextF.keyboardType = UIKeyboardTypeDecimalPad;
            [self addSubview:self.priceTextF];
            [self.priceTextF mas_makeConstraints:^(MASConstraintMaker *make) {
                make.right.equalTo(self).offset(-30);
                make.width.mas_equalTo(120);
                make.height.mas_equalTo(30);
                make.centerY.equalTo(self);
            }];
            [self.priceTextF.rac_textSignal subscribeNext:^(NSString *x) {
                if (x && self.servicePriceblock) {
                    self.servicePriceblock(x);
                }
            }];
        }
    } else if (section == 2 || section == 4){
        self.imageView.image = [UIImage imageNamed:@"publish_detail"];
    }
    if (_section == 3) {
        [self addButtons];
    }
}

- (void)addButtons{
    
    NSUInteger index = 0;
    CGFloat btnwidth = 90;
    CGFloat space = 18;
    NSArray *serviceKind = @[@"在线服务",@"到店服务",@"上门服务"];
    for (NSString *name in serviceKind) {
        UIButton *button = [UIButton buttonWithType:(UIButtonTypeCustom)];
        button.tag = index;
        [button setTitle:name forState:(UIControlStateNormal)];
        [button setTitleColor:[UIColor colorWithHexString:@"#8f959c"] forState:(UIControlStateNormal)];
        
        button.titleLabel.font = [UIFont systemFontOfSize:15];
        [button addTarget:self action:@selector(clickServiceKindButton:) forControlEvents:(UIControlEventTouchUpInside)];
        button.backgroundColor = [UIColor colorWithHexString:@"#ebebeb"];
        if (index == 0) {
            button.backgroundColor = [UIColor colorWithHexString:@"#009698"];
            [button setTitleColor:[UIColor colorWithHexString:@"#fefefe"] forState:(UIControlStateNormal)];
        }
        
        button.frame = CGRectMake(15 + index *  (btnwidth + space) , 50, btnwidth, 30);
        button.layer.cornerRadius = 15;
        [self addSubview:button];
        
        index++;
    }
}
- (void)clickServiceKindButton:(UIButton*)button{
    
    for (UIView *view in self.subviews) {
        if ([view isKindOfClass:[UIButton class]]) {
            UIButton *btn = (UIButton*)view;
            btn.backgroundColor = [UIColor colorWithHexString:@"#ebebeb"];
            [btn setTitleColor:[UIColor colorWithHexString:@"#8f959c"] forState:(UIControlStateNormal)];
            
        }
    }
    
    button.backgroundColor = [UIColor colorWithHexString:@"#009698"];
    [button setTitleColor:[UIColor colorWithHexString:@"#fefefe"] forState:(UIControlStateNormal)];
    if (self.serviceTypeblock) {
        self.serviceTypeblock(button.tag);
    }
}

- (UIImageView *)imageView{
    if (_imageView == nil) {
        _imageView = [[UIImageView alloc] initWithFrame:CGRectMake(kScreenWidth - 35, 10, 20, 20)];
        _imageView.image = [UIImage imageNamed:@"右边.png"];
    }
    return _imageView;
}

- (UILabel *)nameLabel{
    if (_nameLabel == nil) {
        _nameLabel = [[UILabel alloc] initWithFrame:CGRectMake(15, 30, 100, self.frame.size.height - 20)];
        _nameLabel.font = [UIFont systemFontOfSize:15];
    }
    return _nameLabel;
}

-(UILabel *)tailLabel{
    if (_tailLabel == nil) {
        _tailLabel = [UILabel new];
        _tailLabel.textAlignment = NSTextAlignmentRight;
        _tailLabel.font = [UIFont systemFontOfSize:13];
        _tailLabel.textColor = [UIColor colorWithHexString:@"#a2a5aa"];
        _tailLabel.numberOfLines = 0;
    }
    
    return _tailLabel;
    
}


- (void)tapOpen{
    if (_isOpen) {
        
        if (self.closeblock) {
            self.closeblock(self.section);
        }
    }else{
        
        if (self.openblock) {
            self.openblock(self.section);
        }
    }
    self.isOpen = !self.isOpen;
}



@end
