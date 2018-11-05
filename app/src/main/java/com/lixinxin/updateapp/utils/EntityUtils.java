package com.lixinxin.updateapp.utils;

/**
 * Created by android on 2018/1/12.
 */

public class EntityUtils {

    public static String getComponentTitle(String type) {

        switch (type) {
            case "full_name":  //姓名
                return "姓名";
            case "id_card":  //身份证
                return "身份证";
            case "phone":  //手机
                return "手机";
            case "email":  //邮箱
                return "邮箱";
            case "appellation":  //称谓
                return "称谓";
            case "sex":  //性别
                return "性别";
            case "birthday":  //生日
                return "生日";
            case "location":  //所在地
                return "所在地";
            case "address":  //地址
                return "地址";
            case "head_portrait":  //头像
                return "头像";
            case "company":  //公司
                return "公司";
            case "department":  //部门
                return "部门";
            case "position":  //职位
                return "职位";
            case "website":  //网址
                return "网址";
            case "landline":  //座机
                return "座机";
            case "fax":  //fax
                return "传真";
            case "qq":  //QQ
                return "QQ";
            case "weibo":  //微博
                return "微博";
            case "wechat":  //微信
                return "微信";
            case "id_cart":  //身份证
                return "身份证";
            case "number":  //数字
                return "数字";
            case "region":  //城市
                return "城市";
            case "description":  //文本描述
                return "文本描述";
            case "images_upload":  //图片上传
                return "图片上传";
            case "images_look":  //图片展示
                return "图片展示";
            case "map":  //地图
                return "地图";
            case "single_choice": //单选
                return "单选";
            case "parting_line": //分割线
                return "分割线";
            case "textbox":  //文本框
                return "文本框";
            case "more_textbox":  // 文本框（多行）
                return "多行文本框";
            case "multiple_choice":  // 多项选择
                return "多项选择";
            case "drop_down":  // 下拉
                return "下拉";
            case "multiple_drop_down":  // 多级下拉选项
                return "多级下拉选项";
            case "date_time":  // 时间日期
                return "时间日期";
            default:
                return "";
        }
    }
}


