package com.bornfire.util;

public class SwiftParserMT103Util {
	 private static String SPLIT_STRING;
	    
	    static {
	    	SwiftParserMT103Util.SPLIT_STRING = "\\r?\\n";
	    }
	    
	    private SwiftParserMT103Util() {
	    }
	    
	    public static String[] parseTag32A(final String tag32A) {
	    	System.out.println("Sub"+tag32A.substring(3,tag32A.length()-1));
	        final String[] arrayTag32A = { tag32A.substring(0, 6), tag32A.substring(6, 9), tag32A.substring(9, tag32A.length()-1) };
	        System.out.println("111"+arrayTag32A[1]);
	        System.out.println("111"+arrayTag32A[0]);
	        System.out.println("111"+arrayTag32A[2]);
	        return arrayTag32A;
	    }
	    
	    public static String[] parseTag33B(final String tag33B) {
	    	System.out.println("33B val"+ tag33B);
	    	System.out.println("index" + tag33B.indexOf(","));
	    	System.out.println("Sub"+tag33B.length());
	    	System.out.println("Sub"+tag33B.substring(3));
	    	System.out.println("Sub"+tag33B.substring(3,tag33B.length()-1));
	    	
	    	String bal=tag33B.substring(3,tag33B.length()-1);
//	        final String[] arrayTag33B = { tag33B.substring(0, 3), tag33B.substring(3, tag33B.indexOf(44)) };
	    	//String
	    	  final String[] arrayTag33B = { tag33B.substring(0, 3), bal };
	    	  System.out.println(arrayTag33B[1]);
	    	  System.out.println(arrayTag33B[0]);
	        return arrayTag33B;
	    }
//	    
//	    public static String[] parseTag33B2(final String tag33B) {
////	        final String[] arrayTag33B = { tag33B.substring(0, 3), tag33B.substring(3, tag33B.indexOf(44)) };
//	    	  final String[] arrayTag33B = { tag33B.substring(0, tag33B.indexOf(",")), tag33B.substring(tag33B.indexOf(","),2) };
//	        return arrayTag33B;
//	    }
	    
	    public static String[] parseTag50A(final String tag50A) {
	        return tag50A.split(SwiftParserMT103Util.SPLIT_STRING);
	    }
	    
	    public static String[] parseTag50F(final String tag50F) {
	        return tag50F.split(SwiftParserMT103Util.SPLIT_STRING);
	    }
	    
	    public static String[] parseTag50K(final String tag50K) {
	        return tag50K.split(SwiftParserMT103Util.SPLIT_STRING);
	    }
	    
	    public static String[] parseTag59(final String tag59) {
	        return tag59.split(SwiftParserMT103Util.SPLIT_STRING);
	    }
}
