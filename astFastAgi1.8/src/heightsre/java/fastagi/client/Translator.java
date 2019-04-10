package heightsre.java.fastagi.client;

//Imports the Google Cloud client library
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class Translator {

	Translate translate;
	public Translator() {
		super();
		// TODO Auto-generated constructor stub
		
		Translate translate = TranslateOptions.getDefaultInstance().getService();
	}
	
	
	public String EnglishToSpanish(String strEng) throws Exception
	{
		//String strSpa;
		
		 Translation translation =
			        translate.translate(
			            strEng,
			            TranslateOption.sourceLanguage("en"),
			            TranslateOption.targetLanguage("es"));
		
		
		return translation.getTranslatedText();
		
		
		
		
		
		
	}

}
