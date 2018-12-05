package com.ly.mc.handler;

import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.ly.common.constant.CommonConstant;
import com.ly.common.util.Assert;
import com.ly.common.util.template.MobileMsgTemplate;
import com.ly.mc.config.SmsTencentPropertiesConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * 腾讯短信服务处理
 *
 * @auther Administrator liyang
 * @Create 2018/8/18
 */
@Slf4j
@Component(CommonConstant.TENCENT_SMS)
public class SmsTencentMessageHandler extends AbstractMessageHandler {

    @Autowired
    private SmsTencentPropertiesConfig smsAliyunPropertiesConfig;

//    @Autowired
//    private SysSmsService sysSmsService;

    @Override
    public void check(MobileMsgTemplate mobileMsgTemplate) {
        Assert.isBlank(mobileMsgTemplate.getMobile(), "手机号不能为空");
        Assert.isBlank(mobileMsgTemplate.getContext(), "验证码不能为空");
    }

    @Override
    public boolean process(MobileMsgTemplate mobileMsgTemplate) {
        //假设短信模板 id 为 123，模板内容为：测试短信，{1}，{2}，{3}，上学。
        SmsSingleSender sender = null;
        try {
            sender = new SmsSingleSender(Integer.parseInt(smsAliyunPropertiesConfig.getAccessKey()),smsAliyunPropertiesConfig.getSecretKey());
            ArrayList<String> params = new ArrayList();


            JSONObject contextJson = JSONObject.parseObject(mobileMsgTemplate.getContext());
            Map<String, Object> map  = contextJson.getInnerMap();
            Collection values = map.values();

            for(Object v:values){
                params.add(v+"");
            }

            SmsSingleSenderResult result = sender.sendWithParam("86", mobileMsgTemplate.getMobile(),
                    Integer.parseInt(smsAliyunPropertiesConfig.getChannels().get(mobileMsgTemplate.getTemplate())), params, "", "", "");

            log.error("短信发送状态{}", result.toString());
//            sysSmsService.insert(mobileMsgTemplate,result.result == 0);
            return result.result == 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void fail(MobileMsgTemplate mobileMsgTemplate) {
        log.error("短信发送失败 -> 网关：{} -> 手机号：{}", mobileMsgTemplate.getType(), mobileMsgTemplate.getMobile());
    }
}
