package com.shanjupay.transaction.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 支付宝接口对接测试类
 **/
@Slf4j
@Controller
//@RestController//请求方法响应统一json格式
public class PayTestController {

    //沙箱应用APPID
    String APP_ID = "2021000118620802";
    //沙箱应用私钥
    String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCWIa5n7rnlTYpgC7AUXC5n1I9CtFfK40wBkQoPea8dpsuMWcOFOM7TDzD1ImQXO01oak5VGdpRKj4WIOH3o/n2VYwaFipBldGLfFOQhPZbqbFEI2I7vWf0r716SuN5cG4mny8ZbhP56S4ccNpCsLsd7ZExjDPMR30loc0sBYkwomBk3To9HzD+xvYB0Ld2orhjFTB7/VCPYblDYZkwfRiZ/BoJY5cpyt3hgF9uVa0KQq6whhrvnX0HzzTNtYClCrOemLu0BqTzX6g5mFxGJwfF7hhwvbgHqTS8Gonn9+ha3VTEMikPHjVCg03PmtBoJyaMhQwhATELNe2UWOMmlRJLAgMBAAECggEADw/b/oNd1Rp9anths/k3kqUppkiPkkRRiMqzVrAfmHr2auNKkWAMp/IbOEy1+/qwHmyj5TfNxlzVk8TCxuSFnGgiwS8+GAxe1H6pp5MfYDzbEvn1zgaHmm3TNaSzw6g69Nb9k7COgoEZZjMQQqaWbz85VN47CCCX9qGQAv2fMOjBnYXz9/5cexEYFM5n881ocWh4CbmRwn3S5M2EqVXMvxkYP27STtv808GvrozrODzR+D4k3yubkLC7/U9HDnazY5sAN0YNCf3sBudAgeU42HyBHq2sxIUWyZy9UaxTnqNoYo+8lldGS3QbdrXIHZM40V6tWKYbGMA2BrYAFNCRCQKBgQDnvFjmxEZRQOt9f9u5fF4wL2TRYPQajsajBm5bXq2MF+YQnNnUzh1n+Lh/GLSCND+97mNftaM0zYgGxj241KaLF83w9KqFTl1RCT1CSMN4+PRhsMfzU/Hpx/13ZYtvOb+qLnczjkTYa4wx79n5I54ib9noQcKGsEtRyHygU+tqLwKBgQCl2fFTb2l2IdwgeopRkE+Ak+bTIBzks/VS8+4pbFPFKb94VI0eJCTtLaMun9ElB01WfnYqVfQaCeieRzHWnpo00XR6r4qtmeoBV91JDpHmpnRqHjEMr5gr5RBhzgLUgxOA2O2RtX68Pe8Dd/siSEKHGz9gyw8Eus1gdj2RjrH+pQKBgQC9MO8f0ARcl/Tqa/V2VMwM6NSVgGMqP4B6XmjAneZwJp7E11mcPH6TgOMXmJLebkvQA40L+Z36IQa6CSUg/jPOASw4WXfSB6112GYz9HXqEM5r50kHJnStWYJc9QFGWE5bYT4eUDtyuTMnHdvGZEbZdJnh3bY0AkArz9O3jWv4LwKBgC793oO+eIoxM9a8Ab70faI3xdoiKi2e067KULvJ5r5hgs/MXSOiKBhPqwHF5JNySzZrpH2AVyadkhxuna9qxtSaWD9+x3NCvevdgmR1zV8l4KxEm681fY9KWubrYR/nd7o1PLLhUuRxQ+yerThccwUm8kExp7K2XwSq2+0HGmXFAoGBAIxgJ57Pcmix0HO/xnEcHYhADrSDGDyKDCmNdoW/rxI5JKq6/SucPvDGwWv9/1bwp46y7CSWUM5UqCOqcnsbTDmoOz1oO3Lm7ESKydv7/IXQdrmDXUPzrXVGwne4JpCKkuwhYaCeT6uICJWOs4ZFv0kw9l9n9nYQBrQB06Hc4mzd";
    //支付宝公钥
    String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApZsrdXBmfuatiXIQWT84KNAirI9XrGnFNFmXpoZUaU9kC40fFZiamPoCaHASs3gsuJka3gezE+rMoZHPNzHIoWsHnE6kPgv2n2AdBFW7Bo4BL8fkiNj+2iNPfF2Cn9CgpsplTWCBygJCdf6QoRblQRyQzpnMT8aqcid/5cXxUNTsXqtcunCmQyZMqN+KJcgskk46RFIvdgkQMNuiTKJI3Pg+pPRDFXoxpY4YcWiJNDRbzc7UC4jr+sR6qAYkd7mDiMSSK8t8ybtltbfyvIIYnTG87HCl/atMCGYWUcyohjWxduX1PDQP3IxxgKpRSDDujVb8s/Le4LZ4LEZocasUgQIDAQAB";
    //签名算法类型
    String CHARSET = "UTF-8";
    //支付宝接口的网关地址，正式"https://openapi.alipay.com/gateway.do"
    String serverUrl = "https://openapi.alipaydev.com/gateway.do";
    //签名算法类型
    String sign_type = "RSA2";

    @GetMapping("/alipaytest")
    public void alipaytest(HttpServletRequest httpRequest,
                           HttpServletResponse httpResponse) throws ServletException, IOException {
        //构造sdk的客户端对象
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, sign_type); //获得初始化的AlipayClient
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
//        alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
//        alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
                " \"out_trade_no\":\"20150420010101017\"," +
                " \"total_amount\":\"88.88\"," +
                " \"subject\":\"Iphone6 16G\"," +
                " \"product_code\":\"QUICK_WAP_PAY\"" +
                " }");//填充业务参数
        String form = "";
        try {
            //请求支付宝下单接口,发起http请求
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }
}