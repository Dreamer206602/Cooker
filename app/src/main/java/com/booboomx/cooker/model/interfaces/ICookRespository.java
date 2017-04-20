package com.booboomx.cooker.model.interfaces;

import com.booboomx.cooker.model.bean.CategorySubscriberResultInfo;
import com.booboomx.cooker.model.bean.SearchCookMenuSubscriberResultInfo;

import rx.Observable;

/**
 * Created by booboomx on 17/4/20.
 */

public interface ICookRespository {

     Observable<CategorySubscriberResultInfo> getCategoryQuery();
     Observable<SearchCookMenuSubscriberResultInfo> searchCookMenuByID(final String cid, final int page, final int size);
     Observable<SearchCookMenuSubscriberResultInfo> searchCookMenuByName(final String name, final int page, final int size);

}
