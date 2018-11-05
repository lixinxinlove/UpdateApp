package com.lixinxin.updateapp.utils;

import android.os.RemoteException;
import android.util.Base64;
import android.widget.Toast;

import com.eventmosh.evente.EventApp;
import com.gprinter.aidl.GpService;
import com.gprinter.command.EscCommand;
import com.gprinter.command.EscCommand.ENABLE;
import com.gprinter.command.EscCommand.FONT;
import com.gprinter.command.EscCommand.JUSTIFICATION;
import com.gprinter.command.GpCom;
import com.gprinter.command.GpUtils;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by android on 2017/6/16.
 */

public class PrintUtil {

//            event_name
//            user_name
//            mobile
//           screening
//            price
//            time
//            seat_name

    public static void printData(GpService mGpService, HashMap<String, String> print) {
        EscCommand esc = new EscCommand();
        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, ENABLE.OFF);
        esc.addText("票核销凭证\n\n");
        esc.addSelectJustification(JUSTIFICATION.LEFT);
        esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF);
        esc.addText("打单时间：" + DateTimeUtils.getNowTiem() + "\n");
        esc.addText("打单手机：" + StringUtils.deviceType() + "\n");
        esc.addText("活动名称：" + print.get("event_name") + "\n");
        esc.addText("场次：" + print.get("screening") + "\n");
        esc.addText("票品：" + print.get("price") + "\n");
        esc.addText("购票人：" + print.get("user_name").substring(0, 1) + "(^o^)\n");
        esc.addText("购票手机：" + print.get("mobile").substring(0, 3) + "(^o^)" + print.get("mobile").substring(7, 11) + "\n\n");


        if (!print.get("seat_name").equals("无座")) {
            esc.addText("座位号：\n");
            esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.ON, ENABLE.OFF, ENABLE.OFF);
            esc.addText(print.get("seat_name") + "\n");
            esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF);
        }


        esc.addText("----------------------------------------\n");
        esc.addText("技术支持：活动易\n合作热线：400-063-0260");
        esc.addText("\n\n\n\n\n");

        Vector<Byte> datas = esc.getCommand(); // 发送数据
        byte[] bytes = GpUtils.ByteTo_byte(datas);
        String str = Base64.encodeToString(bytes, Base64.DEFAULT);
        int rel;
        try {
            rel = mGpService.sendEscCommand(0, str);
            GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rel];
            if (r != GpCom.ERROR_CODE.SUCCESS) {
                Toast.makeText(EventApp.mContext, GpCom.getErrorText(r), Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void printTest(GpService mGpService) {
        EscCommand esc = new EscCommand();

        esc.addSelectJustification(JUSTIFICATION.CENTER);
        esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.ON, ENABLE.ON, ENABLE.OFF);
        esc.addText("票核销凭证(测试)\n\n");
        esc.addSelectJustification(JUSTIFICATION.LEFT);
        esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF);
        esc.addText("打单时间：" + DateTimeUtils.getNowTiem() + "\n");
        esc.addText("打单手机：" + StringUtils.deviceType() + "\n");
        esc.addText("活动名称：孙xx演唱会\n");
        esc.addText("场次：2017年5月28日\n");
        esc.addText("票品：演出票\n");
        esc.addText("购票人：" + "孙里".substring(0, 1) + "(^o^)\n");
        esc.addText("购票手机：" + "17090408824".substring(0, 3) + "(^o^)" + "17090408824".substring(7, 11) + "\n\n");
        esc.addText("座位号：\n");

        esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.ON, ENABLE.OFF, ENABLE.OFF);

        esc.addText("东区 5排8座\n");
        esc.addText("东区 5排8座\n");
        esc.addText("东区 5排12座\n");

        esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF);
        esc.addText("----------------------------------------\n");
        esc.addText("技术支持：活动易\n合作热线：400-063-0260");
        esc.addText("\n\n\n\n\n");

        Vector<Byte> datas = esc.getCommand(); // 发送数据
        byte[] bytes = GpUtils.ByteTo_byte(datas);
        String str = Base64.encodeToString(bytes, Base64.DEFAULT);
        int rel;
        try {
            rel = mGpService.sendEscCommand(0, str);
            GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rel];
            if (r != GpCom.ERROR_CODE.SUCCESS) {
                Toast.makeText(EventApp.mContext, GpCom.getErrorText(r), Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public static void printTest2(GpService mGpService, int count) {
        EscCommand esc = new EscCommand();
        esc.addSelectJustification(JUSTIFICATION.CENTER);
        esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.ON, ENABLE.ON, ENABLE.OFF);
        esc.addText("收款凭证(测试)\n\n");
        esc.addSelectJustification(JUSTIFICATION.LEFT);
        esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF);
        esc.addText("打单时间：" + DateTimeUtils.getNowTiem() + "\n");
        esc.addText("打单手机：" + StringUtils.deviceType() + "\n");
        esc.addText("操作账号：lixinxin\n");
        esc.addText("交易单号：35425346356247557\n");
        esc.addText("支付时间：" + DateTimeUtils.getNowTiem() + "\n\n");
        esc.addText("付款金额：\n");

        esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.ON, ENABLE.ON, ENABLE.OFF);

        esc.addText("￥320元\n");

        esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF);
        esc.addText("------------------------------------------\n");
        esc.addText("技术支持：活动易\n合作热线：400-063-0260");
        esc.addText("\n\n\n\n\n");

        Vector<Byte> datas = esc.getCommand(); // 发送数据
        byte[] bytes = GpUtils.ByteTo_byte(datas);
        String str = Base64.encodeToString(bytes, Base64.DEFAULT);
        int rel;
        for (int i = 0; i < count; i++) {
            try {
                rel = mGpService.sendEscCommand(0, str);
                GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rel];
                if (r != GpCom.ERROR_CODE.SUCCESS) {
                    Toast.makeText(EventApp.mContext, GpCom.getErrorText(r), Toast.LENGTH_SHORT).show();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 收据
     *
     * @param mGpService
     * @param print
     */
    public static void printReceipt(GpService mGpService, HashMap<String, String> print, int count) {
        EscCommand esc = new EscCommand();
        esc.addSelectJustification(JUSTIFICATION.CENTER);
        esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.ON, ENABLE.ON, ENABLE.OFF);
        esc.addText("收款凭证\n\n");
        esc.addSelectJustification(JUSTIFICATION.LEFT);
        esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF);
        esc.addText("打单时间：" + DateTimeUtils.getNowTiem() + "\n");
        esc.addText("打单手机：" + StringUtils.deviceType() + "\n");
        esc.addText("操作账号：" + print.get("operation_account") + "\n");
        esc.addText("交易单号：" + print.get("transaction_number") + "\n");
        esc.addText("支付时间：" + print.get("pay_date") + "\n\n");
        esc.addText("付款金额：\n");

        esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.ON, ENABLE.ON, ENABLE.OFF);

        esc.addText("￥" + print.get("money") + "元\n");

        esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF);
        esc.addText("---------------------------------------\n");
        esc.addText("技术支持：活动易\n合作热线：400-063-0260");
        esc.addText("\n\n\n\n\n");

        Vector<Byte> datas = esc.getCommand(); // 发送数据
        byte[] bytes = GpUtils.ByteTo_byte(datas);
        String str = Base64.encodeToString(bytes, Base64.DEFAULT);
        int rel;
        for (int i = 0; i < count; i++) {
            try {
                rel = mGpService.sendEscCommand(0, str);
                GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rel];
                if (r != GpCom.ERROR_CODE.SUCCESS) {
                    Toast.makeText(EventApp.mContext, GpCom.getErrorText(r), Toast.LENGTH_SHORT).show();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


}
