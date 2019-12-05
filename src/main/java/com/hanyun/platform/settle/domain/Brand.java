package com.hanyun.platform.settle.domain;

import com.hanyun.platform.settle.domain.BrandInfo;
import com.hanyun.platform.settle.domain.BrandUser;

/**
 * Created by jack on 2017/6/19.
 */
public class Brand {

    private BrandInfo brandInfo;
    private BrandUser brandUser;

    public BrandInfo getBrandInfo() {
        return brandInfo;
    }

    public void setBrandInfo(BrandInfo brandInfo) {
        this.brandInfo = brandInfo;
    }

    public BrandUser getBrandUser() {
        return brandUser;
    }

    public void setBrandUser(BrandUser brandUser) {
        this.brandUser = brandUser;
    }
}
