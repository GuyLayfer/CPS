package core.guiUtilities;

public class CpsRegEx {
	
	public final static String IntegerBetweenMinAndMaxLength = "^\\d{1,9}$";
	
	public final static String OnlyIntegers = "\\d*";
	
	public final static String LicencePlateLength = "^.{7}$";
	
	public final static String FloatNumber = "^(\\d*[.])?\\d+$";
	
	public final static String FloatAllowedCharacter = "^\\d*([.]?\\d*)?$";
}
