package com.example.week_3;

import java.util.ArrayList;

public class GeoData {

	
	public static ArrayList<BankData> getAddressData(){
        ArrayList<BankData> list=new ArrayList<>();
        BankData data=new BankData();
        data.bankName="국민은행";
        data.bankLat=37.3696662622309;
        data.bankLng=126.81010281130753;
        list.add(data);

        data=new BankData();
        data.bankName="IBK기업은행";
        data.bankLat=37.369465560070964;
        data.bankLng=126.8092045872416;
        list.add(data);

        data=new BankData();
        data.bankName="신한은행";
        data.bankLat=37.369529836617005;
        data.bankLng=126.81072508380889;

        list.add(data);
        return list;
	}
}
class BankData {
	String bankName;
	double bankLat;
	double bankLng;
}