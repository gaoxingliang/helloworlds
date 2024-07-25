import cn.hutool.core.io.*;
import cn.sichuancredit.apigateway.encryption.*;
import com.alibaba.fastjson.*;
import kong.unirest.HttpResponse;
import kong.unirest.*;
import org.apache.http.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

/**
 * 数据服务测试用例
 */
public class TestCases {
    private static String endpoint = "http://devicbc.sichuancredit.cn:88";
    private static String username, password, token, privKey, pubKey;

    static {
        Unirest.config().automaticRetries(false);
    }

    @BeforeAll
    static void auth() throws Exception {
        // 修改为提供的授权信息
        // 开发环境：
        username = readFile("D:\\code\\helloworlds\\apisix-hmac-clienttest\\test\\sichuanzhengxin\\test-username.txt");
        password = readFile("D:\\code\\helloworlds\\apisix-hmac-clienttest\\test\\sichuanzhengxin\\test-password.txt");
        privKey = readFile("D:\\code\\helloworlds\\apisix-hmac-clienttest\\test\\sichuanzhengxin\\test-user-priv.txt");
        pubKey = readFile("D:\\code\\helloworlds\\apisix-hmac-clienttest\\test\\sichuanzhengxin\\test-sczx-pub.txt");
        if (false) {
            // 生产环境
            endpoint = "https://api.tianfucredit.cn";
            username = readFile("D:\\code\\helloworlds\\apisix-hmac-clienttest\\prod\\test-username.txt");
            password = readFile("D:\\code\\helloworlds\\apisix-hmac-clienttest\\prod\\test-password.txt");
            privKey = readFile("D:\\code\\helloworlds\\apisix-hmac-clienttest\\prod\\test-user-priv.txt");
            pubKey = readFile("D:\\code\\helloworlds\\apisix-hmac-clienttest\\prod\\test-sczx-pub.txt");
        }

        String rbacToken = getRbacToken(endpoint + "/auth/token", "data", username, password);
        System.out.println(rbacToken);
        token = rbacToken;
    }

