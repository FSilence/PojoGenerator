/*
 * Copyright (c) 2017-present, wlh
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.wlh.beangenerator;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElementFactory;
import com.wlh.beangenerator.beans.FieldInfo;
import com.wlh.utils.FormatUtils;
import com.wlh.utils.PsiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weilh on 2018/2/6.
 */
public class BeanClassGenerator {

    Project mProject;
    PsiDirectory mPsiDirectory;

    Config mConfig;

    PsiElementFactory mElementFactory;

    public BeanClassGenerator(Project project, PsiDirectory psiDirectory) {
        this.mProject = project;
        this.mPsiDirectory = psiDirectory;
        this.mElementFactory = JavaPsiFacade.getInstance(mProject).getElementFactory();
    }

    public void generate(String text, Config config) {
        this.mConfig = config;
        List<FieldInfo> fieldInfos = parseFieldInfos(text);
        PsiClass psiClass = mElementFactory.createClass(mConfig.className);
        new WriteCommandAction.Simple(mProject) {
            @Override
            protected void run() throws Throwable {
                for (FieldInfo fieldInfo : fieldInfos) {
                    String fieldStr = String.format("/**\n* %s\n*/\npublic %s %s;", fieldInfo.describe,
                            fieldInfo.type, fieldInfo.name);
                    psiClass.add(mElementFactory.createFieldFromText(fieldStr, psiClass));
                }
                mPsiDirectory.add(psiClass);
            }
        }.execute();
    }

    /**
     *
     */

    private List<FieldInfo> parseFieldInfos(String text) {
        String[] lines = text.split(mConfig.lineSplit);
        List<FieldInfo> fieldInfos = new ArrayList<>();
        for (String line : lines) {
            if (!StringUtil.isEmpty(line)) {
                fieldInfos.add(parseField(line));
            }
        }
        return fieldInfos;
    }

    private FieldInfo parseField(String line) {
        String[] strArr = line.split(mConfig.blockSplit);
        FieldInfo fieldInfo = new FieldInfo();
        fieldInfo.name = strArr[mConfig.namePosition - 1].trim();
        fieldInfo.type = strArr[mConfig.typePosition - 1].trim();
        //处理注释中的换行
        if (mConfig.describePosition - 1 < strArr.length) {
            fieldInfo.describe = strArr[mConfig.describePosition - 1].trim().replaceAll("\\n", "\n* ");
        }
        if (mConfig.firstToLow) {
            fieldInfo.name = PsiUtils.firstToLower(fieldInfo.name);
        }
        if (mConfig.forceToAb) {
            fieldInfo.name = PsiUtils.formatFieldNameToaB(fieldInfo.name);
        }
        fieldInfo.type = FormatUtils.formatType(fieldInfo.type);
        return fieldInfo;
    }

//    public static void main(String[] args) {
//        BeanClassGenerator generator = new BeanClassGenerator(null, null);
//        generator.mConfig = new Config();
//        generator.mConfig.describePosition = 4;
//        generator.mConfig.forceToAb = true;
//        List<FieldInfo> fieldInfos = generator.parseFieldInfos("app_id\tString\tN\tGoogle Play商品ID\n" +
//                "orderId\tString\tN\tgoogle交易订单标识\n" +
//                "packageName\tString\tN\t发起购买的应用软件包\n" +
//                "purchaseTime\tLong\tN\t商品的购买时间（从新纪年[1970 年 1 月 1 日]开始计算的毫秒数）\n" +
//                "purchaseState\tint\tN\t订单的购买状态\n" +
//                "developerPayload\tString\tN\t开发者指定的字符串，包含订单的补充信息\n" +
//                "purchaseToken\tString\tN\t用于对给定商品和用户对进行唯一标识的令牌\n" +
//                "ip\tString\tY\t用户IP\n" +
//                "amount\tNumber\tN\t数量\n" +
//                "platform\tString\tN\t平台Code\n" +
//                "cid\tString\tN\t渠道,主站:afbe8fd3d73448c9\n" +
//                "version\tString\tN\t版本号，1.0\n" +
//                "fc\tString\tY\t入口参数\n" +
//                "fv\tString\tY\t外站入口参数\n" +
//                "testMode\tString\tY\t沙盒模式标识，1：测试\n" +
//                "lang\tString\tY\tzh_CN - 简体中文，zh_TW - 繁体中文（缺省值)\n" +
//                "app_lm\tString\tY\tcn - 标准大陆模式，tw - 台湾模式（缺省值)。统一小写\n" +
//                "P00001 \tString\tN\tPassport返回的Cookie值\n" +
//                "device_id\tString\tY\t设备ID\n" +
//                "fr_version\tString\tN\t移动端版本标识\n" +
//                "appType\tString\tN\tApp的类型 pps的app：PPS 爱奇艺的app：IQIYI\n" +
//                "app_version\tString\tY\t客户端版本号 例如：9.2.0 \n" +
//                "dev_os\tString\tY\t操作系统版本\n" +
//                "dev_ua\tString\tY\t机型\n" +
//                "net_sts\tString\tY\t网络状态");
//
//        for (FieldInfo fieldInfo : fieldInfos) {
//
//            System.out.println(fieldInfo);
//        }
//    }


}
