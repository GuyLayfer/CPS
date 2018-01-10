package core.guiUtilities;

public class CpsRegEx {
	
	public static String IntegerBetweenMinAndMaxLength = "^\\\\d{1,9}$";
	
	public static String OnlyIntegers = "\\d*";
	
	public static String LicencePlateLength = "^.{7}$";
	
	public static String FloatNumber = "^(\\d*[.])?\\d+$";
	
	public static String FloatAllowedCharacter = "^\\d*([.]?\\d*)?$";
}