    @Test
    void testSocialSecurity() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "唐山市丰南区众鑫铝业有限公司");
        getRequest("/v2/enterprises/ent-operate/modules/socialsecuritys", params);
    }

    @Test
    void testFinances() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "北京九十度生活科技有限公司");
        getRequest("/v2/enterprises/ent-operate/modules/finances", params);
    }

    @Test
    void testDoublecheckups() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "汉阴县文海检验检测有限公司");
        getRequest("/v2/enterprises/ent-operate/modules/doublecheckups", params);
    }

    @Test
    void testGuarantes() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "好利来（中国）电子科技股份有限公司");
        getRequest("/v2/enterprises/ent-operate/modules/guarantees", params);
    }

    @Test
    void testLandDeals() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "北京京东方生命科技有限公司");
        getRequest("/v2/enterprises/customized/modules/landdeals", params);
    }

    @Test
    void testCorpBenefit() {
        Map<String, Object> params = new HashMap<>();
        params.put("creditCode", "91330106673976542L");
        getRequest("/v2/enterprises/ent-ba/modules/corp-beneficiaries", params);
    }

    @Test
    void testCorpShareholders() {
        Map<String, Object> params = new HashMap<>();
        params.put("creditCode", "91330106673976542L");
        getRequest("/v2/enterprises/ent-ba/modules/collaborative-shareholders", params);
    }

    @Test
    void testEntCases() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "河北鹏豪建筑工程有限公司");
        getRequest("/v2/enterprises/justices/modules/cases", params);
    }

    @Test
    void testPersonCases() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "张三");
        params.put("idCard", "500235199001011234");
        getRequest("/v2/enterprises/ent-ba/modules/altinfo", params);
    }

    @Test
    void testMainInvestments() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "成都正态铠甲科技有限公司");
        params.put("name", "罗应儒");
        getRequest("/v2/enterprises/ent-ba/modules/maininvestments", params);
    }

    @Test
    void testAltInfo() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "上海羽昼文化传播有限公司");
        getRequest("/v2/enterprises/ent-ba/modules/altinfo", params);
    }

    @Test
    void testBranches() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "小米科技有限责任公司");
        getRequest("/v2/enterprises/ent-ba/modules/branches", params);
    }

    @Test
    void testEquityPledge() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "中基宁波集团股份有限公司");
        getRequest("/v2/enterprises/ent-ba/modules/equitypledge", params);
    }

    @Test
    void testLogout() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "东莞市盘英服装贸易有限公司");
        getRequest("/v2/enterprises/ent-ba/modules/logout", params);
    }

    @Test
    void testEquityPenetration() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "成都成电光信科技股份有限公司");
        getRequest("/v2/enterprises/ent-ba/modules/equitypenetration", params);
    }

    @Test
    void testSpotCheck() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "福建宇桐机械有限公司");
        getRequest("/v2/enterprises/ent-operate/modules/spotcheck", params);
    }

    @Test
    void testMortgage() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "清远市清新区浸潭镇大冲水电站");
        getRequest("/v2/enterprises/ent-operate/modules/mortgage", params);
    }

    @Test
    void testSeriousIllegal() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "深圳市福田区回春养生贸易商行");
        getRequest("/v2/enterprises/ent-operate/modules/seriousillegal", params);
    }

    @Test
    void testKPropertypledge() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "四川廖老九餐饮管理有限公司");
        getRequest("/v2/enterprises/ent-operate/modules/kpropertypledge", params);
    }

    @Test
    void testLegalpersoncheck() {
        Map<String, Object> params = new HashMap<>();
        params.put("creditCode", "91510100MA63807M9M");
        params.put("idCard", "510124199101010101");
        getRequest("/v2/enterprises/ent-ba/modules/legalpersoncheck", params);
    }

    @Test
    void testCertificates() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "企查查科技股份有限公司");
        getRequest("/v2/enterprises/ent-operate/modules/certificates", params);
    }

    @Test
    void testTaxCreditLevels() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "福建宇桐机械有限公司");
        getRequest("/v2/enterprises/taxinfo/modules/taxcreditlevels", params);
    }

    @Test
    void testCapitalType() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "上海兆瓦投资咨询有限公司");
        getRequest("/v2/enterprises/ent-ba/modules/capitaltype", params);
    }

    @Test
    void testTaxPayerInfos() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "上海兆瓦投资咨询有限公司");
        getRequest("/v2/enterprises/taxinfo/modules/taxpayerinfos", params);
    }

    @Test
    void testJudicialFreezes() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "上海兆瓦投资咨询有限公司");
        getRequest("/v2/enterprises/justices/modules/judicialfreezes", params);
    }

    @Test
    void testLogoutRecords() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "太原市星雅石材有限公司");
        getRequest("/v2/enterprises/justices/modules/logoutrecords", params);
    }

    @Test
    void testNotices() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "吴江市远征彩钢板活动房厂");
        getRequest("/v2/enterprises/justices/modules/notices", params);
    }

    @Test
    void testOperationAbnormals() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "乐视致新电子科技（天津）有限公司");
        getRequest("/v2/enterprises/ent-operate/modules/operation-abnormals", params);
    }

    @Test
    void testSuppliers() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "北京金泰得生物科技股份有限公司");
        getRequest("/v2/enterprises/industrial/modules/suppliers", params);
    }

    @Test
    void testDistributors() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "北京清畅电力技术股份有限公司");
        getRequest("/v2/enterprises/industrial/modules/distributors", params);
    }

    @Test
    void testCoreMembers() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "北京九十度生活科技有限公司");
        getRequest("/v2/enterprises/customized/modules/coremembers", params);
    }

    @Test
    void testCreditLevels() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "福建宇桐机械有限公司");
        getRequest("/v2/enterprises/taxinfo/modules/taxcreditlevels", params);
    }

    @Test
    void testTaxpayerinfo() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "苏州科达科技股份有限公司");
        getRequest("/v2/enterprises/taxinfo/modules/taxpayerinfos", params);
    }

    @Test
    void testEntEmploy() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "广州香雪空港跨境物联有限公司");
        getRequest("/v2/enterprises/ent-ba/modules/entemploy", params);
    }

    @Test
    void testLandTenures() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "广州香雪空港跨境物联有限公司");
        params.put("type", "TYPE_LAND_ANNOUNCEMENT");
        getRequest("/v2/enterprises/ent-operate/modules/landtenures", params);
    }

    @Test
    void testBidding() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "成都成电光信科技股份有限公司");
        getRequest("/v2/enterprises/ent-operate/modules/bidding", params);
    }

    @Test
    void testBankrupts() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "重庆鼎翌建筑设备租赁有限公司");
        getRequest("/v2/enterprises/justices/modules/bankrupts", params);
    }

    @Test
    void testSeizures() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "小米科技");
        params.put("pageSize", "10");
        params.put("pageNumber", "1");
        getRequest("/v2/enterprises/justices/modules/seizures", params);
    }

    @Test
    void testCaseFlows() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "成都香岸餐饮管理有限公司");
        params.put("pageSize", "10");
        params.put("pageNumber", "1");
        getRequest("/v2/enterprises/justices/modules/caseflows", params);
    }

    @Test
    void testInvestments() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "北京启明星辰信息安全技术有限公司");
        getRequest("/v2/enterprises/ent-ba/modules/investments", params);
    }

    @Test
    void testDomains() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "北京启明星辰信息安全技术有限公司");
        getRequest("/v2/enterprises/ent-operate/modules/domains", params);
    }

    @Test
    void testFinalCases() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "乐视控股（北京）有限公司");
        getRequest("/v2/enterprises/justices/modules/finalcases", params);
    }

    @Test
    void testPatents() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "福建乐旺佳贸易有限公司");
        getRequest("/v2/enterprises/ent-operate/modules/patents", params);
    }

    @Test
    void testTrademarks() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "福建乐旺佳贸易有限公司");
        getRequest("/v2/enterprises/ent-operate/modules/trademarks", params);
    }

    @Test
    void testAdministrativeLicensing() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "阿里巴巴（中国）有限公司");
        getRequest("/v2/enterprises/ent-operate/modules/licensing", params);
    }

    @Test
    void testJudgements() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "福建乐旺佳贸易有限公司");
        getRequest("/v2/enterprises/justices/modules/judgements", params);
    }

    @Test
    void testAnnouncements() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "福建乐旺佳贸易有限公司");
        getRequest("/v2/enterprises/justices/modules/announcements", params);
    }

    @Test
    void testCourtSessions() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "珠海华发投资发展有限公司");
        getRequest("/v2/enterprises/justices/modules/sessions", params);
    }


    @Test
    void testSoftwareCopyrights() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "成都创新房地产开发有限公司");
        getRequest("/v2/enterprises/ent-operate/modules/software-copyrights", params);
    }

    @Test
    void testCopyrights() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "成都创新房地产开发有限公司");
        getRequest("/v2/enterprises/ent-operate/modules/copyrights", params);
    }

    @Test
    void testSeizure() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "小米科技");
        params.put("pageNumber", "1");
        params.put("pageSize", "10");
        getRequest("/v2/enterprises/justices/modules/seizures", params);
    }

    @Test
    void testAnnualReport() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "成都创新房地产开发有限公司");
        getRequest("/v2/enterprises/ent-ba/modules/annualreports", params);
    }

    @Test
    void testEmploymentrelid() {
        Map<String, Object> params = new HashMap<>();
        params.put("idCard", "500235199101010101");
        params.put("name", "高兴");
        getRequest("/v2/enterprises/ent-ba/modules/entemployrelid", params);
    }

    @Test
    void testTaxRelated() {
        Map<String, Object> params = new HashMap<>();
        params.put("taxpyrIdNo", "9156CU30AM60911D1E");
        getRequest("/v2/enterprises/taxinfo/modules/basicinfo", params);
        getRequest("/v2/enterprises/taxinfo/modules/declaration", params);
        getRequest("/v2/enterprises/taxinfo/modules/financialstatements", params);
        getRequest("/v2/enterprises/taxinfo/modules/interaction", params);
        getRequest("/v2/enterprises/taxinfo/modules/abnormal", params);
        getRequest("/v2/enterprises/taxinfo/modules/shareholders", params);
        getRequest("/v2/enterprises/taxinfo/modules/taxrate", params);
        getRequest("/v2/enterprises/taxinfo/modules/incomeinvoice", params);
        getRequest("/v2/enterprises/taxinfo/modules/saleinvoice", params);
    }

    @Test
    void testSocialAssurance() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "成都创新房地产开发有限公司");
        getRequest("/v2/enterprises/ent-operate/modules/social-insurance", params);
    }

    @Test
    void testSearch() {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", "四川征信");
        getRequest("/v2/enterprises/customized/modules/search", params);
    }

    @Test
    void testAdminPunishment() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "廊坊市翡翠华庭房地产开发有限公司");
        getRequest("/v2/enterprises/ent-operate/modules/administrative-punishments", params);
    }

    @Test
    void testActualController() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "廊坊市翡翠华庭房地产开发有限公司");
        getRequest("/v2/enterprises/ent-ba/modules/actual-controller", params);
    }

    @Test
    void testShareholders() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "廊坊市翡翠华庭房地产开发有限公司");
        getRequest("/v2/enterprises/ent-ba/modules/shareholders", params);
    }

    @Test
    void testPersons() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "廊坊市翡翠华庭房地产开发有限公司");
        getRequest("/v2/enterprises/ent-ba/modules/persons", params);
    }

    @Test
    void testOpenCase() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "廊坊市翡翠华庭房地产开发有限公司");
        getRequest("/v2/enterprises/justices/modules/opencase", params);
    }

    @Test
    void testCorportateinfo() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "廊坊市翡翠华庭房地产开发有限公司");
        getRequest("/v2/enterprises/ent-ba/modules/corporateinfo", params);
    }

    @Test
    void testBasicInfo() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "廊坊市翡翠华庭房地产开发有限公司");
        getRequest("/v2/enterprises/ent-ba/modules/basicinfo", params);
    }

    @Test
    void testDiscreditExecutions() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "廊坊市翡翠华庭房地产开发有限公司");
        getRequest("/v2/enterprises/justices/modules/discredit-enforcements", params);
    }

    @Test
    void testAuctions() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "福建乐旺佳贸易有限公司");
        getRequest("/v2/enterprises/justices/modules/auctions", params);
    }

    @Test
    void testExecutions() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "乐视致新电子科技（天津）有限公司");
        getRequest("/v2/enterprises/justices/modules/enforcements", params);
    }

    @Test
    void testRestrictedConsumers() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "乐视致新电子科技（天津）有限公司");
        getRequest("/v2/enterprises/justices/modules/restricted-consumers", params);
    }

    @Test
    void testTaxArrears() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "拉德餐饮（上海）有限公司");
        getRequest("/v2/enterprises/taxinfo/modules/arrears", params);
    }

    @Test
    void testTaxCases() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "福建乐旺佳贸易有限公司");
        getRequest("/v2/enterprises/taxinfo/modules/taxcases", params);
    }

    @Test
    void testBenefitPerson() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "四川征信有限公司");
        getRequest("/v2/enterprises/ent-ba/modules/beneficiaries", params);
    }

    @Test
    void testScale() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "阿里巴巴（中国）有限公司");
        getRequest("/v2/enterprises/ent-ba/modules/scale", params);
    }


    // 辅助方法 ---------------------------------------------
    public static void getRequest(String path, Map<String, Object> params) {
        // GET请求示例
        System.out.println("Get 请求开始-----------" + endpoint + path + " params:" + params);
        HttpResponse<String> response = Unirest.get(endpoint + path)
                .header(HttpHeaders.AUTHORIZATION, token)
                .queryString(params)
                .asString();
        System.out.println("收到回复" + response.getBody() + " headers:" + response.getHeaders() + " status:" + response.getStatus());
        if (response.getStatus() == 200) {
            // 解密
            EncryptedData responseEncryptedData = JSONObject.parseObject(response.getBody(), EncryptedData.class);
            String sm4Key2 = MySmUtil.sm2Decrypt(responseEncryptedData.getEncryptKey(), privKey);
            // 国密Sm4解密返回的数据
            String dataStr = MySmUtil.sm4Decrypt(responseEncryptedData.getData(), sm4Key2);
            System.out.println("Sm解密数据为: " + dataStr);
            System.out.println("数据状态：" + responseEncryptedData.getDataStatus());
        } else {
            System.err.println("Non 200 return code");
            System.exit(1);
        }
    }


    public static String getRbacToken(String url, String appid, String u, String p) throws Exception {
        System.out.println(url);
        JSONObject d = new JSONObject();
        d.put("appid", appid);
        d.put("username", u);
        d.put("password", p);

        HttpResponse<String> response = Unirest.post(url)
                .header("Content-Type", "application/json")
                .body(d.toString())
                .asString();
        if (response.getStatus() != 200) {
            throw new Exception("Invalid rbac response : " + response.getBody());
        }

        com.alibaba.fastjson2.JSONObject resp = com.alibaba.fastjson2.JSONObject.parse(response.getBody());

        System.out.println(response.getBody());
        String t = resp.getString("rbac_token");
        if (t == null) {
            throw new IllegalArgumentException("No token found in rbac response");
        }

        return t;
    }

    public static String readFile(String file) {
        return FileUtil.readString(new File(file), Charset.forName("UTF-8")).trim();
    }
}
