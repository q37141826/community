package com.qixiu.intelligentcommunity.engine;


import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.mvp.beans.store.goods.GoodsDetailBean;
import com.qixiu.intelligentcommunity.utlis.SplitStringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class GoodsDetailSpecEngine {
    /**
     * 返回商品详情选择规格的bean信息，以及选择规格的id
     *
     * @param datas
     * @return
     */
    public static Map<Integer, Object> getSpecInfo(List<GoodsDetailBean.GoodsDetailInfos.SpecGoodsPriceBean> datas) {
        Map<Integer, Object> dataObj = new HashMap<>();
        List<GoodsDetailBean.GoodsDetailInfos.SpecGoodsPriceBean> specGoodsPriceBeanList = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < datas.size(); i++) {
            GoodsDetailBean.GoodsDetailInfos.SpecGoodsPriceBean specGoodsPriceBean = datas.get(i);
            if (!specGoodsPriceBean.isSelectSpecLine()) {
                specGoodsPriceBeanList.add(specGoodsPriceBean);
            }
            for (int j = 0; j < specGoodsPriceBean.getSpec().size(); j++) {
                GoodsDetailBean.GoodsDetailInfos.SpecGoodsPriceBean.SpecBean specBean = datas.get(i).getSpec().get(j);

                if (specBean.isSelect()) {
                    stringBuffer.append(specBean.getId());
                    if (i < datas.size() - 1) {
                        stringBuffer.append(StringConstants.STRING_UNDERLINE);
                    }

                }

            }

        }

        dataObj.put(0, SplitStringUtils.cutStringEnd(stringBuffer.toString(), StringConstants.STRING_UNDERLINE));
        dataObj.put(1, specGoodsPriceBeanList);
        return dataObj;
    }
}
