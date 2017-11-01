//package org.toba.habco.utils;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//import org.compiere.util.Env;
//
//
//public class HBC_StandarDeviasi {
//
//	public static void main (String[] args){
//		  System.out.print("Jumlah bilangan = ");
//		  Scanner input = new Scanner(System.in);
//		  int banyakdata = input.nextInt();
//		  System.out.println("\n");
//
//		  List<BigDecimal> nilai = new ArrayList<BigDecimal>();
//		  for(int i = 0;i<banyakdata;i++){
//
//		  System.out.print("Data Ke- " + (i+=0)+"\t : ");
//		  nilai.add(input.nextBigDecimal());
//		      
//		  }
//		  	 System.out.println("Jumlah Bilangan :"+TotalJumlahData(nilai));
//		      System.out.println("Rata - rata :"+hasilrata2(nilai));
//		      System.out.println("Standar Deviasi :"+hasilstandardeviasi(nilai));
//	}
//
//		  
//		  protected static BigDecimal TotalJumlahData(List<BigDecimal> nilai){
//		  BigDecimal result = Env.ZERO;
//		  
//		  for (int i = 0 ; i < nilai.size() ; i++){
//			  result=result.add(nilai.get(i));
//		  }
//		  return result;
//		  }
//		  
//		  protected static BigDecimal hasilrata2(List<BigDecimal> nilai){
//			  BigDecimal rata2= TotalJumlahData(nilai).divide(new BigDecimal(nilai.size()));
//			  return  rata2;
//		  }
//		  
//		  protected static Double hasilstandardeviasi (List<BigDecimal> nilai){
//		  BigDecimal ratarata = hasilrata2(nilai);
//		  double akarjum = 0;
//		  for (int i=0;i<nilai.size();i++){
//			  akarjum += Math.pow(nilai.get(i).subtract(ratarata).doubleValue(),2);
//			  }
//			  return Math.sqrt(akarjum/nilai.size());
//
//			  }
//		
//}
